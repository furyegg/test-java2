import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import domain.RegulatoryForm;

public class OracleTest {

	public static void main(String[] args) throws Exception {
		Class.forName("oracle.jdbc.driver.OracleDriver");

		String connStr = "jdbc:oracle:thin:@172.20.20.52:1521:stbdev";
		Connection connection = DriverManager.getConnection(connStr, "TZ_RAC_SYS", "password");

		PreparedStatement statement = connection
				.prepareStatement("select * from \"MFSDRets\" where (ONLYDATAPROCESS is null or ONLYDATAPROCESS <> 1) and \"ReturnId\" in (2501,2502,2503,2504,2505,2506,2507,2508,2509,2510,2511,2512,2513,2515,2517,2550,2552,2553,2554,2555,2556,2557,2558,2559,2560,250001,250002,250003,250004,250005,250006,250007,250008,250009,250010,250011,250012,250013,250014,250015,250016,250017,250018,250019,250020,250021,250022,250023,250024,250025,250026,250027,250028)");
		// PreparedStatement statement =
		// connection.prepareStatement("select * from \"MFSDRets\" where (ONLYDATAPROCESS is null or ONLYDATAPROCESS <> 1) and \"ReturnId\" in (2501,2502)");

		ResultSet rs = statement.executeQuery();

		// System.out.println(resultSet.getMetaData().getTableName(1));

		// String insertSql = "insert into Currency (currencyCode, currencyName) values (''{0}'', ''{1}'');";

		if (rs.next()) {
			RegulatoryForm form = new RegulatoryForm();
			form.setFormId(rs.getInt("ReturnId"));
			form.setFormName(rs.getString("Return"));
			form.setFormVersion(rs.getInt("Version"));
			form.setFormDescription(rs.getString("Name"));
			form.setActiveDate(rs.getDate("ActivateDate"));
			form.setDeActiveDate(rs.getDate("DeActivateDate"));
			form.setExcludeHolidays(rs.getBoolean("ExclHolidays"));
			form.setExcludeWeekends(rs.getBoolean("ExclWeekends"));
			// Raymond MOD 4 TAVA V1.1 START
			// form.setDueDays(rs.getInt("DueDays"));
			form.setFormOrder(rs.getInt("FormOrder"));
			form.setIntervalType(rs.getInt("IntervalType"));
			form.setIntervalFrequency(rs.getInt("IntervalFrequency"));
			form.setKeepNullNumeric(rs.getBoolean("KeepNullNumeric"));
			form.setNewFormBatchRun(rs.getString("NewFormBatchRun"));
			form.setNewFormOutputTable(rs.getString("NewFormOutputTable"));
			form.setComputeBatchRun(rs.getString("CompBatchRun"));
			form.setComputeOutputTable(rs.getString("CompOutputTable"));
			form.setTransmitBatchRun(rs.getString("TransmitBatchRun"));
			// form.setProcessDefName(rs.getString("ProcessDefName"));
			
			String required = rs.getString("Required");
			System.out.println(required);
			form.setRequired(rs.getBoolean("Required"));
			// form.setIntervalSetting(rs.getString("IntervalSetting"));
			// Raymond MOD 4 TAVA V1.1 END
		}

		System.out.println("");
	}

}