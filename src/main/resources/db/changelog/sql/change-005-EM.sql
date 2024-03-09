CREATE TABLE quiz_assignations (
    assignation_id BIGINT PRIMARY KEY,
    quiz_id UUID,
    question_id BIGINT,
    assigned_at TIMESTAMP WITH TIME ZONE,
    assigned_by BIGINT
)