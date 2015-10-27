package test.mybatis3.unittest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import test.mybatis3.service.BlogService;

public class BlogServiceTest {
	private static BlogService blogService;

	@BeforeClass
	public static void setup() {
		blogService = new BlogService();
	}

	@AfterClass
	public static void teardown() {
		blogService = null;
	}

	@Test
	public void testGetBlogById() {
		blogService.getBlogById();
	}

}