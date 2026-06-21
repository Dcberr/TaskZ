package dcberr.taskz.modules.task.dto;

import java.util.Collections;
import java.util.Set;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;

public record TaskQueryFilter(
        Set<TaskStatus> statuses,
        Priority priority,
        String assignee
) {

    public TaskQueryFilter {
        statuses = statuses == null ? Collections.emptySet() : Set.copyOf(statuses);
        assignee = assignee == null || assignee.isBlank() ? null : assignee.trim();
    }

    public static TaskQueryFilter of(
            Set<TaskStatus> statuses,
            Priority priority,
            String assignee
    ) {
        return new TaskQueryFilter(statuses, priority, assignee);
    }
}
