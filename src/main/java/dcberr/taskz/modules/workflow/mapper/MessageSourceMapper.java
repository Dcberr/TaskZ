package dcberr.taskz.modules.workflow.mapper;

import dcberr.taskz.common.enums.MessageSource;
import dcberr.taskz.common.enums.TaskSource;

public final class MessageSourceMapper {

    private MessageSourceMapper() {}

    public static TaskSource toTaskSource(MessageSource source) {
        if (source == null) {
            return TaskSource.MOCK;
        }

        return switch (source) {
            case MOCK -> TaskSource.MOCK;
            case TEAMS -> TaskSource.TEAMS;
            case SLACK -> TaskSource.SLACK;
            case EMAIL -> TaskSource.EMAIL;
        };
    }
}
