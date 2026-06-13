package dcberr.taskz.modules.task.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.dto.CreateTaskRequest;
import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.dto.UpdateTaskStatusRequest;
import dcberr.taskz.modules.task.entity.Task;
import dcberr.taskz.modules.task.exception.TaskNotFoundException;
import dcberr.taskz.modules.task.mapper.TaskMapper;
import dcberr.taskz.modules.task.repository.TaskRepository;
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
                .priority(request.priority())
                .status(TaskStatus.OPEN)
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

        task.setStatus(request.status());

        taskRepository.save(task);
    }
}
