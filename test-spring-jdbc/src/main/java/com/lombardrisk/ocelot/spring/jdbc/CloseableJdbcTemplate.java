package com.lombardrisk.ocelot.spring.jdbc;

/**
 * Created by jason zhang on 7/2/2016.
 */
public interface CloseableJdbcTemplate {

    void release();
}
