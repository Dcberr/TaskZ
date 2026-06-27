CREATE TABLE task_assignees
(
    task_id UUID NOT NULL,

    position INTEGER NOT NULL,

    assignee VARCHAR(255) NOT NULL,

    CONSTRAINT pk_task_assignees
        PRIMARY KEY (task_id, position),

    CONSTRAINT fk_task_assignees_task
        FOREIGN KEY (task_id) REFERENCES tasks (id)
        ON DELETE CASCADE
);

INSERT INTO task_assignees (task_id, position, assignee)
SELECT id, 0, trim(assignee)
FROM tasks
WHERE assignee IS NOT NULL
  AND trim(assignee) <> '';

CREATE INDEX idx_task_assignees_lower_assignee
    ON task_assignees (lower(assignee));

ALTER TABLE tasks
DROP COLUMN assignee;
