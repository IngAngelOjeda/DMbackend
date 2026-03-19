CREATE TABLE driver_platforms (
    driver_id   BIGINT NOT NULL REFERENCES drivers (id) ON DELETE CASCADE,
    platform_id BIGINT NOT NULL REFERENCES platforms (id) ON DELETE CASCADE,
    PRIMARY KEY (driver_id, platform_id)
);