CREATE OR REPLACE SCHEMA discogs;

--------------------------------------------------------------------------------
-- ArtistEntity
--------------------------------------------------------------------------------

CREATE TABLE discogs.artist (
	id INTEGER PRIMARY KEY,
	name TEXT,
	realname TEXT,
	data_quality TEXT,
	profile TEXT);
// TODO: add check clause for dataquality

CREATE TABLE discogs.artist_namevariations (
	id INTEGER,
	name_variation TEXT);
	
ALTER TABLE discogs.artist_namevariations ADD PRIMARY KEY (id, name_variation);
ALTER TABLE discogs.artist_namevariations ADD FOREIGN KEY (id) REFERENCES discogs.artist(id);

CREATE TABLE discogs.artist_alias (
	id INTEGER,
	alias TEXT);
	
ALTER TABLE discogs.artist_alias ADD PRIMARY KEY (id, alias);

CREATE TABLE discogs.alias_of_artist (
	artist_id INTEGER,
	alias_id INTEGER,
	alias_name TEXT);

ALTER TABLE discogs.alias_of_artist ADD PRIMARY KEY (artist_id, alias_id, alias_name);
ALTER TABLE discogs.alias_of_artist ADD FOREIGN KEY (artist_id) REFERENCES discogs.artist(id);
ALTER TABLE discogs.alias_of_artist ADD FOREIGN KEY (alias_id, alias_name) REFERENCES discogs.artist_alias(id, alias);

CREATE TABLE discogs.artist_image (
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);
// NOTE: the URIs are empty, so no suitable primary key can be selected!

CREATE TABLE discogs.image_of_artist (
	id INTEGER,
	uri TEXT);

ALTER TABLE discogs.image_of_artist ADD FOREIGN KEY (id) REFERENCES discogs.artist(id);
