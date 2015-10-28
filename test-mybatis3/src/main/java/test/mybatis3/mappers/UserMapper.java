package test.mybatis3.mappers;

import test.mybatis3.model.User;

import java.util.List;

public interface UserMapper {

	public void insertUser(User user);

	public User getUserById(Integer userId);

	public List<User> getAllUsers();

	void updateUser(User user);

}