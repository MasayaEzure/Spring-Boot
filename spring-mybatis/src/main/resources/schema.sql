CREATE TABLE IF NOT EXISTS teacher (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(60) NOT NULL,
    email VARCHAR(254) NOT NULL
);