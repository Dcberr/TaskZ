package dcberr.taskz.modules.task.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;

public record TaskResponse(
        UUID id,
        String title,
        String requester,
        Priority priority,
        TaskStatus status,
        LocalDateTime createdAt
) {
}
