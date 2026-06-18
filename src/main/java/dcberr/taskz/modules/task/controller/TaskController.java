package dcberr.taskz.modules.task.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.dto.UpdateTaskAssigneeRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskPriorityRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskStatusRequest;
import dcberr.taskz.modules.task.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public List<TaskResponse> getTasks() {

        return taskService.getAllTasks();
    }

    @GetMapping("/{taskId}")
    public TaskDetailResponse getTask(
            @PathVariable UUID taskId
    ) {

        return taskService.getTask(taskId);
    }

    @PatchMapping("/{taskId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskStatusRequest request
    ) {

        taskService.updateStatus(taskId, request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/priority")
    public ResponseEntity<Void> updatePriority(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskPriorityRequest request
    ) {

        taskService.updatePriority(taskId, request);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{taskId}/assignee")
    public ResponseEntity<Void> updateAssignee(
            @PathVariable UUID taskId,
            @Valid @RequestBody UpdateTaskAssigneeRequest request
    ) {

        taskService.updateAssignee(taskId, request);

        return ResponseEntity.noContent().build();
    }
}
