package com.tenda.digital.coupon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseCleaner {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public DatabaseCleaner(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Trunca todas as tabelas especificadas no Postgres para limpar os dados antes de cada teste.
     */
    public void truncatePostgresTables() {
        String truncateQuery = """
        DO $$
        DECLARE
            table_name TEXT;
        BEGIN
            FOR table_name IN
                SELECT tablename
                FROM pg_tables
                WHERE schemaname = 'public'
            LOOP
                EXECUTE format('TRUNCATE TABLE public.%I CASCADE', table_name);
            END LOOP;
        END $$;
        """;

        jdbcTemplate.execute(truncateQuery);
    }

}
