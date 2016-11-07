package com.lombardrisk.ocelot.spring.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.ArgumentTypePreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jason zhang on 7/2/2016.
 */
public class JdbcTemplate2 extends JdbcTemplate implements CloseableJdbcTemplate {
    private Connection conn;
    private PreparedStatementSetter pss;
    private PreparedStatement ps;
    private ResultSet resultSet;

    public JdbcTemplate2(){}


    public JdbcTemplate2(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public CloseableSqlRowSet queryForRowSet(String sql, Object[] args, int[] argTypes) throws DataAccessException {
        try {
            this.release();
            conn =  getDataSource().getConnection();
            ps =  conn.prepareStatement(sql);
            pss = new ArgumentTypePreparedStatementSetter(args, argTypes);
            pss.setValues(ps);
            resultSet = ps.executeQuery();
            return new CloseableResultSetWrappingSqlRowSet(resultSet, this);
        } catch (SQLException ex) {
            this.release();
            throw getExceptionTranslator().translate("PreparedStatementCallback", sql, ex);
        } catch (Exception e) {
            this.release();
            throw new DataRetrievalFailureException(e.getMessage());
        }

    }

    @Override
    public CloseableSqlRowSet queryForRowSet(String sql, Object... args) throws DataAccessException {

        try {
            this.release();
            conn =  getDataSource().getConnection();
            ps =  conn.prepareStatement(sql);
            pss = new ArgumentPreparedStatementSetter(args);
            pss.setValues(ps);
            applyStatementSettings(ps);
            resultSet = ps.executeQuery();
            return new CloseableResultSetWrappingSqlRowSet(resultSet, this);
        } catch (SQLException ex) {
            this.release();
            throw getExceptionTranslator().translate("PreparedStatementCallback", sql, ex);
        } catch (Exception e) {
            this.release();
            throw new DataRetrievalFailureException(e.getMessage());
        }
    }

    @Override
    public CloseableSqlRowSet queryForRowSet(String sql) throws DataAccessException {
        try {
            this.release();
            conn =  getDataSource().getConnection();
            ps =  conn.prepareStatement(sql);
            resultSet = ps.executeQuery();
            return new CloseableResultSetWrappingSqlRowSet(resultSet, this);
        } catch (SQLException ex) {
            this.release();
            throw getExceptionTranslator().translate("PreparedStatementCallback", sql, ex);
        } catch (Exception e) {
            this.release();
            throw new DataRetrievalFailureException(e.getMessage());
        }
    }

//    @Override
    public void release() {
        if (pss != null) {
            if (pss instanceof ParameterDisposer) {
                ((ParameterDisposer) pss).cleanupParameters();

            }
        }

        JdbcUtils.closeResultSet(resultSet);
        JdbcUtils.closeStatement(ps);
        DataSourceUtils.releaseConnection(conn, getDataSource());
    }
}
