CREATE TABLE discogs.artist (
    id INTEGER,
    name text NOT NULL,
CONSTRAINT discogs_artist_pkey PRIMARY KEY (id));

CREATE TABLE discogs.artist_alias (
    artist INTEGER,
    alias text,
CONSTRAINT discogs_artist_alias_pkey PRIMARY KEY (artist, alias),
CONSTRAINT discogs_artist_artist_fk FOREIGN KEY (artist)
    REFERENCES discogs.artist(id));

    CREATE TABLE discogs.artist_relation (
        musician INTEGER NOT NULL,
        "group" INTEGER NOT NULL,
    CONSTRAINT discogs_artist_relation_pkey PRIMARY KEY (musician, "group"),
    CONSTRAINT discogs_artist_relation_musician_fk FOREIGN KEY (musician)
        REFERENCES discogs.artist(id),
    CONSTRAINT discogs_artist_relation_group_fk FOREIGN KEY ("group")
        REFERENCES discogs.artist(id));

        CREATE TABLE discogs.country (
            name text,
        CONSTRAINT discogs_country_pkey PRIMARY KEY (name));

        CREATE TABLE discogs.format (
            id	    serial,
            name    text UNIQUE,
        CONSTRAINT discogs_format_pkey PRIMARY KEY (id));

        CREATE TABLE discogs.master (
            id INTEGER,
            title text,
        CONSTRAINT discogs_master_pkey PRIMARY KEY (id));

        CREATE TABLE discogs.release (
            id INTEGER,
            title text,
            master INTEGER,
            barcode text,
        CONSTRAINT discogs_release_pkey PRIMARY KEY (id));

        CREATE TABLE discogs.medium (
    release INTEGER,
    position INTEGER,
    format INTEGER,
CONSTRAINT discogs_medium_pkey PRIMARY KEY (release, position),
CONSTRAINT discogs_medium_format_fk FOREIGN KEY (format)
    REFERENCES discogs.format (id),
CONSTRAINT discogs_medium_release_fk FOREIGN KEY (release)
    REFERENCES discogs.release (id));

    CREATE TABLE discogs.label (
        id INTEGER,
        name text NOT NULL,
    CONSTRAINT discogs_label_pkey PRIMARY KEY (id));

    CREATE TABLE discogs.label_relation (
    parent INTEGER,
    child INTEGER,
CONSTRAINT discogs_label_relation_pkey PRIMARY KEY (parent, child));

CREATE TABLE discogs.release_artist (
    release INTEGER,
    position INTEGER,
    artist INTEGER,
    join_phrase text,
CONSTRAINT discogs_release_artist_pkey PRIMARY KEY (release, position),
CONSTRAINT discogs_release_artist_artist_fk FOREIGN KEY (artist)
    REFERENCES discogs.artist(id),
CONSTRAINT discogs_release_artist_release_fk FOREIGN KEY (release)
    REFERENCES discogs.release(id));

    CREATE TABLE discogs.release_label (
    release INTEGER,
    label INTEGER,
    catno text,
CONSTRAINT discogs_release_label_pkey PRIMARY KEY (release, label),
CONSTRAINT discogs_release_label_release_fk FOREIGN KEY (release)
    REFERENCES discogs.release(id),
CONSTRAINT discogs_release_label_label_fk FOREIGN KEY (label)
    REFERENCES discogs.label(id));

    CREATE TABLE discogs.release_country (
        release INTEGER,
        country text,
        date text,
    CONSTRAINT discogs_release_country_pkey PRIMARY KEY (release, country),
    CONSTRAINT discogs_release_country_release_fk FOREIGN KEY (release)
        REFERENCES discogs.release(id),
    CONSTRAINT discogs_release_country_country_fk FOREIGN KEY (country)
        REFERENCES discogs.country(name));

        CREATE TABLE discogs.master_artist (
            master INTEGER,
            position INTEGER,
            artist INTEGER,
            join_phrase text,
        CONSTRAINT discogs_master_artist_pkey PRIMARY KEY (master, position),
        CONSTRAINT discogs_master_artist_master_fk FOREIGN KEY (master)
            REFERENCES discogs.master(id),
        CONSTRAINT discogs_master_artist_artist_fk FOREIGN KEY (artist)
            REFERENCES discogs.artist(id));

            CREATE TABLE discogs.track (
                id text,
                title text,
                duration INTEGER,
                position text,
                number INTEGER,
                release INTEGER,
            CONSTRAINT discogs_track_pkey PRIMARY KEY (id),
            CONSTRAINT discogs_track_release_fk FOREIGN KEY (release)
                REFERENCES discogs.release(id));

                CREATE TABLE discogs.track_artist (
                    track text,
                    position text,
                    artist INTEGER,
                    join_phrase text,
                CONSTRAINT discogs_track_artist_pkey PRIMARY KEY (track, position),
                CONSTRAINT discogs_track_artist_track_fk FOREIGN KEY (track)
                    REFERENCES discogs.track(id),
                CONSTRAINT discogs_track_artist_artist_fk FOREIGN KEY (artist)
                    REFERENCES discogs.artist(id));
