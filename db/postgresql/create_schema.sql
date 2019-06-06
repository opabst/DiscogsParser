CREATE SCHEMA discogs;

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- IMPORT SCHEMA
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- ArtistEntity
--------------------------------------------------------------------------------

-- NOTE: primary key removed
CREATE TABLE discogs.artist_import (
	id INTEGER,
	name TEXT,
	realname TEXT,
	data_quality TEXT,
	profile TEXT);
-- TODO: add check clause for dataquality

CREATE TABLE discogs.artist_namevariations_import (
	id INTEGER,
	name_variation TEXT);

--ALTER TABLE discogs.artist_namevariations_import ADD PRIMARY KEY (id, name_variation);
--ALTER TABLE discogs.artist_namevariations_import ADD FOREIGN KEY (id) REFERENCES discogs.artist(id);

CREATE TABLE discogs.artist_alias_import (
	id INTEGER,
	alias TEXT);

--ALTER TABLE discogs.artist_alias_import ADD PRIMARY KEY (id, alias);

CREATE TABLE discogs.alias_of_artist_import (
	artist_id INTEGER,
	alias_id INTEGER,
	alias_name TEXT);

--ALTER TABLE discogs.alias_of_artist_import ADD PRIMARY KEY (artist_id, alias_id, alias_name);
--ALTER TABLE discogs.alias_of_artist_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.artist(id);
--ALTER TABLE discogs.alias_of_artist_import ADD FOREIGN KEY (alias_id, alias_name) REFERENCES discogs.artist_alias(id, alias);

-- TODO: primary key?
CREATE TABLE discogs.artist_image_import (
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);
-- NOTE: the URIs are empty, so no suitable primary key can be selected!

CREATE TABLE discogs.image_of_artist_import (
	id INTEGER,
	uri TEXT);

--ALTER TABLE discogs.image_of_artist_import ADD FOREIGN KEY (id) REFERENCES discogs.artist(id);

--------------------------------------------------------------------------------
-- LabelEntity
--------------------------------------------------------------------------------

-- NOTE: primary key removed
CREATE TABLE discogs.label_import (
	id INTEGER,
	name TEXT,
	contactinfo TEXT,
	profile TEXT,
	data_quality TEXT);

CREATE TABLE discogs.label_urls_import (
	id INTEGER,
	url TEXT);

--ALTER TABLE discogs.label_urls_import ADD PRIMARY KEY (id, url);
--ALTER TABLE discogs.label_urls_import ADD FOREIGN KEY (id) REFERENCES discogs.label(id);

CREATE TABLE discogs.sublabel_import (
	id INTEGER,
	name TEXT);
--ALTER TABLE discogs.sublabel_import ADD PRIMARY KEY (id);

CREATE TABLE discogs.sublabel_of_import (
	label_id INTEGER,
	sublabel_id INTEGER);

--ALTER TABLE discogs.sublabel_of_import ADD PRIMARY KEY (label_id, sublabel_id);
--ALTER TABLE discogs.sublabel_of_import ADD FOREIGN KEY (label_id) REFERENCES discogs.label(id);
--ALTER TABLE discogs.sublabel_of_import ADD FOREIGN KEY (sublabel_id) REFERENCES discogs.sublabel(id);

