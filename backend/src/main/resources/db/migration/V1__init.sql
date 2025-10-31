-- V1__init.sql
CREATE TABLE roles (
  id SERIAL PRIMARY KEY,
  name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE users (
  id BIGSERIAL PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  password VARCHAR(255) NOT NULL,
  email VARCHAR(150),
  full_name VARCHAR(200),
  role_id INTEGER REFERENCES roles(id),
  active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT NOW(),
  updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE campaigns (
  id BIGSERIAL PRIMARY KEY,
  code VARCHAR(100) NOT NULL UNIQUE,
  title VARCHAR(255) NOT NULL,
  description TEXT,
  start_date DATE,
  end_date DATE,
  status VARCHAR(50)
);

CREATE TABLE applications (
  id BIGSERIAL PRIMARY KEY,
  candidate_name VARCHAR(200),
  candidate_email VARCHAR(150),
  campaign_id BIGINT REFERENCES campaigns(id),
  status VARCHAR(50) DEFAULT 'PENDING',
  submitted_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE audit_logs (
  id BIGSERIAL PRIMARY KEY,
  actor VARCHAR(150),
  action VARCHAR(255),
  details TEXT,
  created_at TIMESTAMP DEFAULT NOW()
);

-- Insert default roles
INSERT INTO roles (name) VALUES ('ADMIN'), ('RECRUITER'), ('CANDIDATE');
