package test.mybatis3.service;

import org.apache.ibatis.session.SqlSession;
import test.mybatis3.mappers.BlogMapper;
import test.mybatis3.model.Blog;
import test.mybatis3.model.Post;

import java.util.List;

public class BlogService {

	public void getBlogById() {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
			Blog blog = blogMapper.getBlogById(1);
			System.out.println(blog);
			List<Post> posts = blog.getPosts();
			for (Post post : posts) {
				System.out.println(post);
			}
		} finally {
			sqlSession.close();
		}
	}
}