package dcberr.taskz.modules.task.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateTaskAssigneeRequest(

        @NotBlank
        String assignee

) {
}
