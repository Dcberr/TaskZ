package dcberr.taskz.modules.task.dto;

import java.time.OffsetDateTime;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskSource;

public record CreateTaskRequest(

        String title,

        String description,

        String requester,

        String assignee,

        Priority priority,

        OffsetDateTime dueDateTime,

        Double aiConfidence,

        TaskSource source,

        String sourceMessageId
) {
}
