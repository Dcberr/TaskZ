CREATE TABLE task_events
(
    id UUID PRIMARY KEY,

    task_id UUID NOT NULL,

    event_type VARCHAR(50) NOT NULL,

    old_value TEXT,

    new_value TEXT,

    created_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_task_events_task
        FOREIGN KEY (task_id) REFERENCES tasks (id)
);

CREATE INDEX idx_task_events_task_id_created_at
    ON task_events (task_id, created_at);
