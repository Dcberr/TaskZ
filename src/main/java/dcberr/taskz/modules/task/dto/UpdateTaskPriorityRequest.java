package dcberr.taskz.modules.task.dto;

import dcberr.taskz.common.enums.Priority;
import jakarta.validation.constraints.NotNull;

public record UpdateTaskPriorityRequest(

        @NotNull
        Priority priority

) {
}
