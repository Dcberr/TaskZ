package dcberr.taskz.modules.task.specification;

import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.entity.Task;
import jakarta.persistence.criteria.Join;

public final class TaskSpecifications {

    private TaskSpecifications() {}

    public static Specification<Task> withStatuses(Set<TaskStatus> statuses) {
        return (root, query, cb) -> {
            if (statuses == null || statuses.isEmpty()) {
                return cb.conjunction();
            }

            return root.get("status").in(statuses);
        };
    }

    public static Specification<Task> withPriority(Priority priority) {
        return (root, query, cb) ->
                priority == null ? cb.conjunction() : cb.equal(root.get("priority"), priority);
    }

    public static Specification<Task> withAssignees(List<String> assignees) {
        return (root, query, cb) -> {
            if (assignees == null || assignees.isEmpty()) {
                return cb.conjunction();
            }

            if (query != null) {
                query.distinct(true);
            }

            Join<Task, String> assigneeJoin = root.join("assignees");
            return cb.lower(assigneeJoin)
                    .in(assignees.stream()
                            .map(assignee -> assignee.toLowerCase(Locale.ROOT))
                            .toList());
        };
    }

    public static Specification<Task> andAll(
            Set<TaskStatus> statuses,
            Priority priority,
            List<String> assignees
    ) {
        return Specification.where(withStatuses(statuses))
                .and(withPriority(priority))
                .and(withAssignees(assignees));
    }
}
