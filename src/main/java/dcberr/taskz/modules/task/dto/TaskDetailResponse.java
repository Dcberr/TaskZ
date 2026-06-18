package dcberr.taskz.modules.task.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.UUID;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskSource;
import dcberr.taskz.common.enums.TaskStatus;

public record TaskDetailResponse(
        UUID id,
        String title,
        String description,
        String requester,
        String assignee,
        Priority priority,
        TaskStatus status,
        OffsetDateTime dueDateTime,
        Double aiConfidence,
        TaskSource source,
        String sourceMessageId,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime completedAt
) {
}
