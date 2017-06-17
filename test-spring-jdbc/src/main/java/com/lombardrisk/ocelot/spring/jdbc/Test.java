package com.lombardrisk.ocelot.spring.jdbc;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.lombardrisk.ocelot.spring.jdbc.sign.EncryptionKey;
import com.lombardrisk.ocelot.spring.jdbc.sign.SignatureOutputStream;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SignatureException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Test {
	public static void main(String[] args) throws ParseException, SQLException, IOException, SignatureException {
		ApplicationContext context = new ClassPathXmlApplicationContext("app-context.xml");
		DataSource dataSource = context.getBean(DataSource.class);
		JdbcTemplate jdbcTemplate = context.getBean(JdbcTemplate.class);
		jdbcTemplate.setFetchSize(1000);
		NamedParameterJdbcTemplate namedParamJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);

		String sql = "select \"DRILL_REF\",\"DS_ID\",\"D_CALENDAR_DATE\",\"N_RUN_SKEY\",\"N_SCENARIO_CD\",\"PARENT_ENTITY_CODE\",\"PARENT_ENTITY_NAME\",\"S_A_1\",\"S_A_12_3\",\"S_A_2_1\",\"S_A_2_2\",\"S_A_3\",\"S_A_4_2\",\"S_A_5_1\",\"S_A_5_2\",\"S_A_6\",\"S_D_7\",\"S_N_10_1\",\"S_N_10_2\",\"S_N_11\",\"S_N_12_1\",\"S_N_12_2\",\"S_N_4_1\",\"S_N_8\",\"S_N_9\",\"V_A_13_1\",\"V_A_13_2\",\"V_A_14\",\"V_A_15\",\"V_A_18_1\",\"V_A_18_2\",\"V_A_19\",\"V_A_23_1\",\"V_D_16_1\",\"V_D_16_2\",\"V_D_17\",\"V_D_18_3\",\"V_N_20_1\",\"V_N_20_2\",\"V_N_21_1\",\"V_N_21_2\",\"V_N_22_1\",\"V_N_22_2\",\"V_N_22_3\",\"V_N_23_2\",\"V_N_24_1\",\"V_N_24_2\",\"V_N_24_3\",\"V_N_24_4\" from MV_DSFR02_DS_50K where PARENT_ENTITY_CODE=:entityCode and D_CALENDAR_DATE=:date  and N_RUN_SKEY = (select max(N_RUN_SKEY) RUNKEY from MV_DSFR02_DS_50K where PARENT_ENTITY_CODE =:entityCode and D_CALENDAR_DATE =:date)  order by DRILL_REF";

		Map<String, Object> params = Maps.newHashMap();
		params.put("entityCode", "0001");
		Date time = DateUtils.parseDate("2015-12-31", "yyyy-MM-dd");
		params.put("date", time);

//		jdbcResultSet(dataSource);
//		closeableSqlRowSet(jdbcTemplate, sql, params);
//		sqlRowSetWithSign(jdbcTemplate, sql, params);
//		resultSet(jdbcTemplate, sql, params);
	}

	private static void jdbcResultSet(DataSource dataSource) throws SQLException, ParseException {
		String sql = "select \"DRILL_REF\",\"DS_ID\",\"D_CALENDAR_DATE\",\"N_RUN_SKEY\",\"N_SCENARIO_CD\",\"PARENT_ENTITY_CODE\",\"PARENT_ENTITY_NAME\",\"S_A_1\",\"S_A_12_3\",\"S_A_2_1\",\"S_A_2_2\",\"S_A_3\",\"S_A_4_2\",\"S_A_5_1\",\"S_A_5_2\",\"S_A_6\",\"S_D_7\",\"S_N_10_1\",\"S_N_10_2\",\"S_N_11\",\"S_N_12_1\",\"S_N_12_2\",\"S_N_4_1\",\"S_N_8\",\"S_N_9\",\"V_A_13_1\",\"V_A_13_2\",\"V_A_14\",\"V_A_15\",\"V_A_18_1\",\"V_A_18_2\",\"V_A_19\",\"V_A_23_1\",\"V_D_16_1\",\"V_D_16_2\",\"V_D_17\",\"V_D_18_3\",\"V_N_20_1\",\"V_N_20_2\",\"V_N_21_1\",\"V_N_21_2\",\"V_N_22_1\",\"V_N_22_2\",\"V_N_22_3\",\"V_N_23_2\",\"V_N_24_1\",\"V_N_24_2\",\"V_N_24_3\",\"V_N_24_4\" " +
				"from MV_DSFR02_DS_50K where PARENT_ENTITY_CODE=? and D_CALENDAR_DATE=?  and N_RUN_SKEY = (select max(N_RUN_SKEY) RUNKEY " +
				"from MV_DSFR02_DS_50K where PARENT_ENTITY_CODE =? and D_CALENDAR_DATE =?)  order by DRILL_REF";

		String entityCode = "0001";
		Date date = DateUtils.parseDate("2015-12-31", "yyyy-MM-dd");
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());

		Timer timer = new Timer();
		timer.begin();
		Connection conn = DataSourceUtils.getConnection(dataSource);
		PreparedStatement preparedStatement = conn.prepareStatement(sql);
		preparedStatement.setFetchSize(1000);

		preparedStatement.setString(1, entityCode);
		preparedStatement.setDate(2, sqlDate);
		preparedStatement.setString(3, entityCode);
		preparedStatement.setDate(4, sqlDate);
		ResultSet resultSet = preparedStatement.executeQuery();
		timer.end();

