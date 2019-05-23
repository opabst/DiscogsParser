
-- transformation for artist table
-- '#' used as delimiter to seperate realnames and profiles from different records

INSERT INTO discogs.artist (id, name, realname, profile)
    SELECT id, name, STRING_AGG(REPLACE(realname, ',', ' ''#'' '), ' ''#'' '), STRING_AGG(profile, '''#''')
    FROM discogs.artist_import
    WHERE data_quality IN ('CORRECT', 'COMPLETE_AND_CORRECT')
    GROUP BY id, name
    ORDER BY id, name;

INSERT INTO discogs.artist_namevariations (id, name_variation)
    SELECT DISTINCT id, name_variation
    FROM discogs.artist_namevariations_import;

INSERT INTO discogs.artist_alias (id, alias)
    SELECT DISTINCT id, alias
    FROM discogs.artist_alias_import;

INSERT INTO discogs.alias_of_artist (artist_id, alias_id, alias_name)
    SELECT DISTINCT artist_id, alias_id, alias_name
    FROM discogs.alias_of_artist_import;

INSERT INTO discogs.artist_image (uri, uri150, type, width, height)
    SELECT *
    FROM discogs.artist_image_import;

INSERT INTO discogs.image_of_artist (id, uri)
    SELECT *
    FROM discogs.image_of_artist_import;
