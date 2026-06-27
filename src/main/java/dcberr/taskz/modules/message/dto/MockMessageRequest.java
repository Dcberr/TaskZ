package dcberr.taskz.modules.message.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record MockMessageRequest(

        @NotBlank
        String sender,

        @NotBlank
        String content,

        String channelName,

        String conversationId,

        String externalMessageId,

        List<String> conversationMessages,

        List<String> participants 

) {
}
