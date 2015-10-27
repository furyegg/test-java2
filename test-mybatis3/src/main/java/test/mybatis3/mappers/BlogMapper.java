package test.mybatis3.mappers;

import test.mybatis3.model.Blog;

public interface BlogMapper {

	// @Select("SELECT BLOG_ID AS blogId, BLOG_NAME as blogName, CREATED_ON as createdOn FROM BLOG WHERE BLOG_ID=#{blogId}")
	Blog getBlogById(Integer blogId);

}