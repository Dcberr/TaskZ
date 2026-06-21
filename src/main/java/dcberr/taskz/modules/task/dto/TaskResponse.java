package dcberr.taskz.modules.task.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;

public record TaskResponse(
        UUID id,
        String title,
        String requester,
        String assignee,
        OffsetDateTime dueDateTime,
        Priority priority,
        TaskStatus status,
        LocalDateTime createdAt
) {
}
