-- Users table
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL
);

-- Projects table
CREATE TABLE IF NOT EXISTS projects (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Jobs table
CREATE TABLE IF NOT EXISTS jobs (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES users(id),
    project_id UUID REFERENCES projects(id),
    status VARCHAR(20) NOT NULL,
    request_payload TEXT,
    result_payload TEXT,
    error_message TEXT,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_jobs_user_id ON jobs(user_id);
CREATE INDEX IF NOT EXISTS idx_jobs_status ON jobs(status);

-- Sample data
INSERT INTO users (id, email, name) VALUES 
    ('a1b2c3d4-e5f6-4a7b-8c9d-0e1f2a3b4c5d', 'alice@example.com', 'Alice'),
    ('b2c3d4e5-f6a7-4b8c-9d0e-1f2a3b4c5d6e', 'john@example.com', 'John')
ON CONFLICT DO NOTHING;

INSERT INTO projects (id, name) VALUES 
    ('c3d4e5f6-a7b8-4c9d-0e1f-2a3b4c5d6e7f', 'Project Alpha'),
    ('d4e5f6a7-b8c9-4d0e-1f2a-3b4c5d6e7f8a', 'Project Beta')
ON CONFLICT DO NOTHING;
