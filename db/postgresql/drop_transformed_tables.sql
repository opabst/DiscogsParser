-- ARTIST TABLES
DROP TABLE discogs.artist CASCADE;
DROP TABLE discogs.artist_namevariations CASCADE;
DROP TABLE discogs.artist_alias CASCADE;
DROP TABLE discogs.alias_of_artist CASCADE;
DROP TABLE discogs.artist_image CASCADE;
DROP TABLE discogs.image_of_artist CASCADE;

-- LABEL TABLES
DROP TABLE discogs.label CASCADE;
DROP TABLE discogs.label_urls CASCADE;
DROP TABLE discogs.sublabel CASCADE;
DROP TABLE discogs.sublabel_of CASCADE;
DROP TABLE discogs.label_images CASCADE;
DROP TABLE discogs.image_of_label CASCADE;

-- MASTER TABLES
DROP TABLE discogs.master CASCADE;
DROP TABLE discogs.master_styles CASCADE;
DROP TABLE discogs.master_genres CASCADE;
DROP TABLE discogs.master_images CASCADE;
DROP TABLE discogs.images_of_master CASCADE;
DROP TABLE discogs.master_artist CASCADE;
DROP TABLE discogs.master_artist_performs CASCADE;

-- RELEASE TABLES
DROP TABLE discogs.release CASCADE;
DROP TABLE discogs.release_styles CASCADE;
DROP TABLE discogs.release_genres CASCADE;
DROP TABLE discogs.release_artist CASCADE;
DROP TABLE discogs.artist_of_release CASCADE;
DROP TABLE discogs.release_extraartist CASCADE;
DROP TABLE discogs.extraartist_of_release CASCADE;
DROP TABLE discogs.release_identifier CASCADE;
DROP TABLE discogs.identifies CASCADE;
DROP TABLE discogs.release_video CASCADE;
DROP TABLE discogs.video_of_release CASCADE;
DROP TABLE discogs.release_company CASCADE;
DROP TABLE discogs.company_of_release CASCADE;
DROP TABLE discogs.release_image CASCADE;
DROP TABLE discogs.image_of_release CASCADE;
DROP TABLE discogs.release_label CASCADE;
DROP TABLE discogs.label_of_release CASCADE;
