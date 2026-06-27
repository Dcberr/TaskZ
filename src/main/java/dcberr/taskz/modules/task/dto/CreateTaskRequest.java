package dcberr.taskz.modules.task.dto;

import java.time.OffsetDateTime;
import java.util.List;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskSource;

public record CreateTaskRequest(

        String title,

        String description,

        String requester,

        List<String> assignees,

        Priority priority,

        OffsetDateTime dueDateTime,

        Double aiConfidence,

        TaskSource source,

        String sourceMessageId
) {
    public CreateTaskRequest {
        assignees = assignees == null ? List.of() : List.copyOf(assignees);
    }
}
