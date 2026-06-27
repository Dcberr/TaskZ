package dcberr.taskz.modules.ai.dto;

import java.util.List;

public record AnalyzeResponse(
        boolean isTask,

        double confidence,

        String title,

        String description,

        List<String> assignees,

        String dueDateTime,

        String priority
) {
    public AnalyzeResponse {
        assignees = assignees == null ? List.of() : List.copyOf(assignees);
    }
}
