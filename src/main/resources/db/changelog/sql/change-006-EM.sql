CREATE TABLE user_assignations (
    id BIGINT PRIMARY KEY,
    user_id BIGINT,
    learning_object_id UUID,
    assigned_at TIMESTAMP WITH TIME ZONE,
    completed_at TIMESTAMP WITH TIME ZONE,
    user_assignation_status CHARACTER VARYING(30)
)