CREATE TABLE discogs.label_images_import (
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

--ALTER TABLE discogs.label_images_import ADD PRIMARY KEY (uri);

CREATE TABLE discogs.image_of_label_import (
	uri TEXT,
	label_id INTEGER);

--ALTER TABLE discogs.image_of_label_import ADD FOREIGN KEY (label_id) REFERENCES discogs.label(id);
--ALTER TABLE discogs.image_of_label_import ADD FOREIGN KEY (uri) REFERENCES discogs.label_images(uri);

--------------------------------------------------------------------------------
-- MasterEntity
--------------------------------------------------------------------------------

-- NOTE: primary key removed
CREATE TABLE discogs.master_import (
	id INTEGER,
	year INTEGER,
	data_quality TEXT,
	title TEXT,
	main_release INTEGER);

CREATE TABLE discogs.master_styles_import (
	id INTEGER,
	style TEXT);

--ALTER TABLE discogs.master_styles_import ADD PRIMARY KEY (id, style);
--ALTER TABLE discogs.master_styles_import ADD FOREIGN KEY (id) REFERENCES discogs.master(id);

CREATE TABLE discogs.master_genres_import (
	id INTEGER,
	genre TEXT);

--ALTER TABLE discogs.master_genres_import ADD PRIMARY KEY (id, genre);
--ALTER TABLE discogs.master_genres_import ADD FOREIGN KEY (id) REFERENCES discogs.master(id);

-- NOTE: primary key removed
CREATE TABLE discogs.master_images_import (
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE TABLE discogs.images_of_master_import (
	uri TEXT,
	master_id INTEGER);

--ALTER TABLE discogs.images_of_master_import ADD FOREIGN KEY (master_id) REFERENCES discogs.master(id);
--ALTER TABLE discogs.images_of_master_import ADD FOREIGN KEY (uri) REFERENCES discogs.master_images(uri);

-- NOTE: primary key removed
CREATE TABLE discogs.master_artist_import (
	id INTEGER ,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.master_artist_performs_import (
	master_id INTEGER,
	artist_id INTEGER);

--ALTER TABLE discogs.master_artist_performs_import ADD PRIMARY KEY (master_id, artist_id);
--ALTER TABLE discogs.master_artist_performs_import ADD FOREIGN KEY (master_id) REFERENCES discogs.master(id);
--ALTER TABLE discogs.master_artist_performs_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.master_artist(id);

--------------------------------------------------------------------------------
-- ReleaseEntity
--------------------------------------------------------------------------------
--NOTE: primary key removed
CREATE TABLE discogs.release_import (
	id INTEGER,
	released TEXT,
	country TEXT,
	notes TEXT,
	status TEXT,
	title TEXT,
	data_quality TEXT);

CREATE TABLE discogs.release_styles_import (
	id INTEGER,
	style TEXT);

--ALTER TABLE discogs.release_styles_import ADD PRIMARY KEY (id, style);
--ALTER TABLE discogs.release_styles_import ADD FOREIGN KEY (id) REFERENCES discogs.release(id);

CREATE TABLE discogs.release_genres_import (
	id INTEGER,
	genre TEXT);

--ALTER TABLE discogs.release_genres_import ADD PRIMARY KEY (id, genre);
--ALTER TABLE discogs.release_genres_import ADD FOREIGN KEY (id) REFERENCES discogs.release(id);

-- NOTE: primary key removed
CREATE TABLE discogs.release_artist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.artist_of_release_import (
	release_id INTEGER,
	artist_id INTEGER);

--ALTER TABLE discogs.artist_of_release_import ADD PRIMARY KEY (release_id, artist_id);
--ALTER TABLE discogs.artist_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
--ALTER TABLE discogs.artist_of_release_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_artist(id);

-- Note: primary key removed
CREATE TABLE discogs.release_extraartist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.extraartist_of_release_import (
	release_id INTEGER,
	artist_id INTEGER);

--ALTER TABLE discogs.extraartist_of_release_import ADD PRIMARY KEY (release_id, artist_id);
--ALTER TABLE discogs.extraartist_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
--ALTER TABLE discogs.extraartist_of_release_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_extraartist(id);

--Note: primary key removed
CREATE TABLE discogs.release_identifier_import (
	value TEXT,
	type TEXT,
	description TEXT);

CREATE TABLE discogs.identifies_import (
	release_id INTEGER,
	identifier_value TEXT);

--ALTER TABLE discogs.identifies_import ADD PRIMARY KEY (release_id, identifier_value);
--ALTER TABLE discogs.identifies_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
--ALTER TABLE discogs.identifies_import ADD FOREIGN KEY (identifier_value) REFERENCES discogs.release_identifier(value);

--Note: primary key removed
CREATE TABLE discogs.release_video_import (
	src TEXT,
	duration TEXT,
	description TEXT,
	title TEXT,
	embed TEXT); -- maybe boolean

CREATE TABLE discogs.video_of_release_import (
	release_id INTEGER,
	video_src TEXT);

--ALTER TABLE discogs.video_of_release_import ADD PRIMARY KEY (release_id, video_src);
--ALTER TABLE discogs.video_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
--ALTER TABLE discogs.video_of_release_import ADD FOREIGN KEY (video_src) REFERENCES discogs.release_video(src);

--Note: primary key removed
CREATE TABLE discogs.release_company_import (
	id INTEGER,
	resource_url TEXT,
	name TEXT,
	entity_type TEXT,
	entity_type_value TEXT,
	catno TEXT);

CREATE TABLE discogs.company_of_release_import (
	release_id INTEGER,
	company_id INTEGER);

--ALTER TABLE discogs.company_of_release_import ADD PRIMARY KEY (release_id, company_id);
--ALTER TABLE discogs.company_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
--ALTER TABLE discogs.company_of_release_import ADD FOREIGN KEY (company_id) REFERENCES discogs.release_company(id);

--Note: primary key removed
CREATE TABLE discogs.release_image_import (
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	heigth INTEGER);

CREATE TABLE discogs.image_of_release_import (
	uri TEXT,
	release_id INTEGER);

--ALTER TABLE discogs.image_of_release_import ADD PRIMARY KEY (uri, release_id);
--ALTER TABLE discogs.image_of_release_import ADD FOREIGN KEY (uri) REFERENCES discogs.release_image(uri);
--ALTER TABLE discogs.image_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);

--Note: primary key removed
CREATE TABLE discogs.release_label_import (
	id INTEGER,
	catno TEXT,
	name TEXT);

CREATE TABLE discogs.label_of_release_import (
	label_id INTEGER,
	release_id INTEGER);

--ALTER TABLE discogs.label_of_release_import ADD PRIMARY KEY (label_id, release_id);
--ALTER TABLE discogs.label_of_release_import ADD FOREIGN KEY (label_id) REFERENCES discogs.release_label(id);
--ALTER TABLE discogs.label_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);

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
	name TEXT,
	realname TEXT,
	profile TEXT);

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
-- NOTE: the URIs are empty, so no suitable primary key can be selected!

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
ALTER TABLE discogs.sublabel ADD PRIMARY KEY (id);

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
--ALTER TABLE discogs.label_images ADD PRIMARY KEY (uri);

