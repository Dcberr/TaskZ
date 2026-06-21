package dcberr.taskz.modules.task.service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dcberr.taskz.common.dto.PageResponse;
import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskEventType;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.dto.TaskEventResponse;
import dcberr.taskz.modules.task.entity.Task;
import dcberr.taskz.modules.task.entity.TaskEvent;
import dcberr.taskz.modules.task.exception.TaskNotFoundException;
import dcberr.taskz.modules.task.mapper.TaskEventMapper;
import dcberr.taskz.modules.task.repository.TaskEventRepository;
import dcberr.taskz.modules.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskEventServiceImpl implements TaskEventService {

    private final TaskEventRepository taskEventRepository;
    private final TaskRepository taskRepository;

    @Override
    public void recordTaskCreated(Task task) {
        taskEventRepository.save(
                TaskEvent.builder()
                        .taskId(task.getId())
                        .eventType(TaskEventType.TASK_CREATED)
                        .oldValue(null)
                        .newValue(toJson(snapshot(task)))
                        .build()
        );
    }

    @Override
    public void recordStatusChanged(
            UUID taskId,
            TaskStatus oldStatus,
            LocalDateTime oldCompletedAt,
            TaskStatus newStatus,
            LocalDateTime newCompletedAt
    ) {

        TaskEventType eventType = newStatus == TaskStatus.COMPLETED
                ? TaskEventType.TASK_COMPLETED
                : newStatus == TaskStatus.CANCELLED
                ? TaskEventType.TASK_CANCELLED
                : TaskEventType.STATUS_CHANGED;

        taskEventRepository.save(
                TaskEvent.builder()
                        .taskId(taskId)
                        .eventType(eventType)
                        .oldValue(toJson(statusPayload(oldStatus, oldCompletedAt)))
                        .newValue(toJson(statusPayload(newStatus, newCompletedAt)))
                        .build()
        );
    }

    @Override
    public void recordPriorityChanged(
            UUID taskId,
            Priority oldPriority,
            Priority newPriority
    ) {

        taskEventRepository.save(
                TaskEvent.builder()
                        .taskId(taskId)
                        .eventType(TaskEventType.PRIORITY_CHANGED)
                        .oldValue(toJson(valueMap("priority", oldPriority)))
                        .newValue(toJson(valueMap("priority", newPriority)))
                        .build()
        );
    }

    @Override
    public void recordAssigneeChanged(
            UUID taskId,
            String oldAssignee,
            String newAssignee
    ) {

        taskEventRepository.save(
                TaskEvent.builder()
                        .taskId(taskId)
                        .eventType(TaskEventType.ASSIGNEE_CHANGED)
                        .oldValue(toJson(valueMap("assignee", oldAssignee)))
                        .newValue(toJson(valueMap("assignee", newAssignee)))
                        .build()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public PageResponse<TaskEventResponse> getEventsByTaskId(
            UUID taskId,
            Pageable pageable
    ) {

        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Task not found: " + taskId);
        }

        return PageResponse.from(
                taskEventRepository.findByTaskId(taskId, pageable)
                        .map(TaskEventMapper::toResponse)
        );
    }

    private Map<String, Object> snapshot(Task task) {
        Map<String, Object> snapshot = new LinkedHashMap<>();
        snapshot.put("id", task.getId());
        snapshot.put("title", task.getTitle());
        snapshot.put("description", task.getDescription());
        snapshot.put("requester", task.getRequester());
        snapshot.put("assignee", task.getAssignee());
        snapshot.put("dueDateTime", task.getDueDateTime());
        snapshot.put("priority", task.getPriority());
        snapshot.put("status", task.getStatus());
        snapshot.put("aiConfidence", task.getAiConfidence());
        snapshot.put("source", task.getSource());
        snapshot.put("sourceMessageId", task.getSourceMessageId());
        snapshot.put("createdAt", task.getCreatedAt());
        snapshot.put("updatedAt", task.getUpdatedAt());
        snapshot.put("completedAt", task.getCompletedAt());
        return snapshot;
    }

    private Map<String, Object> valueMap(String field, Object value) {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put(field, value);
        return values;
    }

    private Map<String, Object> statusPayload(TaskStatus status, LocalDateTime completedAt) {
        Map<String, Object> values = new LinkedHashMap<>();
        values.put("status", status);
        values.put("completedAt", completedAt);
        return values;
    }

    private String toJson(Object value) {
        if (value == null) {
            return null;
        }

        if (value instanceof Map<?, ?> map) {
            return mapToJson(map);
        }

        return "\"" + escapeJson(value.toString()) + "\"";
    }

    private String mapToJson(Map<?, ?> map) {
        StringBuilder builder = new StringBuilder("{");
        boolean first = true;

        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                builder.append(',');
            }

            first = false;
            builder.append("\"")
                    .append(escapeJson(String.valueOf(entry.getKey())))
                    .append("\":")
                    .append(toJsonValue(entry.getValue()));
        }

        builder.append('}');
        return builder.toString();
    }

    private String toJsonValue(Object value) {
        if (value == null) {
            return "null";
        }

        if (value instanceof Map<?, ?> map) {
            return mapToJson(map);
        }

        if (value instanceof Number || value instanceof Boolean) {
            return value.toString();
        }

        return "\"" + escapeJson(value.toString()) + "\"";
    }

    private String escapeJson(String value) {
        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }
}
