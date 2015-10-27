package test.jar;

import org.apache.commons.lang3.StringUtils;

import java.io.File;

public class Main {

	public static void main(String[] args) {
		String fileName = "1.txt";
		StringUtils.isNoneBlank(fileName);

		System.out.println(Main.class.getResource(fileName));
		System.out.println(Main.class.getResourceAsStream(fileName) == null);
		System.out.println(Thread.currentThread().getContextClassLoader().getResource(fileName));
		System.out.println(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName) == null);

		String userDir = System.getProperty("user.dir");
		System.out.println(userDir);
		File file = new File(userDir + File.separator + fileName);
		System.out.println(file.exists());
	}

}
