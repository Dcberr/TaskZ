package dcberr.taskz.modules.message.provider;

import dcberr.taskz.common.enums.MessageSource;
import dcberr.taskz.modules.message.dto.InboundMessageCommand;
import dcberr.taskz.modules.message.dto.MessageResponse;

public interface MessageProvider {

    MessageSource source();

    MessageResponse receiveMessage(InboundMessageCommand command);
}
