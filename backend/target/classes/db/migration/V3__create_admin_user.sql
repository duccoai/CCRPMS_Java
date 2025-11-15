-- Tạo role ADMIN nếu chưa có
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM roles WHERE name = 'ADMIN') THEN
        INSERT INTO roles (name) VALUES ('ADMIN');
    END IF;
END
$$;

-- Tạo user admin nếu chưa có
DO $$
DECLARE
    admin_role_id INT;
BEGIN
    SELECT id INTO admin_role_id FROM roles WHERE name = 'ADMIN';

    IF NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin') THEN
        INSERT INTO users (username, password, full_name, email, active, role_id)
        VALUES (
            'admin',               -- username
            '123456',              -- password plain
            'Administrator',       -- full name
            'admin@example.com',   -- email
            TRUE,                  -- active
            admin_role_id
        );
    END IF;
END
$$;
