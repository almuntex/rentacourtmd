CREATE TABLE sport
(
    id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    CONSTRAINT sport_name_not_empty CHECK (name = TRIM(name) AND LENGTH(name) > 0)
);

CREATE UNIQUE INDEX sport_name_unique_idx ON sport (LOWER(name));