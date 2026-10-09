CREATE TABLE locality
(
    id   INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name TEXT NOT NULL,
    CONSTRAINT locality_name_not_empty CHECK (name = TRIM(name) AND LENGTH(name) > 0)
);

CREATE UNIQUE INDEX locality_name_upper_unique_idx ON locality (LOWER(name));
