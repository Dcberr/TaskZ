package dcberr.taskz.modules.task.service;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Pageable;

import dcberr.taskz.common.dto.PageResponse;
import dcberr.taskz.modules.task.dto.CreateTaskRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskAssigneesRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskPriorityRequest;
import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.dto.TaskQueryFilter;
import dcberr.taskz.modules.task.dto.UpdateTaskStatusRequest;

public interface TaskService {

    PageResponse<TaskResponse> getTasks(TaskQueryFilter filter, Pageable pageable);

    PageResponse<TaskResponse> getOpenTasks(TaskQueryFilter filter, Pageable pageable);

    PageResponse<TaskResponse> getCompletedTasks(TaskQueryFilter filter, Pageable pageable);

    TaskDetailResponse getTask(UUID taskId);

    void updateStatus(UUID taskId, UpdateTaskStatusRequest request);

    void updatePriority(UUID taskId, UpdateTaskPriorityRequest request);

    void updateAssignees(UUID taskId, UpdateTaskAssigneesRequest request);

    void createTask(CreateTaskRequest request);
}
