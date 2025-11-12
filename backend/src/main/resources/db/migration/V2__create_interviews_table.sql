-- ==============================================
--  Migration: V2__create_interviews_table.sql
--  Purpose: Tạo bảng phỏng vấn (interviews)
--  Author: CCRPMS Backend Migration
--  Date: 2025-11-10
-- ==============================================

CREATE TABLE IF NOT EXISTS interviews (
    id BIGSERIAL PRIMARY KEY,
    application_id BIGINT NOT NULL,
    interview_date TIMESTAMP WITH TIME ZONE NOT NULL,
    location VARCHAR(255),
    score DOUBLE PRECISION,
    comment TEXT,
    note TEXT,
    status VARCHAR(50) DEFAULT 'SCHEDULED',

    CONSTRAINT fk_interview_application
        FOREIGN KEY (application_id)
        REFERENCES applications(id)
        ON DELETE CASCADE
);

-- ==============================================
--  Index for performance
-- ==============================================
CREATE INDEX IF NOT EXISTS idx_interview_application_id
    ON interviews(application_id);

-- ==============================================
--  Insert sample interview data (optional demo)
-- ==============================================
-- INSERT INTO interviews (application_id, interview_date, location, note, status)
-- VALUES 
-- (1, '2025-11-12T09:00:00+07', 'Room A101', 'Vòng 1 kỹ thuật', 'SCHEDULED'),
-- (2, '2025-11-13T14:30:00+07', 'Room B204', 'Phỏng vấn HR', 'SCHEDULED');
