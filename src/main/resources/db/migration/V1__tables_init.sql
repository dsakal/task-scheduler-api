CREATE TABLE buckets (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE tasks (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    priority VARCHAR(50),
    image VARCHAR(255),
    due_date DATE,
    created_at TIME,
    user_id BIGINT REFERENCES users(id),
    bucket_id BIGINT REFERENCES buckets(id)
);

CREATE TABLE task_user (
    task_id BIGINT REFERENCES tasks(id),
    user_id BIGINT REFERENCES users(id),
    PRIMARY KEY (task_id, user_id)
);
