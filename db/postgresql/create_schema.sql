--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- CLEANSED SCHEMA
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- ArtistEntity
--------------------------------------------------------------------------------

CREATE TABLE discogs.artist (
	id INTEGER PRIMARY KEY,
	name TEXT[],
	realname TEXT[],
	profile TEXT[]);

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
    id INTEGER PRIMARY KEY,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);
-- NOTE: the URIs are empty, so no suitable primary key can be selected!

CREATE TABLE discogs.image_of_artist (
	artist_id INTEGER,
	image_id INTEGER);

ALTER TABLE discogs.image_of_artist ADD PRIMARY KEY (artist_id, image_id);
ALTER TABLE discogs.image_of_artist ADD FOREIGN KEY (artist_id) REFERENCES discogs.artist(id);
ALTER TABLE discogs.image_of_artist ADD FOREIGN KEY (image_id) REFERENCES discogs.artist_image(id);

--------------------------------------------------------------------------------
-- LabelEntity
--------------------------------------------------------------------------------

CREATE TABLE discogs.label (
	id INTEGER PRIMARY KEY,
	name TEXT,
	contactinfo TEXT,
	profile TEXT,
	data_quality TEXT);

CREATE TABLE discogs.label_urls (
	id INTEGER,
	url TEXT);
ALTER TABLE discogs.label_urls ADD PRIMARY KEY (id, url);
ALTER TABLE discogs.label_urls ADD FOREIGN KEY (id) REFERENCES discogs.label(id);

CREATE TABLE discogs.sublabel (
	id INTEGER,
	name TEXT);
ALTER TABLE discogs.sublabel ADD PRIMARY KEY (id);

CREATE TABLE discogs.sublabel_of (
	label_id INTEGER,
	sublabel_id INTEGER);

ALTER TABLE discogs.sublabel_of ADD PRIMARY KEY (label_id, sublabel_id);
ALTER TABLE discogs.sublabel_of ADD FOREIGN KEY (label_id) REFERENCES discogs.label(id);
ALTER TABLE discogs.sublabel_of ADD FOREIGN KEY (sublabel_id) REFERENCES discogs.sublabel(id);

