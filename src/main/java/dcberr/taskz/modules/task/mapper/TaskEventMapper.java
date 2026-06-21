package dcberr.taskz.modules.task.mapper;

import dcberr.taskz.modules.task.dto.TaskEventResponse;
import dcberr.taskz.modules.task.entity.TaskEvent;

public class TaskEventMapper {

    private TaskEventMapper() {}

    public static TaskEventResponse toResponse(TaskEvent event) {
        return new TaskEventResponse(
                event.getId(),
                event.getTaskId(),
                event.getEventType(),
                event.getOldValue(),
                event.getNewValue(),
                event.getCreatedAt()
        );
    }
}
