import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ODBCTest {

	public static void main(String args[]) {

		String url = "jdbc:odbc:DGB1622_System";
		// 取得连接的url名，注是access的dsn名
		Connection con;
		// 实例化一个Connection对象
		Statement stmt;

		String query = "select * from aliases";
		// 选择所有的Col_link表中的数据输出

		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");

		} catch (java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException: ");
			// 加载jdbc-odbc桥错误
			System.err.println(e.getMessage());
			// 其他错误
		}

		try {

			con = DriverManager.getConnection(url, "sa", "123abc");
			// 数据库连接

			stmt = con.createStatement();
			// Create 一个声明
//			stmt.executeUpdate("drop table col_link");
//			stmt.executeUpdate("CREATE TABLE col_link (sitename varchar (20) NULL, siteurl varchar (50) NULL) ");
//			// 执行了一个sql语句生成了一个表col_link的表
//			stmt.executeUpdate("insert into col_link values('ASP中华网','123')");
//			stmt.executeUpdate("insert into col_link values('永远到底有多远','abc')");
//			// 执行一个insert into语句
//			stmt.executeUpdate("update col_link set siteurl='123' where siteurl='321'");
			// 执行一个update语句，更新数据库
			ResultSet rs = stmt.executeQuery(query);
			// 返回一个结果集
			System.out.println("Col_link表中的数据如下(原始数据)");
			// 下面的语句使用了一个while循环打印出了col_link表中的所有的数据
			System.out.println("站点名 " + " " + "站点地址");
			System.out.println("---------------" + " " + "----------------");
			while (rs.next()) {
				String s = rs.getString("alias");
				String f = rs.getString("username");
				// 取得数据库中的数据
				System.out.println(s + " " + f);
				/*
				 * String t = rs.getString(1); String l = rs.getString(2); System.out.println(t + " " + l);
				 */
				/*
				 * jdbc提供了两种方法识别字段，一种是使用getXXX(注意这里的getXXX表示取不同类型字段的不同的方法)获得字段名， 第二种*是通过字段索引，在这里我把第二种方法注释了
				 */
			}
			stmt.close();
			con.close();
			// 上面的语句关闭声明和连接
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
			ex.printStackTrace();
			// 显示数据库连接错误或者查询错误
		}
	}
}
