package dcberr.taskz.modules.task.service;

import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;

import org.springframework.data.domain.Pageable;

import dcberr.taskz.common.dto.PageResponse;
import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskStatus;
import dcberr.taskz.modules.task.dto.TaskEventResponse;
import dcberr.taskz.modules.task.entity.Task;

public interface TaskEventService {

    void recordTaskCreated(Task task);

    void recordStatusChanged(
            UUID taskId,
            TaskStatus oldStatus,
            LocalDateTime oldCompletedAt,
            TaskStatus newStatus,
            LocalDateTime newCompletedAt
    );

    void recordPriorityChanged(UUID taskId, Priority oldPriority, Priority newPriority);

    void recordAssigneeChanged(UUID taskId, String oldAssignee, String newAssignee);

    PageResponse<TaskEventResponse> getEventsByTaskId(UUID taskId, Pageable pageable);
}
