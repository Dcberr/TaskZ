package dcberr.taskz.modules.task.dto;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;

public record TaskResponse(
        UUID id,
        String title,
        String requester,
        List<String> assignees,
        OffsetDateTime dueDateTime,
        Priority priority,
        TaskStatus status,
        LocalDateTime createdAt
) {
    public TaskResponse {
        assignees = assignees == null ? List.of() : List.copyOf(assignees);
    }
}
