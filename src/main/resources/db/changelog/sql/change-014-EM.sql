ALTER TABLE close_questions
ADD COLUMN media_link CHARACTER VARYING(255);

ALTER TABLE open_questions
ADD COLUMN media_link CHARACTER VARYING(255);