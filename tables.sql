CREATE TABLE questions (
                           id SERIAL PRIMARY KEY,
                           question_text TEXT NOT NULL,
                           ip_address INET,
                           is_read BOOLEAN NOT NULL DEFAULT false
);