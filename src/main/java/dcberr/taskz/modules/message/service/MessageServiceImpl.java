package dcberr.taskz.modules.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dcberr.taskz.modules.message.dto.MessageResponse;
import dcberr.taskz.modules.message.dto.MockMessageRequest;
import dcberr.taskz.modules.message.entity.RawMessage;
import dcberr.taskz.modules.message.mapper.MessageMapper;
import dcberr.taskz.modules.message.repository.RawMessageRepository;
import dcberr.taskz.modules.workflow.dto.MessageContext;
import dcberr.taskz.modules.workflow.service.TaskCreationWorkflow;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl
        implements MessageService {

    private final RawMessageRepository rawMessageRepository;
    private final TaskCreationWorkflow taskCreationWorkflow;

    @Override
    public MessageResponse receiveMessage(
            MockMessageRequest request
    ) {

        log.info(
                "Received mock message from {}",
                request.sender()
        );

        RawMessage message =
                RawMessage.builder()
                        .sender(request.sender())
                        .content(request.content())
                        .processed(false)
                        .build();

        RawMessage saved =
                rawMessageRepository.save(message);

        taskCreationWorkflow.processMessage(
                new MessageContext(
                        saved.getId(),
                        saved.getSender(),
                        saved.getContent()
                )
        );

        return MessageMapper.toResponse(saved);
    }
}
