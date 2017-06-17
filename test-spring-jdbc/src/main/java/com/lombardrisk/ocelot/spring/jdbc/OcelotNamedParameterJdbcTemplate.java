package com.lombardrisk.ocelot.spring.jdbc;

import org.springframework.dao.DataAccessException;
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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class OcelotNamedParameterJdbcTemplate extends NamedParameterJdbcTemplate {
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private Connection conn;
	private PreparedStatementCreator psc;
	private PreparedStatement ps;
	private ResultSet resultSet;

	public OcelotNamedParameterJdbcTemplate(JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
		dataSource = jdbcTemplate.getDataSource();
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Must call {@link #release()} after this this method.<br/>
	 * Should use this method to query big data.
	 *
	 * @param sql
	 * @param params
	 * @return Primitive java.sql.ResultSet
	 */
	public ResultSet queryForResultSet(String sql, Map<String, Object> params) {
		try {
			conn = dataSource.getConnection();
			SqlParameterSource paramSource = new MapSqlParameterSource(params);
			psc = getPreparedStatementCreator(sql, paramSource);
			ps = psc.createPreparedStatement(conn);
			applyStatementSettings(ps);
			resultSet = ps.executeQuery();
			return resultSet;
		} catch (SQLException ex) {
			throw translateSQLException(ex);
		}
	}

	public String[] getColumnNames(ResultSet resultSet) {
		try {
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			String[] columnNames = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				columnNames[i] = metaData.getColumnName(i + 1);
			}
			return columnNames;
		} catch (SQLException e) {
			throw translateSQLException(e);
		}
	}

	/**
	 * Call this method to translate SQLException and throw new exception.
	 */
	public DataAccessException translateSQLException(SQLException e) {
		// Release Connection early, to avoid potential connection pool deadlock
		// in the case when the exception translator hasn't been initialized yet.
		if (psc instanceof ParameterDisposer) {
			((ParameterDisposer) psc).cleanupParameters();
		}
		String sqlStatement = getSql(psc);
		psc = null;
		org.springframework.jdbc.support.JdbcUtils.closeStatement(ps);
		ps = null;
		DataSourceUtils.releaseConnection(conn, dataSource);
		conn = null;
		return jdbcTemplate.getExceptionTranslator().translate("PreparedStatementCallback", sqlStatement, e);
	}

	/**
	 * Call this method to release resources.
	 */
	public void release() {
		if (psc instanceof ParameterDisposer) {
			((ParameterDisposer) psc).cleanupParameters();
		}
		JdbcUtils.closeResultSet(resultSet);
		JdbcUtils.closeStatement(ps);
		DataSourceUtils.releaseConnection(conn, dataSource);
	}

	private static String getSql(Object sqlProvider) {
		if (sqlProvider instanceof SqlProvider) {
			return ((SqlProvider) sqlProvider).getSql();
		} else {
			return null;
		}
	}

	private void applyStatementSettings(Statement stmt) throws SQLException {
		int fetchSize = jdbcTemplate.getFetchSize();
		if (fetchSize >= 0) {
			stmt.setFetchSize(fetchSize);
		}
		int maxRows = jdbcTemplate.getMaxRows();
		if (maxRows >= 0) {
			stmt.setMaxRows(maxRows);
		}
		DataSourceUtils.applyTimeout(stmt, dataSource, jdbcTemplate.getQueryTimeout());
	}
}