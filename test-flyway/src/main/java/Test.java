import javax.sql.DataSource;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.flyway.core.Flyway;

public class Test {

	public static void main(String[] args) {
		BeanFactory factory = new ClassPathXmlApplicationContext("classpath:appContext.xml");
		DataSource dataSource = (DataSource) factory.getBean("datasource");

		Flyway fw = new Flyway();
		fw.setDataSource(dataSource);

		fw.setLocations("dbupgrade/mssql");

		try {
			fw.init();
		} catch (Exception e) {
			System.out.println("initilized already");
		}
		fw.migrate();
	}
}
