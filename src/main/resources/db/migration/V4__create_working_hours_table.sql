CREATE TABLE venue_operating_hours
(
    venue_id     UUID        NOT NULL,
    day_of_week  SMALLINT    NOT NULL,
    opening_time TIME        NOT NULL,
    closing_time TIME        NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT venue_operating_hours_pk PRIMARY KEY (venue_id, day_of_week),
    CONSTRAINT venue_operating_hours_fk_venue FOREIGN KEY (venue_id) REFERENCES venue (id),
    CONSTRAINT venue_operating_hours_valid_day_of_week CHECK (day_of_week BETWEEN 1 AND 7),
    CONSTRAINT venue_operating_hours_check_closing_hour
        CHECK (opening_time < closing_time OR closing_time = '00:00:00'::TIME)
);

COMMENT ON COLUMN venue_operating_hours.closing_time IS
    'A closing time of 00:00 means the venue closes at midnight (end of day). '
    'If both opening and closing time are 00:00, the venue is open 24 hours.';