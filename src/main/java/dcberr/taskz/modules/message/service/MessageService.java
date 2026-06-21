package dcberr.taskz.modules.message.service;

import dcberr.taskz.modules.message.dto.InboundMessageCommand;
import dcberr.taskz.modules.message.dto.MessageResponse;

public interface MessageService {

    MessageResponse receiveMessage(
            InboundMessageCommand command
    );
}
