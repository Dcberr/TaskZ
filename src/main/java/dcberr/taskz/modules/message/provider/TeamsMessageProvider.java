package dcberr.taskz.modules.message.provider;

import org.springframework.stereotype.Component;

import dcberr.taskz.common.enums.MessageSource;
import dcberr.taskz.modules.message.dto.InboundMessageCommand;
import dcberr.taskz.modules.message.dto.MessageResponse;

@Component
public class TeamsMessageProvider implements MessageProvider {

    @Override
    public MessageSource source() {
        return MessageSource.TEAMS;
    }

    @Override
    public MessageResponse receiveMessage(InboundMessageCommand command) {
        throw new UnsupportedOperationException(
                "Teams message provider is not implemented yet"
        );
    }
}
