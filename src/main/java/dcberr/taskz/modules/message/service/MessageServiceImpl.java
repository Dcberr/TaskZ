package dcberr.taskz.modules.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dcberr.taskz.modules.message.dto.InboundMessageCommand;
import dcberr.taskz.modules.message.dto.MessageResponse;
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
            InboundMessageCommand command
    ) {

        log.info(
                "Received {} message from {}",
                command.source(),
                command.sender()
        );

        RawMessage message =
                RawMessage.builder()
                        .sender(command.sender())
                        .content(command.content())
                        .source(command.source())
                        .channelName(command.channelName())
                        .conversationId(command.conversationId())
                        .externalMessageId(command.externalMessageId())
                        .processed(false)
                        .build();

        RawMessage saved =
                rawMessageRepository.save(message);

        if (saved.getExternalMessageId() == null ||
                saved.getExternalMessageId().isBlank()) {
                saved.setExternalMessageId(
                        saved.getId().toString()
                );
                saved = rawMessageRepository.save(saved);
        }

        taskCreationWorkflow.processMessage(
                new MessageContext(
                        saved.getId(),
                        saved.getSender(),
                        saved.getContent(),
                        saved.getSource(),
                        saved.getChannelName(),
                        saved.getConversationId(),
                        saved.getExternalMessageId(),
                        command.conversationMessages(),
                        command.participants()
                )
        );

        return MessageMapper.toResponse(saved);
    }
}
