package dcberr.taskz.modules.message.dto;

import jakarta.validation.constraints.NotBlank;

public record MockMessageRequest(

        @NotBlank
        String sender,

        @NotBlank
        String content

) {
}
