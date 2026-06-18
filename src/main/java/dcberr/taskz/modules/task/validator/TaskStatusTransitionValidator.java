package dcberr.taskz.modules.task.validator;

import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.exception.InvalidTaskStatusTransitionException;

public final class TaskStatusTransitionValidator {

    private static final Map<TaskStatus, Set<TaskStatus>> ALLOWED_TRANSITIONS = Map.of(
            TaskStatus.OPEN, EnumSet.of(
                    TaskStatus.IN_PROGRESS,
                    TaskStatus.BLOCKED,
                    TaskStatus.COMPLETED,
                    TaskStatus.CANCELLED
            ),
            TaskStatus.IN_PROGRESS, EnumSet.of(
                    TaskStatus.BLOCKED,
                    TaskStatus.COMPLETED,
                    TaskStatus.CANCELLED
            ),
            TaskStatus.BLOCKED, EnumSet.of(
                    TaskStatus.IN_PROGRESS,
                    TaskStatus.CANCELLED
            ),
            TaskStatus.COMPLETED, EnumSet.noneOf(TaskStatus.class),
            TaskStatus.CANCELLED, EnumSet.noneOf(TaskStatus.class)
    );

    private TaskStatusTransitionValidator() {}

    public static void validate(TaskStatus currentStatus, TaskStatus nextStatus) {
        if (currentStatus == nextStatus) {
            return;
        }

        Set<TaskStatus> allowed = ALLOWED_TRANSITIONS.getOrDefault(
                currentStatus,
                EnumSet.noneOf(TaskStatus.class)
        );

        if (!allowed.contains(nextStatus)) {
            throw new InvalidTaskStatusTransitionException(
                    "Invalid task status transition from " + currentStatus + " to " + nextStatus
            );
        }
    }
}
