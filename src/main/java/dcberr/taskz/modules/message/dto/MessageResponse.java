package dcberr.taskz.modules.message.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record MessageResponse(

        UUID id,

        String sender,

        String content,

        Boolean processed,

        LocalDateTime createdAt

) {
}
