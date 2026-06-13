CREATE TABLE raw_messages
(
    id UUID PRIMARY KEY,

    sender VARCHAR(255) NOT NULL,

    content TEXT NOT NULL,

    processed BOOLEAN NOT NULL,

    created_at TIMESTAMP NOT NULL,

    updated_at TIMESTAMP
);