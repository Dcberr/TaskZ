CREATE TABLE tasks
(
    id UUID PRIMARY KEY,

    title VARCHAR(500) NOT NULL,

    description TEXT,

    requester VARCHAR(255) NOT NULL,

    priority VARCHAR(50),

    status VARCHAR(50),

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP
);