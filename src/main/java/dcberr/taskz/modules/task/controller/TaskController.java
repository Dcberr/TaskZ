package dcberr.taskz.modules.task.controller;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dcberr.taskz.common.dto.PageResponse;
import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskQueryFilter;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.dto.TaskEventResponse;
import dcberr.taskz.modules.task.dto.UpdateTaskAssigneesRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskPriorityRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskStatusRequest;
import dcberr.taskz.modules.task.exception.InvalidTaskSortFieldException;
import dcberr.taskz.modules.task.service.TaskEventService;
import dcberr.taskz.modules.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private static final int DEFAULT_PAGE_SIZE = 20;
    private static final int MAX_PAGE_SIZE = 100;
    private static final Set<String> TASK_SORT_FIELDS = Set.of(
            "id",
            "title",
            "requester",
            "dueDateTime",
            "priority",
            "status",
            "createdAt",
            "updatedAt",
            "completedAt"
    );
    private static final Set<String> EVENT_SORT_FIELDS = Set.of(
            "id",
            "taskId",
            "eventType",
            "createdAt"
    );

    private final TaskService taskService;
    private final TaskEventService taskEventService;

    @GetMapping
    public PageResponse<TaskResponse> getTasks(
            @RequestParam(required = false)
            TaskStatus status,
            @RequestParam(required = false)
            Priority priority,
            @RequestParam(name = "assignees", required = false)
            List<String> assignees,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "20")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "DESC")
            Sort.Direction sortDirection
    ) {

        return taskService.getTasks(
                TaskQueryFilter.of(
                        status == null ? Set.of() : Set.of(status),
                        priority,
                        assignees
                ),
                toPageRequest(
                        page,
                        size,
                        sortBy,
                        sortDirection,
                        TASK_SORT_FIELDS
                )
        );
    }

    @GetMapping("/{taskId:[0-9a-fA-F\\-]{36}}")
    public TaskDetailResponse getTask(
            @PathVariable UUID taskId
    ) {

        return taskService.getTask(taskId);
    }

    @GetMapping("/{taskId:[0-9a-fA-F\\-]{36}}/events")
    public PageResponse<TaskEventResponse> getTaskEvents(
            @PathVariable UUID taskId,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "20")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "DESC")
            Sort.Direction sortDirection
    ) {

        return taskEventService.getEventsByTaskId(
                taskId,
                toPageRequest(
                        page,
                        size,
                        sortBy,
                        sortDirection,
                        EVENT_SORT_FIELDS
                )
        );
    }

    @GetMapping("/open")
    public PageResponse<TaskResponse> getOpenTasks(
            @RequestParam(required = false)
            Priority priority,
            @RequestParam(name = "assignees", required = false)
            List<String> assignees,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "20")
            int size,
            @RequestParam(defaultValue = "createdAt")
            String sortBy,
            @RequestParam(defaultValue = "DESC")
            Sort.Direction sortDirection
    ) {

        return taskService.getOpenTasks(
                TaskQueryFilter.of(Set.of(), priority, assignees),
                toPageRequest(
                        page,
                        size,
                        sortBy,
                        sortDirection,
                        TASK_SORT_FIELDS
                )
        );
    }

    @GetMapping("/completed")
    public PageResponse<TaskResponse> getCompletedTasks(
            @RequestParam(required = false)
            Priority priority,
            @RequestParam(name = "assignees", required = false)
            List<String> assignees,
            @RequestParam(defaultValue = "0")
            int page,
            @RequestParam(defaultValue = "20")
            int size,
            @RequestParam(defaultValue = "completedAt")
            String sortBy,
            @RequestParam(defaultValue = "DESC")
            Sort.Direction sortDirection
    ) {

        return taskService.getCompletedTasks(
                TaskQueryFilter.of(Set.of(), priority, assignees),
                toPageRequest(
                        page,
                        size,
                        sortBy,
                        sortDirection,
                        TASK_SORT_FIELDS
                )
        );
    }

    @PatchMapping("/{taskId:[0-9a-fA-F\\-]{36}}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {

        taskService.updateStatus(taskId, request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId:[0-9a-fA-F\\-]{36}}/priority")
    public ResponseEntity<Void> updatePriority(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskPriorityRequest request
    ) {

        taskService.updatePriority(taskId, request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId:[0-9a-fA-F\\-]{36}}/assignees")
    public ResponseEntity<Void> updateAssignees(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskAssigneesRequest request
    ) {

        taskService.updateAssignees(taskId, request);

        return ResponseEntity.noContent().build();
    }

    private PageRequest toPageRequest(
            int page,
            int size,
            String sortBy,
            Sort.Direction sortDirection,
            Set<String> allowedSortFields
    ) {

        int safePage = Math.max(page, 0);
        int safeSize = Math.min(Math.max(size, 1), MAX_PAGE_SIZE);
        String effectiveSortBy = (sortBy == null || sortBy.isBlank())
                ? "createdAt"
                : sortBy;

        if (!allowedSortFields.contains(effectiveSortBy)) {
            throw new InvalidTaskSortFieldException(
                    "Invalid sort field: " + effectiveSortBy
            );
        }

        return PageRequest.of(
                safePage,
                safeSize,
                Sort.by(sortDirection, effectiveSortBy)
        );
    }
}
