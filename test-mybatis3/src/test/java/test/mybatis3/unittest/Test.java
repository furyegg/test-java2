package test.mybatis3.unittest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import test.mybatis3.model.User;
import test.mybatis3.service.UserService2;

public class Test {
	public static void main(String[] args) {
		ApplicationContext appCtx = new ClassPathXmlApplicationContext("classpath:spring/applicationContext.xml");
		UserService2 userService = appCtx.getBean(UserService2.class);

		long timestamp = System.currentTimeMillis();
		User user = userService.getUserById(2);
		user.setFirstName("TestFirstName" + timestamp);
		user.setLastName("TestLastName" + timestamp);
		userService.updateUser(user);

		User updatedUser = userService.getUserById(2);
		System.out.println(updatedUser.getFirstName() + ", " + updatedUser.getLastName());
	}
}