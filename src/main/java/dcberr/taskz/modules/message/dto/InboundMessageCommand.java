package dcberr.taskz.modules.message.dto;

import dcberr.taskz.common.enums.MessageSource;

public record InboundMessageCommand(
        String sender,
        String content,
        MessageSource source,
        String channelName,
        String conversationId,
        String externalMessageId
) {
}