//		System.out.println(resultSet.get);

		timer.begin();
		int count = 0;
		while (resultSet.next()) {
			++count;
		}
		timer.end();
		System.out.println("Count: " + count);

		DataSourceUtils.releaseConnection(conn, dataSource);
	}

	private static void resultSet(JdbcTemplate jdbcTemplate, String sql, Map<String, Object> params) throws SQLException {
		Timer timer = new Timer();

		timer.begin();
		OcelotNamedParameterJdbcTemplate namedParameterJdbcTemplate = new OcelotNamedParameterJdbcTemplate(jdbcTemplate);
		ResultSet resultSet = namedParameterJdbcTemplate.queryForResultSet(sql, params);
		String[] columnNames = null;
		timer.end();

		timer.begin();
		int count = 0;
		while (resultSet.next()) {
			if (columnNames == null) {
				columnNames = namedParameterJdbcTemplate.getColumnNames(resultSet);
				System.out.println(Joiner.on(", ").join(columnNames));
			}
			++count;
		}
		timer.end();
		System.out.println("Count: " + count);

		namedParameterJdbcTemplate.release();
	}

	private static void closeableSqlRowSet(JdbcTemplate jdbcTemplate, String sql, Map<String, Object> params) throws SQLException {
		Timer timer = new Timer();

		timer.begin();
		NamedParameterJdbcTemplate2 namedParameterJdbcTemplate = new NamedParameterJdbcTemplate2(jdbcTemplate);
		CloseableSqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, params);
		String[] columnNames = sqlRowSet.getMetaData().getColumnNames();
		System.out.println(Joiner.on(", ").join(columnNames));
		timer.end();

		timer.begin();
		int count = 0;
		while (sqlRowSet.next()) {
			++count;
		}
		timer.end();
		System.out.println("Count: " + count);

		sqlRowSet.close();
	}

	private static void sqlRowSetWithSign(JdbcTemplate jdbcTemplate, String sql, Map<String, Object> params) throws SQLException,
			SignatureException, IOException {
		SignatureOutputStream signatureOutputStream = new SignatureOutputStream( new ByteArrayOutputStream(), EncryptionKey.PRIVATE_KEY);

		Timer timer = new Timer();

		timer.begin();
		NamedParameterJdbcTemplate2 namedParameterJdbcTemplate = new NamedParameterJdbcTemplate2(jdbcTemplate);
		CloseableSqlRowSet sqlRowSet = namedParameterJdbcTemplate.queryForRowSet(sql, params);
		String[] columnNames = sqlRowSet.getMetaData().getColumnNames();
//		System.out.println(Joiner.on(", ").join(columnNames));
		System.out.println("Column count: "+ columnNames.length);
		timer.end();

		timer.begin();
		int count = 0;
		int fieldCount = 0;
		while (sqlRowSet.next()) {
			++count;

			for (String columnName : columnNames) {
				Object value = sqlRowSet.getObject(columnName);
				if (value == null) {
					continue;
				}

				if(Date.class.isAssignableFrom(value.getClass())){
					value = DateFormatUtils.format((Date) value, "dd/MM/yyyy HH:mm:ss");
				}
				 signatureOutputStream.write(value.toString().getBytes());
				++fieldCount;
			}
		}
		timer.end();
		System.out.println("Count: " + count + ", " + fieldCount + ", " + fieldCount / count);

		sqlRowSet.close();

		System.out.println(signatureOutputStream.getSignature());
	}

}