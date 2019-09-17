DROP TABLE IF EXISTS discogs.rel_master_artist;

CREATE TABLE discogs.rel_master_artist (
    master_id INTEGER,
    artist_id INTEGER);
    
ALTER TABLE discogs.rel_master_artist ADD PRIMARY KEY (master_id, artist_id);
ALTER TABLE discogs.rel_master_artist ADD CONSTRAINT discogs_rel_artist_master_master_artist_fk FOREIGN KEY (master_id) REFERENCES discogs.master_artist(id);
ALTER TABLE discogs.rel_master_artist ADD CONSTRAINT discogs_rel_artist_master_artist_fk FOREIGN KEY (artist_id) REFERENCES discogs.artist(id);

INSERT INTO discogs.rel_master_artist(artist_id, master_id)
    SELECT ma.id, a.id
    FROM discogs.master_artist ma JOIN discogs.artist a ON (ma.id = a.id);
    
    
DROP TABLE IF EXISTS discogs.rel_release_label;

CREATE TABLE discogs.rel_release_label(
    release_id INTEGER,
    label_id INTEGER);
    
ALTER TABLE discogs.rel_release_label ADD PRIMARY KEY (release_id, label_id);
ALTER TABLE discogs.rel_release_label ADD CONSTRAINT discogs_rel_release_label_release_fk FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.rel_release_label ADD CONSTRAINT discogs_rel_release_label_label_fk FOREIGN KEY (label_id) REFERENCES discogs.release_label(id);

INSERT INTO discogs.rel_release_label(release_id, label_id)
    SELECT DISTINCT rl.id, l.id
    FROM (SELECT id, UNNEST(label_id) label_id
          FROM discogs.release_label) rl 
        JOIN discogs.label l ON (rl.label_id = l.id)
    WHERE EXISTS (SELECT *
                  FROM discogs.release
                  WHERE id = rl.id);

DROP TABLE IF EXISTS discogs.rel_release_artist;

CREATE TABLE discogs.rel_release_artist(
    release_id INTEGER,
    artist_id INTEGER);
    
ALTER TABLE discogs.rel_release_artist ADD PRIMARY KEY (release_id, artist_id);
ALTER TABLE discogs.rel_release_artist ADD CONSTRAINT discogs_rel_release_artist_release_fk FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.rel_release_artist ADD CONSTRAINT discogs_rel_release_artist_artist_fk FOREIGN KEY (artist_id) REFERENCES discogs.artist(id);

INSERT INTO discogs.rel_release_artist(release_id, artist_id)
    SELECT aor.release_id, a.id
    FROM discogs.artist_of_release aor JOIN discogs.release_artist ra ON (aor.artist_id = ra.id) JOIN discogs.artist a ON (ra.id = a.id);
