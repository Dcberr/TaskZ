package dcberr.taskz.modules.message.service;

import dcberr.taskz.modules.message.dto.MessageResponse;
import dcberr.taskz.modules.message.dto.MockMessageRequest;

public interface MessageService {

    MessageResponse receiveMessage(
            MockMessageRequest request
    );
}