CREATE TABLE discogs.label_images (
    id INTEGER PRIMARY KEY,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE TABLE discogs.image_of_label (
	image_id INTEGER,
	label_id INTEGER);

ALTER TABLE discogs.image_of_label ADD PRIMARY KEY (image_id, label_id);
ALTER TABLE discogs.image_of_label ADD FOREIGN KEY (label_id) REFERENCES discogs.label(id);
ALTER TABLE discogs.image_of_label ADD FOREIGN KEY (image_id) REFERENCES discogs.label_images(id);

--------------------------------------------------------------------------------
-- MasterEntity
--------------------------------------------------------------------------------

CREATE TABLE discogs.master (
	id INTEGER PRIMARY KEY,
	year INTEGER,
	data_quality TEXT,
	title TEXT,
	main_release INTEGER);

CREATE TABLE discogs.master_styles (
	id INTEGER,
	style TEXT);
ALTER TABLE discogs.master_styles ADD PRIMARY KEY (id, style);
ALTER TABLE discogs.master_styles ADD FOREIGN KEY (id) REFERENCES discogs.master(id);

CREATE TABLE discogs.master_genres (
	id INTEGER,
	genre TEXT);
ALTER TABLE discogs.master_genres ADD PRIMARY KEY (id, genre);
ALTER TABLE discogs.master_genres ADD FOREIGN KEY (id) REFERENCES discogs.master(id);

CREATE TABLE discogs.master_images (
    id INTEGER PRIMARY KEY,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE TABLE discogs.images_of_master (
	image_id INTEGER,
	master_id INTEGER);

ALTER TABLE discogs.images_of_master ADD PRIMARY KEY (image_id, master_id);
ALTER TABLE discogs.images_of_master ADD FOREIGN KEY (master_id) REFERENCES discogs.master(id);
ALTER TABLE discogs.images_of_master ADD FOREIGN KEY (image_id) REFERENCES discogs.master_images(id);

CREATE TABLE discogs.master_artist (
	id INTEGER PRIMARY KEY,
	name TEXT[],
	role TEXT[],
	join_att TEXT[],
	anv TEXT[]);

CREATE TABLE discogs.master_artist_performs(
	master_id INTEGER,
	artist_id INTEGER);

ALTER TABLE discogs.master_artist_performs ADD PRIMARY KEY (master_id, artist_id);
ALTER TABLE discogs.master_artist_performs ADD FOREIGN KEY (master_id) REFERENCES discogs.master(id);
ALTER TABLE discogs.master_artist_performs ADD FOREIGN KEY (artist_id) REFERENCES discogs.master_artist(id);
--------------------------------------------------------------------------------
-- ReleaseEntity
--------------------------------------------------------------------------------

CREATE TABLE discogs.release (
	id INTEGER PRIMARY KEY,
	released TEXT,
	country TEXT,
	notes TEXT,
	status TEXT,
	title TEXT,
	data_quality TEXT);

CREATE TABLE discogs.release_styles (
	id INTEGER,
	style TEXT);
ALTER TABLE discogs.release_styles ADD PRIMARY KEY (id, style);
ALTER TABLE discogs.release_styles ADD FOREIGN KEY (id) REFERENCES discogs.release(id);

CREATE TABLE discogs.release_genres (
	id INTEGER,
	genre TEXT);
ALTER TABLE discogs.release_genres ADD PRIMARY KEY (id, genre);
ALTER TABLE discogs.release_genres ADD FOREIGN KEY (id) REFERENCES discogs.release(id);

CREATE TABLE discogs.release_artist (
	id INTEGER PRIMARY KEY,
	name TEXT[],
	role TEXT[],
	join_att TEXT[],
	anv TEXT[]);

CREATE TABLE discogs.artist_of_release (
	release_id INTEGER,
	artist_id INTEGER);

ALTER TABLE discogs.artist_of_release ADD PRIMARY KEY (release_id, artist_id);
ALTER TABLE discogs.artist_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.artist_of_release ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_artist(id);

CREATE TABLE discogs.release_extraartist (
	id INTEGER PRIMARY KEY,
	name TEXT[],
	role TEXT[],
	join_att TEXT[],
	anv TEXT[]);

CREATE TABLE discogs.extraartist_of_release (
	release_id INTEGER,
	artist_id INTEGER);

ALTER TABLE discogs.extraartist_of_release ADD PRIMARY KEY (release_id, artist_id);
ALTER TABLE discogs.extraartist_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.extraartist_of_release ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_extraartist(id);

CREATE TABLE discogs.release_identifier (
    id NUMERIC PRIMARY KEY,
	value TEXT,
	type TEXT,
	description TEXT);

CREATE TABLE discogs.identifies (
	release_id INTEGER,
	identifier_id NUMERIC);

ALTER TABLE discogs.identifies ADD PRIMARY KEY (release_id, identifier_id);
ALTER TABLE discogs.identifies ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.identifies ADD FOREIGN KEY (identifier_id) REFERENCES discogs.release_identifier(id);

CREATE TABLE discogs.release_video (
    id NUMERIC PRIMARY KEY,
	src TEXT,
	duration TEXT,
	description TEXT,
	title TEXT,
	embed TEXT); -- maybe boolean

CREATE TABLE discogs.video_of_release (
	release_id INTEGER,
	video_id NUMERIC);

ALTER TABLE discogs.video_of_release ADD PRIMARY KEY (release_id, video_id);
ALTER TABLE discogs.video_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.video_of_release ADD FOREIGN KEY (video_id) REFERENCES discogs.release_video(id);

CREATE TABLE discogs.release_company (
	id INTEGER PRIMARY KEY,
	resource_url TEXT,
	name TEXT,
	entity_type TEXT,
	entity_type_value TEXT,
	catno TEXT);

CREATE TABLE discogs.company_of_release (
	release_id INTEGER,
	company_id INTEGER);
ALTER TABLE discogs.company_of_release ADD PRIMARY KEY (release_id, company_id);
ALTER TABLE discogs.company_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.company_of_release ADD FOREIGN KEY (company_id) REFERENCES discogs.release_company(id);

CREATE TABLE discogs.release_image (
    id INTEGER PRIMARY KEY,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE TABLE discogs.image_of_release (
	image_id INTEGER,
	release_id INTEGER);
ALTER TABLE discogs.image_of_release ADD PRIMARY KEY (image_id, release_id);
ALTER TABLE discogs.image_of_release ADD FOREIGN KEY (image_id) REFERENCES discogs.release_image(id);
ALTER TABLE discogs.image_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);

CREATE TABLE discogs.release_label (
	id INTEGER PRIMARY KEY,
	catno TEXT[],
	name TEXT[]);

CREATE TABLE discogs.label_of_release (
	label_id INTEGER,
	release_id INTEGER);

ALTER TABLE discogs.label_of_release ADD PRIMARY KEY (label_id, release_id);
ALTER TABLE discogs.label_of_release ADD FOREIGN KEY (label_id) REFERENCES discogs.release_label(id);
ALTER TABLE discogs.label_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
