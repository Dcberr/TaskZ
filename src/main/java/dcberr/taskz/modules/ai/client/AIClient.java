package dcberr.taskz.modules.ai.client;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import dcberr.taskz.modules.ai.dto.AnalyzeRequest;
import dcberr.taskz.modules.ai.dto.AnalyzeResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AIClient {

    private final RestClient restClient;

    public AnalyzeResponse analyze(
        String correlationId,

        String currentMessage,

        List<String> conversationMessages,

        List<String> participants
    ) {

        return restClient.post()
                .uri("/analyze")
                .body(
                        new AnalyzeRequest(
                                correlationId,
                                currentMessage,
                                conversationMessages,
                                participants
                        )
                )
                .retrieve()
                .body(
                        AnalyzeResponse.class
                );
    }
}