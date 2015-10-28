package test.mybatis3.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.mybatis3.mappers.UserMapper;
import test.mybatis3.model.User;

@Service
@Transactional
public class UserService2 {

	@Autowired
	private SqlSession sqlSession; //This is to demonstrate injecting SqlSession object

	public void insertUser(User user) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.insertUser(user);
	}

	public User getUserById(Integer userId) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		return userMapper.getUserById(userId);
	}

	public void updateUser(User user) {
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		userMapper.updateUser(user);
	}
}