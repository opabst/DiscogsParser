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
ALTER TABLE discogs.sublabel ADD PRIMARY KEY (id, name);

CREATE TABLE discogs.sublabel_of (
	label_id INTEGER,
	sublabel_id INTEGER);
	
ALTER TABLE discogs.sublabel_of ADD PRIMARY KEY (label_id, sublabel_id);
ALTER TABLE discogs.sublabel_of ADD FOREIGN KEY (label_id) REFERENCES discogs.label(id);
ALTER TABLE discogs.sublabel_of ADD FOREIGN KEY (sublabel_id) REFERENCES discogs.sublabel(id);

CREATE TABLE discogs.label_images (
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);
	
CREATE TABLE discogs.image_of_label (
	uri TEXT,
	label_id INTEGER);
ALTER TABLE discogs.image_of_label ADD FOREIGN KEY (label_id) REFERENCES discogs.label(id);
ALTER TABLE discogs.image_of_label ADD FOREIGN KEY (uri) REFERENCES discogs.label_images(uri);	

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
ALTER TABLE discogs.master_styles ADD FOREIGN KEY (id) REFERENCES discogs.master_styles(id);

CREATE TABLE discogs.master_genres (
	id INTEGER,
	genre TEXT);
ALTER TABLE discogs.master_genres ADD PRIMARY KEY (id, genre);
ALTER TABLE discogs.master_genres ADD FOREIGN KEY (id) REFERENCES discogs.master_genres(id);

CREATE TABLE discogs.master_images (
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);
	
CREATE TABLE discogs.images_of_master (
	uri TEXT,
	master_id INTEGER);
ALTER TABLE discogs.images_of_master ADD FOREIGN KEY (label_id) REFERENCES discogs.master(id);
ALTER TABLE discogs.images_of_master ADD FOREIGN KEY (uri) REFERENCES discogs.master_images(uri);

CREATE TABLE discogs.master_artist (
	id INTEGER PRIMARY KEY,
	name TEXT,
	role TEXT,
	join TEXT,
	anv TEXT);
	
CREATE TABLE discogs.master_artist_performs(
	master_id INTEGER,
	artist_id INTEGER);
ALTER TABLE discogs.master_artist_performs ADD PRIMARY KEY (master_id, artist_id);
ALTER TABLE discogs.master_artist_performs ADD FOREIGN KEY (master_id) REFERENCES discogs.master(id);
ALTER TABLE discogs.master_artist_performs ADD FOREIGN KEY (artist_id) REFERENCES discogs.master_artist(id);
--------------------------------------------------------------------------------
-- ReleaseEntity
--------------------------------------------------------------------------------