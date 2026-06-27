package dcberr.taskz.modules.message.provider;

import org.springframework.stereotype.Component;

import dcberr.taskz.common.enums.MessageSource;
import dcberr.taskz.modules.message.dto.InboundMessageCommand;
import dcberr.taskz.modules.message.dto.MessageResponse;
import dcberr.taskz.modules.message.dto.MockMessageRequest;
import dcberr.taskz.modules.message.service.MessageService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MockMessageProvider implements MessageProvider {

    private final MessageService messageService;

    @Override
    public MessageSource source() {
        return MessageSource.MOCK;
    }

    @Override
    public MessageResponse receiveMessage(InboundMessageCommand command) {
        return messageService.receiveMessage(command);
    }

    public MessageResponse receiveMessage(MockMessageRequest request) {
        return receiveMessage(
                new InboundMessageCommand(
                        request.sender(),
                        request.content(),
                        source(),
                        request.channelName(),
                        request.conversationId(),
                        request.externalMessageId(),
                        request.conversationMessages(),
                        request.participants()
                )
        );
    }
}
