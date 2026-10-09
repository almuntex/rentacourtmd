CREATE TABLE court
(
    id         UUID PRIMARY KEY     DEFAULT uuidv7(),
    name       TEXT        NOT NULL,
    venue_id   UUID        NOT NULL,
    indoor     BOOLEAN     NOT NULL DEFAULT FALSE,
    status     TEXT        NOT NULL DEFAULT 'ACTIVE',
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT court_fk_venue FOREIGN KEY (venue_id) REFERENCES venue (id),
    CONSTRAINT court_name_not_empty CHECK (name = TRIM(name) AND LENGTH(name) > 0),
    CONSTRAINT court_status_valid CHECK (status IN ('ACTIVE', 'ARCHIVED'))
);

CREATE UNIQUE INDEX court_name_unique_per_venue_idx ON court (venue_id, LOWER(name)) WHERE status <> 'ARCHIVED';
CREATE INDEX court_venue_id_idx ON court (venue_id);

CREATE TABLE court_sport
(
    court_id UUID NOT NULL,
    sport_id INT NOT NULL,
    CONSTRAINT court_sport_pk PRIMARY KEY (court_id, sport_id),
    CONSTRAINT court_sport_fk_court FOREIGN KEY (court_id) REFERENCES court (id) ON DELETE CASCADE,
    CONSTRAINT court_sport_fk_sport FOREIGN KEY (sport_id) REFERENCES sport (id) ON DELETE RESTRICT
);

CREATE INDEX court_sport_sport_id_idx ON court_sport (sport_id);