package dcberr.taskz.modules.task.dto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.support.TaskAssignees;

public record TaskQueryFilter(
        Set<TaskStatus> statuses,
        Priority priority,
        List<String> assignees
) {

    public TaskQueryFilter {
        statuses = statuses == null ? Collections.emptySet() : Set.copyOf(statuses);
        assignees = TaskAssignees.normalize(assignees);
    }

    public static TaskQueryFilter of(
            Set<TaskStatus> statuses,
            Priority priority,
            List<String> assignees
    ) {
        return new TaskQueryFilter(statuses, priority, assignees);
    }
}
