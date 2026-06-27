package dcberr.taskz.modules.task.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dcberr.taskz.common.dto.PageResponse;
import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskSource;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.dto.CreateTaskRequest;
import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskQueryFilter;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.dto.UpdateTaskAssigneesRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskPriorityRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskStatusRequest;
import dcberr.taskz.modules.task.entity.Task;
import dcberr.taskz.modules.task.exception.TaskNotFoundException;
import dcberr.taskz.modules.task.mapper.TaskMapper;
import dcberr.taskz.modules.task.repository.TaskRepository;
import dcberr.taskz.modules.task.specification.TaskSpecifications;
import dcberr.taskz.modules.task.support.TaskAssignees;
import dcberr.taskz.modules.task.validator.TaskStatusTransitionValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final TaskEventService taskEventService;

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskResponse> getTasks(
            TaskQueryFilter filter,
            Pageable pageable
    ) {

        return readTasks(filter, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskResponse> getOpenTasks(
            TaskQueryFilter filter,
            Pageable pageable
    ) {

        return readTasks(
                TaskQueryFilter.of(
                        Set.of(
                                TaskStatus.OPEN,
                                TaskStatus.IN_PROGRESS,
                                TaskStatus.BLOCKED
                        ),
                        filter.priority(),
                        filter.assignees()
                ),
                pageable
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskResponse> getCompletedTasks(
            TaskQueryFilter filter,
            Pageable pageable
    ) {

        return readTasks(
                TaskQueryFilter.of(
                        Set.of(TaskStatus.COMPLETED),
                        filter.priority(),
                        filter.assignees()
                ),
                pageable
        );
    }
    @Override
    public void createTask(
                CreateTaskRequest request
        ) {

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .requester(request.requester())
                .assignees(new ArrayList<>(TaskAssignees.normalize(request.assignees())))
                .priority(request.priority())
                .status(TaskStatus.OPEN)
                .dueDateTime(request.dueDateTime())
                .aiConfidence(request.aiConfidence())
                .source(request.source() == null ? TaskSource.MOCK : request.source())
                .sourceMessageId(request.sourceMessageId())
                .build();

        Task savedTask = taskRepository.save(task);
        taskEventService.recordTaskCreated(savedTask);
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

    private PageResponse<TaskResponse> readTasks(
            TaskQueryFilter filter,
            Pageable pageable
    ) {

        return PageResponse.from(
                taskRepository.findAll(
                                TaskSpecifications.andAll(
                                        filter.statuses(),
                                        filter.priority(),
                                        filter.assignees()
                                ),
                                pageable
                        )
                        .map(TaskMapper::toResponse)
        );
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

        TaskStatus oldStatus = task.getStatus();
        LocalDateTime oldCompletedAt = task.getCompletedAt();

        TaskStatusTransitionValidator.validate(task.getStatus(), request.status());

        task.setStatus(request.status());
        task.setCompletedAt(request.status() == TaskStatus.COMPLETED
                ? java.time.LocalDateTime.now()
                : null);

        Task savedTask = taskRepository.save(task);
        taskEventService.recordStatusChanged(
                savedTask.getId(),
                oldStatus,
                oldCompletedAt,
                savedTask.getStatus(),
                savedTask.getCompletedAt()
        );
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

        Priority oldPriority = task.getPriority();

        task.setPriority(request.priority());
        Task savedTask = taskRepository.save(task);
        taskEventService.recordPriorityChanged(
                savedTask.getId(),
                oldPriority,
                savedTask.getPriority()
        );
    }

    @Override
    public void updateAssignees(
            UUID taskId,
            UpdateTaskAssigneesRequest request
    ) {

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->
                        new TaskNotFoundException(
                                "Task not found: " + taskId
                        ));

        List<String> oldAssignees = TaskAssignees.normalize(task.getAssignees());
        List<String> newAssignees = TaskAssignees.normalize(request.assignees());

        task.setAssignees(new ArrayList<>(newAssignees));
        Task savedTask = taskRepository.save(task);
        taskEventService.recordAssigneesChanged(
                savedTask.getId(),
                oldAssignees,
                savedTask.getAssignees()
        );
    }
}
