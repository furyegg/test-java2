package test.mybatis3.model;

import java.util.Date;

public class Post {
	private Integer postId;
	private String title;
	private String content;
	private Date createdOn;

	@Override
	public String toString() {
		return String.format("Post = Id:%s, title=%s, content=%s, createOn:%s", postId, title, content, createdOn);
	}

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
}
