

package com.lombardrisk.ocelot.spring.jdbc;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.lang.UsesJava7;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

class CloseableResultSetWrappingSqlRowSet implements CloseableSqlRowSet {

    /**
     * use serialVersionUID from Spring 1.2 for interoperability
     */
    private static final long serialVersionUID = -4688694393146734764L;


    private final ResultSet resultSet;

    private final SqlRowSetMetaData rowSetMetaData;

    private final Map<String, Integer> columnLabelMap;

    private final CloseableJdbcTemplate jdbcTemplate;


    /**
     * Create a new CloseableResultSetWrappingSqlRowSet for the given ResultSet.
     *
     * @param resultSet a disconnected ResultSet to wrap
     *                  (usually a {@code javax.sql.rowset.CachedRowSet})
     * @throws InvalidResultSetAccessException if extracting
     *                                         the ResultSetMetaData failed
     * @see javax.sql.rowset.CachedRowSet
     * @see ResultSet#getMetaData
     */
    public CloseableResultSetWrappingSqlRowSet(ResultSet resultSet,CloseableJdbcTemplate jdbcTemplate) throws InvalidResultSetAccessException {
        this.resultSet = resultSet;
        this.jdbcTemplate = jdbcTemplate;
        try {
            this.rowSetMetaData = new CloseableResultSqlRowSetMetaData(resultSet.getMetaData(), this);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            if (rsmd != null) {
                int columnCount = rsmd.getColumnCount();
                this.columnLabelMap = new HashMap<String, Integer>(columnCount);
                for (int i = 1; i <= columnCount; i++) {
                    String key = rsmd.getColumnLabel(i);
                    // Make sure to preserve first matching column for any given name,
                    // as defined in ResultSet's type-level javadoc (lines 81 to 83).
                    if (!this.columnLabelMap.containsKey(key)) {
                        this.columnLabelMap.put(key, i);
                    }
                }
            } else {
                this.columnLabelMap = Collections.emptyMap();
            }
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }

    }


    /**
     * Return the underlying ResultSet
     * (usually a {@code javax.sql.rowset.CachedRowSet}).
     *
     * @see javax.sql.rowset.CachedRowSet
     */
    public final ResultSet getResultSet() {
        return this.resultSet;
    }

    /**
     * @see ResultSetMetaData#getCatalogName(int)
     */
   
    public final SqlRowSetMetaData getMetaData() {
        return this.rowSetMetaData;
    }

    /**
     * @see ResultSet#findColumn(String)
     */
    
    public int findColumn(String columnLabel) throws InvalidResultSetAccessException {
        Integer columnIndex = this.columnLabelMap.get(columnLabel);
        if (columnIndex != null) {
            return columnIndex;
        } else {
            try {
                return this.resultSet.findColumn(columnLabel);
            } catch (SQLException se) {
                this.close();
                throw new InvalidResultSetAccessException(se);
            }
        }
    }


    // RowSet methods for extracting data values

    /**
     * @see ResultSet#getBigDecimal(int)
     */
    
