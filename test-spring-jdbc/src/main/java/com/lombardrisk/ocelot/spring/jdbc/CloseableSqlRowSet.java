package com.lombardrisk.ocelot.spring.jdbc;

import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Created by jason zhang on 7/2/2016.
 */
public interface CloseableSqlRowSet extends SqlRowSet {
    void close();

}
