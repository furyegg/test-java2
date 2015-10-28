package test.mybatis3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.mybatis3.mappers.BlogMapper;
import test.mybatis3.model.Blog;

import java.util.List;

@Service
@Transactional
public class BlogService2 {
	@Autowired
	private BlogMapper blogMapper; // This is to demonstratee how to inject Mappers directly

	public void insertBlog(Blog blog) {
		blogMapper.insertBlog(blog);
	}

	public Blog getBlogById(Integer blogId) {
		return blogMapper.getBlogById(blogId);
	}

	public List<Blog> getAllBlogs() {
		return blogMapper.getAllBlogs();
	}
}