CREATE TABLE discogs.image_of_label (
	uri TEXT,
	label_id INTEGER);
ALTER TABLE discogs.image_of_label ADD FOREIGN KEY (label_id) REFERENCES discogs.label(id);
--ALTER TABLE discogs.image_of_label ADD FOREIGN KEY (uri) REFERENCES discogs.label_images(uri);

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
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

-- ALTER TABLE discogs.master_images ADD PRIMARY KEY (uri);

CREATE TABLE discogs.images_of_master (
	uri TEXT,
	master_id INTEGER);
ALTER TABLE discogs.images_of_master ADD FOREIGN KEY (master_id) REFERENCES discogs.master(id);
--ALTER TABLE discogs.images_of_master ADD FOREIGN KEY (uri) REFERENCES discogs.master_images(uri);

CREATE TABLE discogs.master_artist (
	id INTEGER PRIMARY KEY,
	name TEXT,
	role TEXT,
	join_att TEXT,
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
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.artist_of_release (
	release_id INTEGER,
	artist_id INTEGER);
ALTER TABLE discogs.artist_of_release ADD PRIMARY KEY (release_id, artist_id);
ALTER TABLE discogs.artist_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.artist_of_release ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_artist(id);

CREATE TABLE discogs.release_extraartist (
	id INTEGER PRIMARY KEY,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.extraartist_of_release (
	release_id INTEGER,
	artist_id INTEGER);
ALTER TABLE discogs.extraartist_of_release ADD PRIMARY KEY (release_id, artist_id);
ALTER TABLE discogs.extraartist_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.extraartist_of_release ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_extraartist(id);

CREATE TABLE discogs.release_identifier (
	value TEXT PRIMARY KEY,
	type TEXT,
	description TEXT);

CREATE TABLE discogs.identifies (
	release_id INTEGER,
	identifier_value TEXT);
ALTER TABLE discogs.identifies ADD PRIMARY KEY (release_id, identifier_value);
ALTER TABLE discogs.identifies ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.identifies ADD FOREIGN KEY (identifier_value) REFERENCES discogs.release_identifier(value);

CREATE TABLE discogs.release_video (
	src TEXT PRIMARY KEY,
	duration TEXT,
	description TEXT,
	title TEXT,
	embed TEXT); -- maybe boolean

CREATE TABLE discogs.video_of_release (
	release_id INTEGER,
	video_src TEXT);
ALTER TABLE discogs.video_of_release ADD PRIMARY KEY (release_id, video_src);
ALTER TABLE discogs.video_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.video_of_release ADD FOREIGN KEY (video_src) REFERENCES discogs.release_video(src);

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
	uri TEXT PRIMARY KEY,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

ALTER TABLE discogs.release_image ADD PRIMARY KEY (uri);

CREATE TABLE discogs.image_of_release (
	uri TEXT,
	release_id INTEGER);
--ALTER TABLE discogs.image_of_release ADD PRIMARY KEY (uri, release_id);
--ALTER TABLE discogs.image_of_release ADD FOREIGN KEY (uri) REFERENCES discogs.release_image(uri);
ALTER TABLE discogs.image_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);

CREATE TABLE discogs.release_label (
	id INTEGER PRIMARY KEY,
	catno TEXT,
	name TEXT);

CREATE TABLE discogs.label_of_release (
	label_id INTEGER,
	release_id INTEGER);
ALTER TABLE discogs.label_of_release ADD PRIMARY KEY (label_id, release_id);
ALTER TABLE discogs.label_of_release ADD FOREIGN KEY (label_id) REFERENCES discogs.release_label(id);
ALTER TABLE discogs.label_of_release ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
