package test.mybatis3.unittest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import test.mybatis3.service.UserService;

public class UserServiceTest {
	private static UserService userService;

	@BeforeClass
	public static void setup() {
		userService = new UserService();
	}

	@AfterClass
	public static void teardown() {
		userService = null;
	}

	@Test
	public void testGetUserById() {
		userService.getUserById();
	}

}
