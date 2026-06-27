package dcberr.taskz.modules.ai.dto;

import java.util.List;

public record AnalyzeRequest(
        String correlationId,

        String currentMessage,

        List<String> conversationMessages,

        List<String> participants
) {
        public AnalyzeRequest {
        conversationMessages = conversationMessages == null
                ? List.of()
                : List.copyOf(conversationMessages);
        participants = participants == null ? List.of() : List.copyOf(participants);
    }
}
