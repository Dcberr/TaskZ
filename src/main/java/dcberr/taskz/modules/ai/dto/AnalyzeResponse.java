package dcberr.taskz.modules.ai.dto;

public record AnalyzeResponse(
        boolean isTask,

        double confidence,

        String title,

        String description,

        String assignee,

        String dueDateTime,

        String priority
) {
}
