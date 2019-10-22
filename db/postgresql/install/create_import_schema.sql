CREATE SCHEMA discogs;

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
-- IMPORT SCHEMA
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

--------------------------------------------------------------------------------
-- ArtistEntity
--------------------------------------------------------------------------------

CREATE UNLOGGED TABLE discogs.artist_import (
	id INTEGER,
	name TEXT,
	realname TEXT,
	data_quality TEXT,
	profile TEXT);
-- TODO: add check clause for dataquality

CREATE UNLOGGED TABLE discogs.artist_namevariations_import (
	id INTEGER,
	name_variation TEXT);

CREATE UNLOGGED TABLE discogs.artist_alias_import (
	id INTEGER,
	alias TEXT);

CREATE UNLOGGED TABLE discogs.alias_of_artist_import (
	artist_id INTEGER,
	alias_id INTEGER,
	alias_name TEXT);

-- TODO: primary key?
CREATE UNLOGGED TABLE discogs.artist_image_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);
-- NOTE: the URIs are empty, so no suitable primary key can be selected!

CREATE UNLOGGED TABLE discogs.image_of_artist_import (
	image_id INTEGER,
	artist_id INTEGER);

--ALTER TABLE discogs.image_of_artist_import ADD FOREIGN KEY (id) REFERENCES discogs.artist(id);

--------------------------------------------------------------------------------
-- LabelEntity
--------------------------------------------------------------------------------

CREATE UNLOGGED TABLE discogs.label_import (
	id INTEGER,
	name TEXT,
	contactinfo TEXT,
	profile TEXT,
	data_quality TEXT);

CREATE UNLOGGED TABLE discogs.label_urls_import (
	id INTEGER,
	url TEXT);

CREATE UNLOGGED TABLE discogs.sublabel_import (
	id INTEGER,
	name TEXT);

CREATE UNLOGGED TABLE discogs.sublabel_of_import (
	label_id INTEGER,
	sublabel_id INTEGER);

CREATE UNLOGGED TABLE discogs.label_images_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE UNLOGGED TABLE discogs.image_of_label_import (
	image_id INTEGER,
	label_id INTEGER);

--------------------------------------------------------------------------------
-- MasterEntity
--------------------------------------------------------------------------------

CREATE UNLOGGED TABLE discogs.master_import (
	id INTEGER,
	year INTEGER,
	data_quality TEXT,
	title TEXT,
	main_release INTEGER);

CREATE UNLOGGED TABLE discogs.master_styles_import (
	id INTEGER,
	style TEXT);

CREATE UNLOGGED TABLE discogs.master_genres_import (
	id INTEGER,
	genre TEXT);

CREATE UNLOGGED TABLE discogs.master_images_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	height INTEGER);

CREATE UNLOGGED TABLE discogs.images_of_master_import (
	image_id INTEGER,
	master_id INTEGER);

CREATE UNLOGGED TABLE discogs.master_artist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE UNLOGGED TABLE discogs.master_artist_performs_import (
	master_id INTEGER,
	artist_id INTEGER);

CREATE UNLOGGED TABLE discogs.master_video_import (
	id INTEGER,
	embed TEXT,
	source TEXT,
	description TEXT,
	duration TEXT,
	title TEXT);

CREATE UNLOGGED TABLE discogs.video_of_master_import (
	video_id INTEGER,
	master_id INTEGER);

--------------------------------------------------------------------------------
-- ReleaseEntity
--------------------------------------------------------------------------------

CREATE UNLOGGED TABLE discogs.release_import (
	id INTEGER,
	released TEXT,
	country TEXT,
	notes TEXT,
	status TEXT,
	title TEXT,
	data_quality TEXT);

CREATE UNLOGGED TABLE discogs.release_styles_import (
	id INTEGER,
	style TEXT);

CREATE UNLOGGED TABLE discogs.release_genres_import (
	id INTEGER,
	genre TEXT);

CREATE UNLOGGED TABLE discogs.release_artist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE UNLOGGED TABLE discogs.artist_of_release_import (
	release_id INTEGER,
	artist_id INTEGER);

CREATE UNLOGGED TABLE discogs.release_extraartist_import (
	id INTEGER,
	name TEXT,
	role TEXT,
	join_att TEXT,
	anv TEXT);

CREATE UNLOGGED TABLE discogs.extraartist_of_release_import (
	release_id INTEGER,
	artist_id INTEGER);

CREATE UNLOGGED TABLE discogs.release_identifier_import (
    id NUMERIC,
	value TEXT,
	type TEXT,
	description TEXT);

CREATE UNLOGGED TABLE discogs.identifies_import (
	release_id INTEGER,
	identifier_id NUMERIC);

--Note: primary key removed
CREATE UNLOGGED TABLE discogs.release_video_import (
    id NUMERIC,
	src TEXT,
	duration TEXT,
	description TEXT,
	title TEXT,
	embed TEXT); -- maybe boolean

CREATE UNLOGGED TABLE discogs.video_of_release_import (
	release_id INTEGER,
	video_id NUMERIC);

CREATE UNLOGGED TABLE discogs.release_company_import (
	id INTEGER,
	resource_url TEXT,
	name TEXT,
	entity_type TEXT,
	entity_type_value TEXT,
	catno TEXT);

CREATE UNLOGGED TABLE discogs.company_of_release_import (
	release_id INTEGER,
	company_id INTEGER);

CREATE UNLOGGED TABLE discogs.release_image_import (
    id INTEGER,
	uri TEXT,
	uri150 TEXT,
	type TEXT,
	width INTEGER,
	heigth INTEGER);

CREATE UNLOGGED TABLE discogs.image_of_release_import (
	image_id INTEGER,
	release_id INTEGER);

CREATE UNLOGGED TABLE discogs.release_label_import (
	id INTEGER,
	catno TEXT,
	label_id INTEGER,
	name TEXT);

CREATE UNLOGGED TABLE discogs.label_of_release_import (
	label_id INTEGER,
	release_id INTEGER);

CREATE UNLOGGED TABLE discogs.release_track_import (
	id INTEGER,
	position TEXT,
	title TEXT,
	duration TEXT);

CREATE UNLOGGED TABLE discogs.track_of_release_import(
	track_id INTEGER,
	release_id INTEGER);

CREATE UNLOGGED TABLE discogs.release_format_import (
	id INTEGER,
	name TEXT,
	qty TEXT,
	fmt_text TEXT);

CREATE UNLOGGED TABLE discogs.format_of_release_import (
	format_id INTEGER,
	release_id INTEGER);

CREATE UNLOGGED TABLE discogs.release_format_description_import (
	format_id INTEGER,
	description TEXT);
