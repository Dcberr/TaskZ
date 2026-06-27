package dcberr.taskz.modules.workflow.dto;

import java.util.List;
import java.util.UUID;

import dcberr.taskz.common.enums.MessageSource;

public record MessageContext(

        UUID messageId,

        String sender,

        String content,

        MessageSource source,

        String channelName,

        String conversationId,

        String externalMessageId,

        List<String> conversationMessages,

        List<String> participants

) {
}
