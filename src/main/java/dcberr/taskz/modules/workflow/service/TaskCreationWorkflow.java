package dcberr.taskz.modules.workflow.service;

import dcberr.taskz.modules.workflow.dto.MessageContext;

public interface TaskCreationWorkflow {

    void processMessage(
            MessageContext context
    );
}
