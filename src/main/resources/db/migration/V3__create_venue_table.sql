CREATE TABLE venue
(
    id          UUID PRIMARY KEY     DEFAULT uuidv7(),
    name        TEXT        NOT NULL,
    address     TEXT        NOT NULL,
    status      TEXT        NOT NULL DEFAULT 'ACTIVE',
    created_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    locality_id INT         NOT NULL,
    CONSTRAINT venue_fk_locality FOREIGN KEY (locality_id) REFERENCES locality (id),
    CONSTRAINT venue_name_not_empty CHECK (name = TRIM(name) AND LENGTH(name) > 0),
    CONSTRAINT venue_address_not_empty CHECK (address = TRIM(address) AND LENGTH(address) > 0),
    CONSTRAINT venue_status_valid CHECK (status IN ('ACTIVE', 'ARCHIVED'))
);

CREATE INDEX venue_locality_id_idx ON venue (locality_id);