CREATE TABLE quizzes (
  quiz_id UUID PRIMARY KEY,
  quiz_name VARCHAR(255),
  created_at TIMESTAMP WITH TIME ZONE,
  created_by BIGINT,
  quiz_difficulty VARCHAR(50)
)