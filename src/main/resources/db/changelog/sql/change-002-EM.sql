CREATE TABLE close_questions (
    question_id BIGINT PRIMARY KEY,
    question_content CHARACTER VARYING(255),
    correct_answer CHARACTER VARYING(255),
    answer_a CHARACTER VARYING(255),
    answer_b CHARACTER VARYING(255),
    answer_c CHARACTER VARYING(255),
    answer_d CHARACTER VARYING(255)
)