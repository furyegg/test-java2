package test.spark;

import com.google.common.collect.ImmutableList;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Driver implements Serializable {

	public static void main(String[] args) {
		SparkSession spark = SparkSession.builder().enableHiveSupport().appName("Spark Test").getOrCreate();
		List<Employee> employees = ImmutableList.of(new Employee("a"), new Employee("b"), new Employee("c"));
		Dataset<Row> df = spark.createDataFrame(employees, Employee.class);
		df.write().saveAsTable("Employee_" + new Random().nextInt(100));
	}

}