package dcberr.taskz.modules.task.mapper;

import dcberr.taskz.common.enums.Priority;

public final class PriorityMapper {

    private PriorityMapper() {}

    public static Priority from(
            String priority
    ) {

        if (priority == null) {
            return Priority.MEDIUM;
        }

        try {

            return Priority.valueOf(
                    priority.toUpperCase()
            );

        } catch (Exception ex) {

            return Priority.MEDIUM;
        }
    }
}
