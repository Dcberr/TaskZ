package dcberr.taskz.modules.task.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import dcberr.taskz.common.enums.TaskEventType;

public record TaskEventResponse(
        UUID id,
        UUID taskId,
        TaskEventType eventType,
        String oldValue,
        String newValue,
        LocalDateTime createdAt
) {
}
