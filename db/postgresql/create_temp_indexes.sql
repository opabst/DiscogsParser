-------------------
-- ARTIST ---------
-------------------
ALTER TABLE discogs.artist_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.artist_namevariations_import ADD PRIMARY KEY (id, name_variation);
ALTER TABLE discogs.artist_namevariations_import ADD FOREIGN KEY (id) REFERENCES discogs.artist_import(id);

ALTER TABLE discogs.artist_alias_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.alias_of_artist_import ADD PRIMARY KEY (artist_id, alias_id, alias_name);
ALTER TABLE discogs.alias_of_artist_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.artist_import(id);
ALTER TABLE discogs.alias_of_artist_import ADD FOREIGN KEY (alias_id, alias_name) REFERENCES discogs.artist_alias_import(id, alias);

ALTER TABLE discogs.artist_image_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.image_of_artist_import ADD FOREIGN KEY (id) REFERENCES discogs.artist_import(id);

-------------------
-- LABEL ----------
-------------------

ALTER TABLE discogs.label_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.label_urls_import ADD PRIMARY KEY (id, url);
ALTER TABLE discogs.label_urls_import ADD FOREIGN KEY (id) REFERENCES discogs.label_import(id);

ALTER TABLE discogs.sublabel_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.sublabel_of_import ADD PRIMARY KEY (label_id, sublabel_id);
ALTER TABLE discogs.sublabel_of_import ADD FOREIGN KEY (label_id) REFERENCES discogs.label_import(id);
ALTER TABLE discogs.sublabel_of_import ADD FOREIGN KEY (sublabel_id) REFERENCES discogs.sublabel_import(id);

ALTER TABLE discogs.label_images_import ADD PRIMARY KEY (uri);

ALTER TABLE discogs.image_of_label_import ADD FOREIGN KEY (label_id) REFERENCES discogs.label_import(id);
ALTER TABLE discogs.image_of_label_import ADD FOREIGN KEY (uri) REFERENCES discogs.label_images_import(uri);

-------------------
-- MASTER ---------
-------------------

ALTER TABLE discogs.master_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.master_styles_import ADD PRIMARY KEY (id, style);
ALTER TABLE discogs.master_styles_import ADD FOREIGN KEY (id) REFERENCES discogs.master_import(id);

ALTER TABLE discogs.master_genres_import ADD PRIMARY KEY (id, genre);
ALTER TABLE discogs.master_genres_import ADD FOREIGN KEY (id) REFERENCES discogs.master_import(id);

--ALTER TABLE discogs.images_of_master_import ADD FOREIGN KEY (master_id) REFERENCES discogs.master(id);
--ALTER TABLE discogs.images_of_master_import ADD FOREIGN KEY (uri) REFERENCES discogs.master_images(uri);

ALTER TABLE discogs.master_artist_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.master_artist_performs_import ADD PRIMARY KEY (master_id, artist_id);
ALTER TABLE discogs.master_artist_performs_import ADD FOREIGN KEY (master_id) REFERENCES discogs.master_import(id);
ALTER TABLE discogs.master_artist_performs_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.master_artist_import(id);

-------------------
-- RELEASE --------
-------------------

ALTER TABLE discogs.release_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.release_styles_import ADD PRIMARY KEY (id, style);
ALTER TABLE discogs.release_styles_import ADD FOREIGN KEY (id) REFERENCES discogs.release_import(id);

ALTER TABLE discogs.release_genres_import ADD PRIMARY KEY (id, genre);
ALTER TABLE discogs.release_genres_import ADD FOREIGN KEY (id) REFERENCES discogs.release_import(id);

ALTER TABLE discogs.artist_of_release_import ADD PRIMARY KEY (release_id, artist_id);
ALTER TABLE discogs.artist_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release_import(id);
ALTER TABLE discogs.artist_of_release_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_artist_import(id);

ALTER TABLE discogs.release_extraartist_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.extraartist_of_release_import ADD PRIMARY KEY (release_id, artist_id);
ALTER TABLE discogs.extraartist_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);
ALTER TABLE discogs.extraartist_of_release_import ADD FOREIGN KEY (artist_id) REFERENCES discogs.release_extraartist(id);

ALTER TABLE discogs.release_identifier_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.identifies_import ADD PRIMARY KEY (release_id, identifier_id);
ALTER TABLE discogs.identifies_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release_import(id);
ALTER TABLE discogs.identifies_import ADD FOREIGN KEY (identifier_id) REFERENCES discogs.release_identifier_import(id);

ALTER TABLE discogs.release_video_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.video_of_release_import ADD PRIMARY KEY (release_id, video_id);
ALTER TABLE discogs.video_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release_import(id);
ALTER TABLE discogs.video_of_release_import ADD FOREIGN KEY (video_id) REFERENCES discogs.release_video_import(id);

ALTER TABLE discogs.release_company_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.company_of_release_import ADD PRIMARY KEY (release_id, company_id);
ALTER TABLE discogs.company_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release_import(id);
ALTER TABLE discogs.company_of_release_import ADD FOREIGN KEY (company_id) REFERENCES discogs.release_company_import(id);

--ALTER TABLE discogs.image_of_release_import ADD PRIMARY KEY (uri, release_id);
--ALTER TABLE discogs.image_of_release_import ADD FOREIGN KEY (uri) REFERENCES discogs.release_image(uri);
--ALTER TABLE discogs.image_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release(id);

ALTER TABLE discogs.release_label_import ADD PRIMARY KEY (id);

ALTER TABLE discogs.label_of_release_import ADD PRIMARY KEY (label_id, release_id);
ALTER TABLE discogs.label_of_release_import ADD FOREIGN KEY (label_id) REFERENCES discogs.release_label_import(id);
ALTER TABLE discogs.label_of_release_import ADD FOREIGN KEY (release_id) REFERENCES discogs.release_import(id);
