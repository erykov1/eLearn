CREATE TABLE user_results (
    id UUID PRIMARY KEY,
    result INTEGER,
    learning_object_id UUID,
    completed_at TIMESTAMP WITH TIME ZONE,
    user_id BIGINT,
    started_at TIMESTAMP WITH TIME ZONE
)