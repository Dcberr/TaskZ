package dcberr.taskz.modules.task.dto;

import dcberr.taskz.common.enums.TaskStatus;
import jakarta.annotation.Nonnull;

public record UpdateTaskStatusRequest(

        @Nonnull
        TaskStatus status

) {
}
