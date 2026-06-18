package dcberr.taskz.modules.task.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.common.enums.TaskSource;
import dcberr.taskz.modules.task.dto.CreateTaskRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskAssigneeRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskPriorityRequest;
import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.dto.UpdateTaskStatusRequest;
import dcberr.taskz.modules.task.entity.Task;
import dcberr.taskz.modules.task.exception.TaskNotFoundException;
import dcberr.taskz.modules.task.mapper.TaskMapper;
import dcberr.taskz.modules.task.repository.TaskRepository;
import dcberr.taskz.modules.task.validator.TaskStatusTransitionValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponse> getAllTasks() {

        return taskRepository.findAll()
                .stream()
                .map(TaskMapper::toResponse)
                .toList();
    }
    @Override
    public void createTask(
                CreateTaskRequest request
        ) {

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .requester(request.requester())
                .assignee(request.assignee())
                .priority(request.priority())
                .status(TaskStatus.OPEN)
                .dueDateTime(request.dueDateTime())
                .aiConfidence(request.aiConfidence())
                .source(request.source() == null ? TaskSource.MOCK : request.source())
                .sourceMessageId(request.sourceMessageId())
                .build();

        taskRepository.save(task);
        }

    @Override
    @Transactional(readOnly = true)
    public TaskDetailResponse getTask(UUID taskId) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found: " + taskId
                        ));

        return TaskMapper.toDetailResponse(task);
    }

    @Override
    public void updateStatus(
            UUID taskId,
            UpdateTaskStatusRequest request
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found: " + taskId
                        ));

        TaskStatusTransitionValidator.validate(task.getStatus(), request.status());

        task.setStatus(request.status());
        task.setCompletedAt(request.status() == TaskStatus.COMPLETED
                ? java.time.LocalDateTime.now()
                : null);

        taskRepository.save(task);
    }

    @Override
    public void updatePriority(
            UUID taskId,
            UpdateTaskPriorityRequest request
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found: " + taskId
                        ));

        task.setPriority(request.priority());
        taskRepository.save(task);
    }

    @Override
    public void updateAssignee(
            UUID taskId,
            UpdateTaskAssigneeRequest request
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found: " + taskId
                        ));

        task.setAssignee(request.assignee());
        taskRepository.save(task);
    }
}
