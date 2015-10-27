package test.mybatis3.service;

import org.apache.ibatis.session.SqlSession;
import test.mybatis3.mappers.UserMapper;
import test.mybatis3.model.User;

public class UserService {

	public void getUserById() {
		SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
		try {
			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
			User user = userMapper.getUserById(2);
			System.out.println(user.getBlog());
		} finally {
			sqlSession.close();
		}
	}

}