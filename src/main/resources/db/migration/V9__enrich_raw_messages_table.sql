ALTER TABLE raw_messages
ADD COLUMN source VARCHAR(50);

ALTER TABLE raw_messages
ADD COLUMN channel_name VARCHAR(255);

ALTER TABLE raw_messages
ADD COLUMN conversation_id VARCHAR(255);

ALTER TABLE raw_messages
ADD COLUMN external_message_id VARCHAR(255);

UPDATE raw_messages
SET source = 'MOCK'
WHERE source IS NULL;

UPDATE raw_messages
SET external_message_id = id::text
WHERE external_message_id IS NULL;

ALTER TABLE raw_messages
ALTER COLUMN source SET NOT NULL;
