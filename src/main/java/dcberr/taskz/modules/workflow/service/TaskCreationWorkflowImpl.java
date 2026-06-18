package dcberr.taskz.modules.workflow.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskSource;
import dcberr.taskz.modules.ai.client.AIClient;
import dcberr.taskz.modules.ai.dto.AnalyzeResponse;
import dcberr.taskz.modules.task.dto.CreateTaskRequest;
import dcberr.taskz.modules.task.mapper.PriorityMapper;
import dcberr.taskz.modules.task.service.TaskService;
import dcberr.taskz.modules.workflow.dto.MessageContext;
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
                        context.content()
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

                    analysis.assignee(),

                    PriorityMapper.from(
                    analysis.priority()
                    ),

            OffsetDateTime.parse(
                                analysis.dueDateTime()
                        ),

                    analysis.confidence(),

                    TaskSource.MOCK,

                    context.messageId().toString()
            );

        taskService.createTask(
                request
        );

        log.info(
                "Task created successfully from message {}",
                context.messageId()
        );
        }
}
