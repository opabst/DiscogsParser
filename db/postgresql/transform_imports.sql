
-- transformation for artist tables
-- '#' used as delimiter to seperate realnames and profiles from different records

-- There can be more than one record for an id, but with different names; these records will aggregated by aggregating
-- the atributes with a delimiter. 
INSERT INTO discogs.artist (id, name, realname, profile)
    SELECT id, STRING_AGG(name, ' ''#'' '), STRING_AGG(REPLACE(realname, ',', ' ''#'' '), ' ''#'' '), STRING_AGG(profile, '''#''')
    FROM discogs.artist_import
    WHERE data_quality IN ('CORRECT', 'COMPLETE_AND_CORRECT')
    GROUP BY id
    ORDER BY id;

INSERT INTO discogs.artist_namevariations (id, name_variation)
    SELECT DISTINCT id, name_variation
    FROM discogs.artist_namevariations_import
    WHERE id IN (SELECT id
                 FROM discogs.artist);

INSERT INTO discogs.artist_alias (id, alias)
    SELECT DISTINCT id, alias
    FROM discogs.artist_alias_import
    WHERE alias IS NOT NULL;

INSERT INTO discogs.alias_of_artist (artist_id, alias_id, alias_name)
    SELECT DISTINCT artist_id, alias_id, alias_name
    FROM discogs.alias_of_artist_import
    WHERE alias_name IS NOT NULL
      AND artist_id IN (SELECT id
                        FROM discogs.artist)
      AND alias_id IN (SELECT id
                       FROM discogs.artist_alias);

INSERT INTO discogs.artist_image (uri, uri150, type, width, height)
    SELECT *
    FROM discogs.artist_image_import;

INSERT INTO discogs.image_of_artist (id, uri)
    SELECT *
    FROM discogs.image_of_artist_import
    WHERE id IN (SELECT id
                 FROM discogs.artist);

-- transformation for label tables

INSERT INTO discogs.label (id, name, contactinfo, profile)
    SELECT id, name, contactinfo, profile
    FROM discogs.label_import
    WHERE data_quality IN ('CORRECT', 'COMPLETE_AND_CORRECT');

INSERT INTO discogs.label_urls (id, url)
    SELECT DISTINCT id, url
    FROM discogs.label_urls_import
    WHERE id IN (SELECT id
                 FROM discogs.label);

INSERT INTO discogs.sublabel (id, name)
    SELECT id, name
    FROM discogs.sublabel_import;

INSERT INTO discogs.sublabel_of (label_id, sublabel_id)
    SELECT DISTINCT label_id, sublabel_id
    FROM discogs.sublabel_of_import
    WHERE label_id IN (SELECT id
                       FROM discogs.label);

INSERT INTO discogs.label_images (uri, uri150, type, width, height)
    SELECT *
    FROM discogs.label_images_import;

INSERT INTO discogs.image_of_label (uri, label_id)
    SELECT *
    FROM discogs.image_of_label_import
    WHERE label_id IN (SELECT id
                       FROM discogs.label);

-- transformation for master tables
INSERT INTO discogs.master(id, year, title, main_release)
    SELECT id, year, title, main_release
    FROM discogs.master_import
    WHERE data_quality IN ('CORRECT', 'COMPLETE_AND_CORRECT');

INSERT INTO discogs.master_styles (id, style)
    SELECT DISTINCT id, style
    FROM discogs.master_styles_import
    WHERE id IN (SELECT id
                 FROM discogs.master);

-- discard records with '&' as the only character, as they are useless and can occur more than one (violating constraints)
INSERT INTO discogs.master_genres (id, genre)
    SELECT id, genre
    FROM discogs.master_genres_import
    WHERE genre <> '&'
      AND id IN (SELECT id
                 FROM discogs.master);

INSERT INTO discogs.master_images (uri, uri150, type, width, height)
    SELECT *
    FROM discogs.master_images_import;

INSERT INTO discogs.images_of_master (uri, master_id)
    SELECT *
    FROM discogs.images_of_master_import
    WHERE master_id IN (SELECT id
                        FROM discogs.master);

INSERT INTO discogs.master_artist (id, name, role, join_att, anv)
    SELECT id, STRING_AGG(name, ' ''#'' '), STRING_AGG(role, ' ''#'' '), STRING_AGG(join_att, ' ''#'' '), STRING_AGG(anv, ' ''#'' ')
    FROM discogs.master_artist_import
    GROUP BY id;

INSERT INTO discogs.master_artist_performs (master_id, artist_id)
    SELECT DISTINCT master_id, artist_id
    FROM discogs.master_artist_performs_import
    WHERE master_id IN (SELECT id
                        FROM discogs.master)
      AND artist_id IN (SELECT id
                        FROM discogs.master_artist);

-- transformation for release tables

INSERT INTO discogs.release (id, released, country, notes, status, title)
    SELECT id, released, country, STRING_AGG(notes, '''#'''), status, title
    FROM discogs.release_import
    WHERE data_quality IN ('CORRECT', 'COMPLETE_AND_CORRECT')
    GROUP BY id
    ORDER BY id;

INSERT INTO discogs.release_styles (id, style)
    SELECT DISTINCT id, style
    FROM discogs.release_styles_import;

INSERT INTO discogs.release_genres (id, genre)
    SELECT DISTINCT id, genre
    FROM discogs.release_genres_import;

INSERT INTO discogs.release_artist (id, name, role, join_att, anv)
    SELECT DISTINCT id, name, role, join_att, anv
    FROM discogs.release_artist_import;

INSERT INTO discogs.artist_of_release (release_id, artist_id)
    SELECT DISTINCT release_id, artist_id
    FROM discogs.artist_of_release_import;

INSERT INTO discogs.release_extraartist (id, name, role, join_att, anv)
    SELECT DISTINCT id, name, role, join_att, anv
    FROM discogs.release_extraartist_import;

INSERT INTO discogs.extraartist_of_release (release_id, artist_id)
    SELECT DISTINCT release_id, artist_id
    FROM discogs.extraartist_of_release_import;

INSERT INTO discogs.release_identifier (value, type, description)
    SELECT DISTINCT value, type, description
    FROM discogs.release_identifier_import;

INSERT INTO discogs.identifies (release_id, identifier_value)
    SELECT release_id, identifier_value
    FROM discogs.identifies_import;

INSERT INTO discogs.release_video (src, duration, description, title, embed)
    SELECT src, duration, description, title, embed
    FROM discogs.release_video_import;

INSERT INTO discogs.video_of_release (release_id, video_src)
    SELECT release_id, video_src
    FROM discogs.video_of_release_import;

INSERT INTO discogs.release_company (id, resource_url, name, entity_type, entity_type_value, catno)
    SELECT id, resource_url, name, entity_type, entity_type_value, catno
    FROM discogs.release_company_import;

INSERT INTO discogs.company_of_release (release_id, company_id)
    SELECT release_id, company_id
    FROM discogs.company_of_release_import;

INSERT INTO discogs.release_image (uri, uri150, type, width, height)
    SELECT uri, uri150, type, width, heigth
    FROM discogs.release_image_import;

INSERT INTO discogs.image_of_release (uri, release_id)
    SELECT uri, release_id
    FROM discogs.image_of_release_import;

INSERT INTO discogs.release_label (id, catno, name)
    SELECT id, catno, name
    FROM discogs.release_label_import;

INSERT INTO discogs.label_of_release (label_id, release_id)
    SELECT label_id, release_id
    FROM discogs.label_of_release_import;
