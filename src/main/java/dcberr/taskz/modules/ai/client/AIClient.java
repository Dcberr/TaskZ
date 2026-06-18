package dcberr.taskz.modules.ai.client;

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
            String message
    ) {

        return restClient.post()
                .uri("/analyze")
                .body(
                        new AnalyzeRequest(
                                message
                        )
                )
                .retrieve()
                .body(
                        AnalyzeResponse.class
                );
    }
}