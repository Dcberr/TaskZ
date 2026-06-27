package dcberr.taskz.modules.task.support;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class TaskAssignees {

    private TaskAssignees() {}

    public static List<String> normalize(List<String> assignees) {
        if (assignees == null || assignees.isEmpty()) {
            return List.of();
        }

        Map<String, String> normalized = new LinkedHashMap<>();
        for (String assignee : assignees) {
            if (assignee == null || assignee.isBlank()) {
                continue;
            }

            String trimmed = assignee.trim();
            normalized.putIfAbsent(
                    trimmed.toLowerCase(Locale.ROOT),
                    trimmed
            );
        }

        return List.copyOf(normalized.values());
    }
}
