CREATE TABLE sessions (
    id                  BIGSERIAL PRIMARY KEY,
    user_id             VARCHAR(255) NOT NULL,
    driver_id           BIGINT       NOT NULL REFERENCES drivers (id),
    vehicle_id          BIGINT       NOT NULL REFERENCES vehicles (id),
    platform_id         BIGINT       NOT NULL REFERENCES platforms (id),
    start_time          TIMESTAMP    NOT NULL,
    end_time            TIMESTAMP,
    initial_mileage     INTEGER      NOT NULL,
    final_mileage       INTEGER,
    initial_fuel_level  INTEGER,
    final_fuel_level    INTEGER,
    notes               VARCHAR(500),
    status              VARCHAR(20)  NOT NULL DEFAULT 'OPEN',
    created_at          TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at          TIMESTAMP    NOT NULL DEFAULT NOW(),

    CONSTRAINT chk_fuel_level_initial CHECK (initial_fuel_level IS NULL OR (initial_fuel_level >= 0 AND initial_fuel_level <= 100)),
    CONSTRAINT chk_fuel_level_final   CHECK (final_fuel_level IS NULL OR (final_fuel_level >= 0 AND final_fuel_level <= 100))
);

CREATE INDEX idx_sessions_user_id    ON sessions (user_id);
CREATE INDEX idx_sessions_vehicle_id ON sessions (vehicle_id);
CREATE INDEX idx_sessions_driver_id  ON sessions (driver_id);
CREATE INDEX idx_sessions_status     ON sessions (status);