package dcberr.taskz.modules.workflow.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.modules.ai.client.AIClient;
import dcberr.taskz.modules.ai.dto.AnalyzeResponse;
import dcberr.taskz.modules.task.dto.CreateTaskRequest;
import dcberr.taskz.modules.task.mapper.PriorityMapper;
import dcberr.taskz.modules.task.service.TaskService;
import dcberr.taskz.modules.task.support.TaskAssignees;
import dcberr.taskz.modules.workflow.dto.MessageContext;
import dcberr.taskz.modules.workflow.mapper.MessageSourceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskCreationWorkflowImpl
        implements TaskCreationWorkflow {

    private final TaskService taskService;
    private final AIClient aiClient;

    @Override
    public void processMessage(
            MessageContext context
    ) {

        log.info(
                "Start task creation workflow for message {}",
                context.messageId()
        );

        AnalyzeResponse analysis =
                aiClient.analyze(
                        context.messageId().toString(),
                        context.content(),
                        context.conversationMessages(),
                        context.participants()
                );

        if (!analysis.isTask()) {

            log.info(
                    "Message {} is not a task",
                    context.messageId()
            );

            return;
        }

        if (analysis.title() == null ||
                analysis.title().isBlank()) {

            log.warn(
                    "AI returned empty title"
            );

            return;
        }

        CreateTaskRequest request =
                new CreateTaskRequest(

                        analysis.title(),

                        analysis.description(),

                        context.sender(),

                        TaskAssignees.normalize(analysis.assignees()),

                        PriorityMapper.from(
                                analysis.priority()
                        ),

                        parseDueDateTime(analysis.dueDateTime()),

                        analysis.confidence(),

                        MessageSourceMapper.toTaskSource(context.source()),

                        context.externalMessageId() != null &&
                                !context.externalMessageId().isBlank()
                                ? context.externalMessageId()
                                : context.messageId().toString()
                );

        taskService.createTask(
                request
        );

        log.info(
                "Task created successfully from message {}",
                context.messageId()
        );
    }

    private OffsetDateTime parseDueDateTime(String dueDateTime) {
        if (dueDateTime == null || dueDateTime.isBlank()) {
            return null;
        }

        try {
            return OffsetDateTime.parse(dueDateTime);
        } catch (Exception ex) {
            log.warn("AI returned invalid dueDateTime: {}", dueDateTime);
            return null;
        }
    }
}
