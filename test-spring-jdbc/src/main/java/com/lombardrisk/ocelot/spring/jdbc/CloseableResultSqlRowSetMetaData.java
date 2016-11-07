package com.lombardrisk.ocelot.spring.jdbc;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSetMetaData;

import java.sql.ResultSetMetaData;

/**
 * Created by jason zhang on 7/2/2016.
 */
public class CloseableResultSqlRowSetMetaData extends ResultSetWrappingSqlRowSetMetaData {

    private final CloseableSqlRowSet sqlRowSet;

    public CloseableResultSqlRowSetMetaData(ResultSetMetaData resultSetMetaData,CloseableSqlRowSet sqlRowSet) {
        super(resultSetMetaData);
        this.sqlRowSet = sqlRowSet;
    }

    @Override
    public String getCatalogName(int column) throws InvalidResultSetAccessException {
        try {
            return super.getCatalogName(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public String getColumnClassName(int column) throws InvalidResultSetAccessException {

        try {
            return super.getColumnClassName(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public int getColumnCount() throws InvalidResultSetAccessException {

        try {
            return super.getColumnCount();
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public String[] getColumnNames() throws InvalidResultSetAccessException {

        try {
            return super.getColumnNames();
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public int getColumnDisplaySize(int column) throws InvalidResultSetAccessException {

        try {
            return super.getColumnDisplaySize(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public String getColumnLabel(int column) throws InvalidResultSetAccessException {

        try {
            return super.getColumnLabel(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public String getColumnName(int column) throws InvalidResultSetAccessException {

        try {
            return super.getColumnName(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public int getColumnType(int column) throws InvalidResultSetAccessException {

        try {
            return super.getColumnType(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public String getColumnTypeName(int column) throws InvalidResultSetAccessException {

        try {
            return super.getColumnTypeName(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public int getPrecision(int column) throws InvalidResultSetAccessException {

        try {
            return super.getPrecision(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public int getScale(int column) throws InvalidResultSetAccessException {

        try {
            return super.getScale(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public String getSchemaName(int column) throws InvalidResultSetAccessException {

        try {
            return super.getSchemaName(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public String getTableName(int column) throws InvalidResultSetAccessException {

        try {
            return super.getTableName(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public boolean isCaseSensitive(int column) throws InvalidResultSetAccessException {

        try {
            return super.isCaseSensitive(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public boolean isCurrency(int column) throws InvalidResultSetAccessException {

        try {
            return super.isCurrency(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }

    @Override
    public boolean isSigned(int column) throws InvalidResultSetAccessException {

        try {
            return super.isSigned(column);
        } catch (InvalidResultSetAccessException e) {
            sqlRowSet.close();
            throw e;
        }
    }
}
