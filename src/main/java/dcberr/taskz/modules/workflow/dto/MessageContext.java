package dcberr.taskz.modules.workflow.dto;

import java.util.UUID;

public record MessageContext(

        UUID messageId,

        String sender,

        String content

) {
}
