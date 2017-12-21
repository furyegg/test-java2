package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateStatementTest {
	public static void main(String[] args) throws SQLException {
		Connection conn = DriverManager.getConnection(
				"jdbc:oracle:thin:@//172.20.20.49:1521/ora12c",
				"SCHD1_DMP",
				"password");
		
		for (int i = 0; i < 100; ++i) {
			System.out.println(i);
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery("select * from MV_DSFR02_DSV2");
			rs.next();
			System.out.println(rs.getObject(2));
			rs.close();
			statement.close();
		}

		conn.close();
	}
}
