package dcberr.taskz.modules.task.mapper;

import dcberr.taskz.modules.task.dto.TaskDetailResponse;
import dcberr.taskz.modules.task.dto.TaskResponse;
import dcberr.taskz.modules.task.entity.Task;

public class TaskMapper {

    public static TaskResponse toResponse(Task task) {

        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getRequester(),
                task.getPriority(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }

    public static TaskDetailResponse toDetailResponse(Task task) {

        return new TaskDetailResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getRequester(),
                task.getPriority(),
                task.getStatus(),
                task.getCreatedAt()
        );
    }
}
