CREATE SCHEMA discogs;

CREATE UNLOGGED TABLE discogs.artist (
    id integer NOT NULL,
    name text NOT NULL,
    realname text,
    urls text[],
    namevariations text[],
    aliases text[],
    releases integer[],
    profile text,
    members text[],
    groups text[],
    data_quality text,
    CONSTRAINT artist_pkey PRIMARY KEY (id));

CREATE UNLOGGED TABLE discogs.artists_image(
    artist_id integer,
    type text,
    height integer,
    width integer,
    image_uri text);

CREATE UNLOGGED TABLE discogs.country (
    name text);

CREATE UNLOGGED TABLE discogs.format(
    name text NOT NULL,
    CONSTRAINT format_pkey PRIMARY KEY (name));

CREATE UNLOGGED TABLE discogs.genre (
    id integer NOT NULL,
    name text,
    parent_genre integer,
    sub_genre integer,
    CONSTRAINT genre_pkey PRIMARY KEY (id));

CREATE UNLOGGED TABLE discogs.label (
    id integer NOT NULL,
    name text NOT NULL,
    contactinfo text,
    profile text,
    parent_label text,
    sublabels text[],
    urls text[],
    data_quality text,
    CONSTRAINT label_pkey PRIMARY KEY (id));

CREATE UNLOGGED TABLE discogs.labels_images (
    label_id integer,
    type text,
    height integer,
    width integer,
    image_uri text);

CREATE UNLOGGED TABLE discogs.master (
    id integer NOT NULL,
    title text,
    main_release integer NOT NULL,
    year integer,
    notes text,
    genres text,
    styles text,
    role text,
    data_quality text,
    CONSTRAINT master_pkey PRIMARY KEY (id));

CREATE UNLOGGED TABLE discogs.master_artists (
    artist_name text,
    master_id integer);

CREATE UNLOGGED TABLE discogs.masters_artists_joins (
    artist1 text,
    artist2 text,
    join_relation text,
    master_id integer);

CREATE UNLOGGED TABLE discogs.masters_extraartists (
    master_id integer,
    artist_name text,
    roles text[]);

CREATE UNLOGGED TABLE discogs.masters_formats (
    master_id integer,
    format_name text,
    qty integer,
    descriptions text[]);

CREATE UNLOGGED TABLE discogs.masters_images (
    master_id integer NOT NULL,
    type text,
    height integer,
    width integer,
    image_uri text,
    CONSTRAINT masters_images_pkey PRIMARY KEY (master_id, type));

CREATE UNLOGGED TABLE discogs.release (
    id integer NOT NULL,
    status text,
    title text,
    country text,
    released text,
    barcode text,
    notes text,
    genres text,
    styles text,
    master_id integer,
    data_quality text,
    CONSTRAINT release_pkey PRIMARY KEY (id));

CREATE UNLOGGED TABLE discogs.releases_artists (
    release_id integer NOT NULL,
    "position" integer NOT NULL,
    artist_id integer,
    artist_name text,
    anv text,
    join_relation text,
    CONSTRAINT releases_artists_pkey PRIMARY KEY (release_id, "position"));

CREATE UNLOGGED TABLE discogs.releases_extraartists (
    release_id integer,
    artist_id integer,
    artist_name text,
    anv text,
    role text);

CREATE UNLOGGED TABLE discogs.releases_formats (
    release_id integer NOT NULL,
    "position" integer NOT NULL,
    format_name text,
    qty numeric(100,0),
    descriptions text[],
    CONSTRAINT releases_formats_pkey PRIMARY KEY (release_id, "position"),
    CONSTRAINT releases_formats_format_name_fkey FOREIGN KEY (format_name)
        REFERENCES discogs.format (name),
    CONSTRAINT releases_formats_release_id_fkey FOREIGN KEY (release_id)
        REFERENCES discogs.release (id));

CREATE UNLOGGED TABLE discogs.releases_images (
    release_id integer NOT NULL,
    type text NOT NULL,
    height integer,
    width integer,
    image_uri text,
    CONSTRAINT releases_images_pkey PRIMARY KEY (release_id, type));

CREATE UNLOGGED TABLE discogs.releases_labels (
    label text NOT NULL,
    release_id integer NOT NULL,
    catno text NOT NULL,
    CONSTRAINT releases_labels_pkey PRIMARY KEY (release_id, label, catno));

CREATE UNLOGGED TABLE discogs.role (
    role_name text NOT NULL,
    CONSTRAINT role_pkey PRIMARY KEY (role_name));

CREATE UNLOGGED TABLE discogs.track (
    release_id integer,
    "position" text,
    track_id text NOT NULL,
    title text,
    duration text,
    trackno integer,
    CONSTRAINT track_pkey PRIMARY KEY (track_id));

CREATE UNLOGGED TABLE discogs.track_artists (
    track_id text NOT NULL,
    "position" integer NOT NULL,
    artist_id integer,
    artist_name text,
    anv text,
    join_relation text,
    CONSTRAINT tracks_artists_pkey PRIMARY KEY (track_id, "position"));

CREATE UNLOGGED TABLE discogs.tracks_extraartists (
    track_id text,
    artist_id integer,
    artist_name text,
    anv text,
    role text,
    data_quality text);
