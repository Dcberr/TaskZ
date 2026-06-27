package dcberr.taskz.modules.task.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record UpdateTaskAssigneesRequest(

        List<@NotBlank String> assignees

) {
    public UpdateTaskAssigneesRequest {
        assignees = assignees == null ? List.of() : List.copyOf(assignees);
    }
}
