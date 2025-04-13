CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(10) NOT NULL CHECK (role IN ('ADMIN', 'USER'))
);

ALTER TABLE users ADD CONSTRAINT unique_email UNIQUE (email);

INSERT INTO users (name, email, password, role)
VALUES (
    'Admin',
    'contato@simplesdental.com',
    '$2a$10$2rQRFPRRn/X3r4zbZX7DoeeJjJkhOGLO9FEf8ZS4biSVrIpOewHc2',
    'ADMIN'
);
