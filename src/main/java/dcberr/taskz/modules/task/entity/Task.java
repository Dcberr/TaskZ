package dcberr.taskz.modules.task.entity;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import dcberr.taskz.common.entity.BaseEntity;
import dcberr.taskz.common.enums.Priority;
import dcberr.taskz.common.enums.TaskSource;
import dcberr.taskz.common.enums.TaskStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String requester;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "ai_confidence")
    private Double aiConfidence;

    @Enumerated(EnumType.STRING)
    private TaskSource source;

    @Column(name = "source_message_id")
    private String sourceMessageId;

    @Column
    private String assignee;

    @Column(name = "due_date_time")
    private OffsetDateTime dueDateTime;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
