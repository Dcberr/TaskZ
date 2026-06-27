package dcberr.taskz.modules.ai.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import tools.jackson.databind.ObjectMapper;

class AnalyzeResponseTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void deserializesPluralAssigneesContract() throws Exception {
        AnalyzeResponse response = objectMapper.readValue(
                """
                {
                  "isTask": true,
                  "confidence": 0.95,
                  "title": "Review PR #124",
                  "description": "Review pull request #124 and provide actionable feedback.",
                  "assignees": ["Chien", "Linh"],
                  "dueDateTime": "2026-06-28T10:00:00+07:00",
                  "priority": "URGENT"
                }
                """,
                AnalyzeResponse.class
        );

        assertTrue(response.isTask());
        assertEquals(List.of("Chien", "Linh"), response.assignees());
    }

    @Test
    void missingAssigneesDefaultsToEmptyList() throws Exception {
        AnalyzeResponse response = objectMapper.readValue(
                """
                {
                  "isTask": true,
                  "confidence": 0.95,
                  "title": "Review PR #124",
                  "description": "Review pull request #124.",
                  "dueDateTime": null,
                  "priority": "MEDIUM"
                }
                """,
                AnalyzeResponse.class
        );

        assertEquals(List.of(), response.assignees());
    }
}
