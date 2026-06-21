package dcberr.taskz.modules.task.specification;

import java.util.Set;

import org.springframework.data.jpa.domain.Specification;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.entity.Task;
import jakarta.persistence.criteria.Predicate;

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

    public static Specification<Task> withAssignee(String assignee) {
        return (root, query, cb) -> {
            if (assignee == null || assignee.isBlank()) {
                return cb.conjunction();
            }

            return cb.equal(cb.lower(root.get("assignee")), assignee.toLowerCase());
        };
    }

    public static Specification<Task> andAll(
            Set<TaskStatus> statuses,
            Priority priority,
            String assignee
    ) {
        return Specification.where(withStatuses(statuses))
                .and(withPriority(priority))
                .and(withAssignee(assignee));
    }
}
