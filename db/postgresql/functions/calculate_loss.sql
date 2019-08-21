CREATE OR REPLACE FUNCTION calculate_losses() RETURNS VOID AS $$
DECLARE
    table_cursor CURSOR FOR
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'discogs'
          AND table_name NOT LIKE '%import%';

    v_table_name TEXT;

    v_stmt TEXT;

    v_imported_tuple_cnt INTEGER;
    v_transformed_tuple_cnt INTEGER;
BEGIN
    DROP TABLE IF EXISTS discogs.processing_results;
    CREATE TABLE discogs.processing_results (
        import_table TEXT,
        import_tuple_count INTEGER,
        transformed_table TEXT,
        transformed_tuple_count INTEGER);

    OPEN CURSOR table_cursor;
    LOOP
        FETCH table_cursor INTO v_table_name;
        EXIT WHEN NOT FOUND;

        v_stmt := 'SELECT COUNT(*) FROM discogs.$1_import';
        EXECUTE v_stmt INTO v_imported_tuple_cnt USING v_table_name;
        v_stmt := 'SELECT COUNT(*) FROM discogs.$1';
        EXECUTE v_stmt INTO v_transformed_tuple_cnt USING v_table_name;

        INSERT INTO discogs.processing_result (import_table, import_tuple_count, transformed_table, transformed_tuple_count)
            VALUES (v_table_name || '_import', v_imported_tuple_cnt, v_table_name, v_transformed_tuple_cnt);
    END LOOP;
    CLOSE table_cursor;
END;
$$language plpgsql;
