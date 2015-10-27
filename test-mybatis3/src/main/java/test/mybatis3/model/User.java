package test.mybatis3.model;

public class User {
	private Integer userId;
	private String emailId;
	private String password;
	private String firstName;
	private String lastName;
	private Blog blog;

	@Override
	public String toString() {
		return "User [userId=" + userId + ", emailId=" + emailId
				+ ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + "]";
	}

	public Blog getBlog() {
		return blog;
	}

	public void setBlog(Blog blog) {
		this.blog = blog;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}