CREATE TABLE vehicles (
    id          BIGSERIAL PRIMARY KEY,
    user_id     VARCHAR(255) NOT NULL,
    brand       VARCHAR(100) NOT NULL,
    model       VARCHAR(100) NOT NULL,
    plate       VARCHAR(20)  NOT NULL,
    year_model  INTEGER,
    active      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_vehicles_user_id ON vehicles (user_id);
CREATE UNIQUE INDEX idx_vehicles_user_plate ON vehicles (user_id, plate);