    public BigDecimal getBigDecimal(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getBigDecimal(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getBigDecimal(String)
     */
    
    public BigDecimal getBigDecimal(String columnLabel) throws InvalidResultSetAccessException {
        return getBigDecimal(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getBoolean(int)
     */
    
    public boolean getBoolean(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getBoolean(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getBoolean(String)
     */
    
    public boolean getBoolean(String columnLabel) throws InvalidResultSetAccessException {
        return getBoolean(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getByte(int)
     */
    
    public byte getByte(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getByte(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getByte(String)
     */
    
    public byte getByte(String columnLabel) throws InvalidResultSetAccessException {
        return getByte(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getDate(int)
     */
    
    public Date getDate(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getDate(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getDate(String)
     */
    
    public Date getDate(String columnLabel) throws InvalidResultSetAccessException {
        return getDate(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getDate(int, Calendar)
     */
    
    public Date getDate(int columnIndex, Calendar cal) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getDate(columnIndex, cal);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getDate(String, Calendar)
     */
    
    public Date getDate(String columnLabel, Calendar cal) throws InvalidResultSetAccessException {
        return getDate(findColumn(columnLabel), cal);
    }

    /**
     * @see ResultSet#getDouble(int)
     */
    
    public double getDouble(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getDouble(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getDouble(String)
     */
    
    public double getDouble(String columnLabel) throws InvalidResultSetAccessException {
        return getDouble(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getFloat(int)
     */
    
    public float getFloat(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getFloat(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getFloat(String)
     */
    
    public float getFloat(String columnLabel) throws InvalidResultSetAccessException {
        return getFloat(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getInt(int)
     */
    
    public int getInt(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getInt(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getInt(String)
     */
    
    public int getInt(String columnLabel) throws InvalidResultSetAccessException {
        return getInt(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getLong(int)
     */
    
    public long getLong(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getLong(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getLong(String)
     */
    
    public long getLong(String columnLabel) throws InvalidResultSetAccessException {
        return getLong(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getNString(int)
     */
    
    public String getNString(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getNString(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getNString(String)
     */
    
    public String getNString(String columnLabel) throws InvalidResultSetAccessException {
        return getNString(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getObject(int)
     */
    
    public Object getObject(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getObject(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getObject(String)
     */
    
    public Object getObject(String columnLabel) throws InvalidResultSetAccessException {
        return getObject(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getObject(int, Map)
     */
    
    public Object getObject(int columnIndex, Map<String, Class<?>> map) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getObject(columnIndex, map);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getObject(String, Map)
     */
    
    public Object getObject(String columnLabel, Map<String, Class<?>> map) throws InvalidResultSetAccessException {
        return getObject(findColumn(columnLabel), map);
    }

    /**
     * @see ResultSet#getObject(int, Class)
     */
    @UsesJava7
    
    public <T> T getObject(int columnIndex, Class<T> type) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getObject(columnIndex, type);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getObject(String, Class)
     */
    
    public <T> T getObject(String columnLabel, Class<T> type) throws InvalidResultSetAccessException {
        return getObject(findColumn(columnLabel), type);
    }

    /**
     * @see ResultSet#getShort(int)
     */
    
    public short getShort(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getShort(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getShort(String)
     */
    
    public short getShort(String columnLabel) throws InvalidResultSetAccessException {
        return getShort(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getString(int)
     */
    
    public String getString(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getString(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getString(String)
     */
    
    public String getString(String columnLabel) throws InvalidResultSetAccessException {
        return getString(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getTime(int)
     */
    
    public Time getTime(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getTime(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getTime(String)
     */
    
    public Time getTime(String columnLabel) throws InvalidResultSetAccessException {
        return getTime(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getTime(int, Calendar)
     */
    
    public Time getTime(int columnIndex, Calendar cal) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getTime(columnIndex, cal);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getTime(String, Calendar)
     */
    
    public Time getTime(String columnLabel, Calendar cal) throws InvalidResultSetAccessException {
        return getTime(findColumn(columnLabel), cal);
    }

    /**
     * @see ResultSet#getTimestamp(int)
     */
    
    public Timestamp getTimestamp(int columnIndex) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getTimestamp(columnIndex);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getTimestamp(String)
     */
    
    public Timestamp getTimestamp(String columnLabel) throws InvalidResultSetAccessException {
        return getTimestamp(findColumn(columnLabel));
    }

    /**
     * @see ResultSet#getTimestamp(int, Calendar)
     */
    
    public Timestamp getTimestamp(int columnIndex, Calendar cal) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getTimestamp(columnIndex, cal);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getTimestamp(String, Calendar)
     */
    
    public Timestamp getTimestamp(String columnLabel, Calendar cal) throws InvalidResultSetAccessException {
        return getTimestamp(findColumn(columnLabel), cal);
    }


    // RowSet navigation methods

    /**
     * @see ResultSet#absolute(int)
     */
    
    public boolean absolute(int row) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.absolute(row);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#afterLast()
     */
    
    public void afterLast() throws InvalidResultSetAccessException {
        try {
            this.resultSet.afterLast();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#beforeFirst()
     */
    
    public void beforeFirst() throws InvalidResultSetAccessException {
        try {
            this.resultSet.beforeFirst();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#first()
     */
    
    public boolean first() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.first();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#getRow()
     */
    
    public int getRow() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.getRow();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#isAfterLast()
     */
    
    public boolean isAfterLast() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.isAfterLast();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#isBeforeFirst()
     */
    
    public boolean isBeforeFirst() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.isBeforeFirst();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#isFirst()
     */
    
    public boolean isFirst() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.isFirst();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#isLast()
     */
    
    public boolean isLast() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.isLast();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#last()
     */
    
    public boolean last() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.last();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#next()
     */
    
    public boolean next() throws InvalidResultSetAccessException {
        try {

            boolean result = this.resultSet.next();
            if (!result) {
                this.close();
            }
            return result;
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#previous()
     */
    
    public boolean previous() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.previous();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#relative(int)
     */
    
    public boolean relative(int rows) throws InvalidResultSetAccessException {
        try {
            return this.resultSet.relative(rows);
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    /**
     * @see ResultSet#wasNull()
     */
    
    public boolean wasNull() throws InvalidResultSetAccessException {
        try {
            return this.resultSet.wasNull();
        } catch (SQLException se) {
            this.close();
            throw new InvalidResultSetAccessException(se);
        }
    }

    
    public void close() {
        jdbcTemplate.release();
    }
}
