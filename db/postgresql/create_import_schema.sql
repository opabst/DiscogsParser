CREATE SCHEMA discogs;

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- IMPORT SCHEMA
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- ArtistEntity
--------------------------------------------------------------------------------

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

CREATE TABLE discogs.artist_alias_import (
	id INTEGER,
	alias TEXT);

CREATE TABLE discogs.alias_of_artist_import (
	artist_id INTEGER,
	alias_id INTEGER,
	alias_name TEXT);

-- TODO: primary key?
CREATE TABLE discogs.artist_image_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);
-- NOTE: the URIs are empty, so no suitable primary key can be selected!

CREATE TABLE discogs.image_of_artist_import (
	image_id INTEGER,
	artist_id INTEGER);

--ALTER TABLE discogs.image_of_artist_import ADD FOREIGN KEY (id) REFERENCES discogs.artist(id);

--------------------------------------------------------------------------------
-- LabelEntity
--------------------------------------------------------------------------------

CREATE TABLE discogs.label_import (
	id INTEGER,
	name TEXT,
	contactinfo TEXT,
	profile TEXT,
	data_quality TEXT);

CREATE TABLE discogs.label_urls_import (
	id INTEGER,
	url TEXT);

CREATE TABLE discogs.sublabel_import (
	id INTEGER,
	name TEXT);

CREATE TABLE discogs.sublabel_of_import (
	label_id INTEGER,
	sublabel_id INTEGER);

CREATE TABLE discogs.label_images_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE TABLE discogs.image_of_label_import (
	image_id INTEGER,
	label_id INTEGER);

--------------------------------------------------------------------------------
-- MasterEntity
--------------------------------------------------------------------------------

CREATE TABLE discogs.master_import (
	id INTEGER,
	year INTEGER,
	data_quality TEXT,
	title TEXT,
	main_release INTEGER);

CREATE TABLE discogs.master_styles_import (
	id INTEGER,
	style TEXT);

CREATE TABLE discogs.master_genres_import (
	id INTEGER,
	genre TEXT);

CREATE TABLE discogs.master_images_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE TABLE discogs.images_of_master_import (
	image_id INTEGER,
	master_id INTEGER);

CREATE TABLE discogs.master_artist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.master_artist_performs_import (
	master_id INTEGER,
	artist_id INTEGER);



--------------------------------------------------------------------------------
-- ReleaseEntity
--------------------------------------------------------------------------------

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

CREATE TABLE discogs.release_genres_import (
	id INTEGER,
	genre TEXT);

CREATE TABLE discogs.release_artist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.artist_of_release_import (
	release_id INTEGER,
	artist_id INTEGER);

CREATE TABLE discogs.release_extraartist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE TABLE discogs.extraartist_of_release_import (
	release_id INTEGER,
	artist_id INTEGER);

CREATE TABLE discogs.release_identifier_import (
    id NUMERIC,
	value TEXT,
	type TEXT,
	description TEXT);

CREATE TABLE discogs.identifies_import (
	release_id INTEGER,
	identifier_id NUMERIC);

--Note: primary key removed
CREATE TABLE discogs.release_video_import (
    id NUMERIC,
	src TEXT,
	duration TEXT,
	description TEXT,
	title TEXT,
	embed TEXT); -- maybe boolean

CREATE TABLE discogs.video_of_release_import (
	release_id INTEGER,
	video_id NUMERIC);

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

CREATE TABLE discogs.release_image_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	heigth INTEGER);

CREATE TABLE discogs.image_of_release_import (
	image_id INTEGER,
	release_id INTEGER);

CREATE TABLE discogs.release_label_import (
	id INTEGER,
	catno TEXT,
	name TEXT);

CREATE TABLE discogs.label_of_release_import (
	label_id INTEGER,
	release_id INTEGER);
