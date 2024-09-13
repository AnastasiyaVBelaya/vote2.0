CREATE SCHEMA app
    AUTHORIZATION postgres;

CREATE TABLE app.artist
(
    id bigserial PRIMARY KEY,
    name character varying (50) NOT NULL,
    genre character varying (50),
    biography text,
    status character varying(20) NOT NULL DEFAULT 'ACTIVE',
    dateCreated TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    lastModified TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE IF EXISTS app.artist
    OWNER TO postgres;

CREATE TABLE app.genre
(
    id bigserial PRIMARY KEY,
    name character varying (50) NOT NULL,
    description text,
    status character varying(20) NOT NULL DEFAULT 'ACTIVE',
    dateCreated TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    lastModified TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE IF EXISTS app.genre
    OWNER TO postgres;

CREATE TABLE app.vote
(
    id bigserial PRIMARY KEY,
    artist_id bigserial NOT NULL,
    about text NOT NULL,
    dateCreated TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (artist_id) REFERENCES app.artist (id)
);

ALTER TABLE IF EXISTS app.vote
    OWNER TO postgres;

CREATE TABLE app.cross_vote_genre
(
    vote_id bigserial NOT NULL,
    genre_id bigserial NOT NULL,
    FOREIGN KEY (vote_id) REFERENCES app.vote (id),
    FOREIGN KEY (genre_id) REFERENCES app.genre (id),
    UNIQUE (vote_id, genre_id)
);

ALTER TABLE IF EXISTS app.cross_vote_genre
    OWNER TO postgres;
