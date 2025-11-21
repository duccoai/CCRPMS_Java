CREATE TABLE promotion_requests (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    flight_hours INT NOT NULL,
    performance_result TEXT,
    certificates TEXT,
    team_lead_suggestion TEXT,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT unique_promo_for_user UNIQUE (user_id)
);
