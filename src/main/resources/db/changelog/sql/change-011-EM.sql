CREATE TABLE notifications (
    notification_id BIGINT PRIMARY KEY,
    send_when TIMESTAMP WITH TIME ZONE,
    notification_type VARCHAR(30),
    send_at TIMESTAMP WITH TIME ZONE,
    user_mail VARCHAR(255),
    learning_object UUID,
    notification_status VARCHAR(255)
)