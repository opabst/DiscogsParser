CREATE OR REPLACE FUNCTION discogs.calculate_losses() RETURNS VOID AS $$
DECLARE
    table_cursor CURSOR FOR
        SELECT table_name
        FROM information_schema.tables
        WHERE table_schema = 'discogs'
          AND table_name NOT LIKE '%import%'
          AND table_name <> 'processing_results';

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
        transformed_tuple_count INTEGER,
        loss_cnt INTEGER,
        loss_pct NUMERIC(5,2));

    OPEN table_cursor;
    LOOP
        FETCH table_cursor INTO v_table_name;
        EXIT WHEN NOT FOUND;

        v_stmt := 'SELECT COUNT(*) FROM discogs.' || v_table_name || '_import';
        EXECUTE v_stmt INTO v_imported_tuple_cnt;
        v_stmt := 'SELECT COUNT(*) FROM discogs.' || v_table_name;
        EXECUTE v_stmt INTO v_transformed_tuple_cnt;

        INSERT INTO discogs.processing_results (import_table, import_tuple_count, transformed_table, transformed_tuple_count, loss_cnt, loss_pct)
            VALUES (v_table_name || '_import', v_imported_tuple_cnt, v_table_name, v_transformed_tuple_cnt, v_imported_tuple_cnt - v_transformed_tuple_cnt, 100.00 -  TRUNC(cast(v_transformed_tuple_cnt AS DECIMAL) / (v_imported_tuple_cnt/100), 2));
    END LOOP;
    CLOSE table_cursor;
END;
$$language plpgsql;
