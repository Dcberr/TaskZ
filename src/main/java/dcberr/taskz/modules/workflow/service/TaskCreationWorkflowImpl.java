package dcberr.taskz.modules.workflow.service;

import org.springframework.stereotype.Service;

import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.modules.task.dto.CreateTaskRequest;
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

    @Override
    public void processMessage(
            MessageContext context
    ) {

        log.info(
                "Start task creation workflow for message {}",
                context.messageId()
        );

        CreateTaskRequest request =
                new CreateTaskRequest(
                        context.content(),
                        context.content(),
                        context.sender(),
                        Priority.MEDIUM
                );

        taskService.createTask(request);

        log.info(
                "Task created successfully"
        );
    }
}
