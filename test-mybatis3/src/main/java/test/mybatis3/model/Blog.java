package test.mybatis3.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Blog {

	private Integer blogId;
	private String blogName;
	private Date createdOn;
	private Set<Post> posts = new HashSet<Post>();

	@Override
	public String toString() {
		return "Blog [blogId=" + blogId + ", blogName=" + blogName
				+ ", createdOn=" + createdOn + "]";
	}

	public Integer getBlogId() {
		return blogId;
	}

	public void setBlogId(Integer blogId) {
		this.blogId = blogId;
	}

	public String getBlogName() {
		return blogName;
	}

	public void setBlogName(String blogName) {
		this.blogName = blogName;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}
}