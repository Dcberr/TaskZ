package dcberr.taskz.modules.message.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import dcberr.taskz.common.enums.MessageSource;

public record MessageResponse(

        UUID id,

        String sender,

        String content,

        MessageSource source,

        String channelName,

        String conversationId,

        String externalMessageId,

        Boolean processed,

        LocalDateTime createdAt

) {
}
