package com.lombardrisk.ocelot.spring.jdbc;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterDisposer;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.SqlProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * the spring Named parameterJdbcTemplate .queryForRowSet has performance issue . so override the query for rowset to improve
 */
public class NamedParameterJdbcTemplate2 extends NamedParameterJdbcTemplate implements CloseableJdbcTemplate {
	private Connection conn;
	private PreparedStatementCreator psc;
	private PreparedStatement ps;
	private ResultSet resultSet;

	public NamedParameterJdbcTemplate2(DataSource dataSource) {
		super(dataSource);
	}

	public NamedParameterJdbcTemplate2(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}

	private JdbcTemplate getJdbcTemplate() {
		return (JdbcTemplate) this.getJdbcOperations();
	}

	@Override
	public CloseableSqlRowSet queryForRowSet(String sql, SqlParameterSource paramSource) throws DataAccessException {
		try {
			conn = getJdbcTemplate().getDataSource().getConnection();
			psc = getPreparedStatementCreator(sql, paramSource);
			ps = psc.createPreparedStatement(conn);
			applyStatementSettings(ps);
			resultSet = ps.executeQuery();
			return new CloseableResultSetWrappingSqlRowSet(resultSet, this);
		} catch (SQLException ex) {
			this.release();
			throw translateSqlException(sql, ex);
		} catch (Exception e) {
			this.release();
			throw new DataRetrievalFailureException(e.getMessage());
		}
	}

	@Override
	public CloseableSqlRowSet queryForRowSet(String sql, Map<String, ?> paramMap) throws DataAccessException {
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		return this.queryForRowSet(sql, paramSource);

	}

	private DataAccessException translateSqlException(String sql, SQLException ex) {
		if (psc != null) {
            if (psc instanceof ParameterDisposer) {
                ((ParameterDisposer) psc).cleanupParameters();
            }
            String sqlStatement = getSql(psc);
            throw  getJdbcTemplate().getExceptionTranslator().translate("PreparedStatementCallback", sqlStatement, ex);
        }else{
            throw  getJdbcTemplate().getExceptionTranslator().translate("PreparedStatementCallback", sql, ex);
        }
	}

	/**
	 * Determine SQL from potential provider object.
	 * @param sqlProvider object that's potentially a SqlProvider
	 * @return the SQL string, or {@code null}
	 * @see SqlProvider
	 */
	private static String getSql(Object sqlProvider) {
		if (sqlProvider instanceof SqlProvider) {
			return ((SqlProvider) sqlProvider).getSql();
		}
		else {
			return null;
		}
	}

	/**
	 * Call this method to release resources.
	 */
	public void release() {
		if (psc != null) {
			if (psc instanceof ParameterDisposer) {
				((ParameterDisposer) psc).cleanupParameters();
			}
			psc = null;
		}
		JdbcUtils.closeResultSet(resultSet);
		JdbcUtils.closeStatement(ps);
		DataSourceUtils.releaseConnection(conn, getJdbcTemplate().getDataSource());
		ps = null;
		conn = null;
	}

	private void applyStatementSettings(Statement stmt) throws SQLException {
		int fetchSize = getJdbcTemplate().getFetchSize();
		if (fetchSize >= 0) {
			stmt.setFetchSize(fetchSize);
		}
		int maxRows =  getJdbcTemplate().getMaxRows();
		if (maxRows >= 0) {
			stmt.setMaxRows(maxRows);
		}
		DataSourceUtils.applyTimeout(stmt,  getJdbcTemplate().getDataSource(),  getJdbcTemplate().getQueryTimeout());
	}
}