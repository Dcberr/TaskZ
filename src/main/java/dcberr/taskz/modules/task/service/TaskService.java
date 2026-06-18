package dcberr.taskz.modules.task.service;

import java.util.List;
import java.util.UUID;

import dcberr.taskz.modules.task.dto.CreateTaskRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskAssigneeRequest;
import dcberr.taskz.modules.task.dto.UpdateTaskPriorityRequest;
import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.dto.UpdateTaskStatusRequest;

public interface TaskService {

    List<TaskResponse> getAllTasks();

    TaskDetailResponse getTask(UUID taskId);

    void updateStatus(UUID taskId, UpdateTaskStatusRequest request);

    void updatePriority(UUID taskId, UpdateTaskPriorityRequest request);

    void updateAssignee(UUID taskId, UpdateTaskAssigneeRequest request);

    void createTask(CreateTaskRequest request);
    
}
