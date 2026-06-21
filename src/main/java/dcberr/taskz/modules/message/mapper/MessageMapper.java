package dcberr.taskz.modules.message.mapper;

import dcberr.taskz.modules.message.dto.MessageResponse;
import dcberr.taskz.modules.message.entity.RawMessage;

public class MessageMapper {

    private MessageMapper() {}

    public static MessageResponse toResponse(
            RawMessage message
    ) {

        return new MessageResponse(
                message.getId(),
                message.getSender(),
                message.getContent(),
                message.getSource(),
                message.getChannelName(),
                message.getConversationId(),
                message.getExternalMessageId(),
                message.getProcessed(),
                message.getCreatedAt()
        );
    }
}
