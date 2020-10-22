package com.ldx.dao;

import com.ldx.io.Resources;
import com.ldx.pojo.User;
import com.ldx.sqlSession.SqlSession;
import com.ldx.sqlSession.SqlSessionFactory;
import com.ldx.sqlSession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

public class UserDaoImpl implements IUserDao {

    @Override
    public List<User> findAll() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        List<User> users = sqlSession.selectList("user.selectList");
        for (User u: users) {
            System.out.println(u);
        }
        return users;
    }

    @Override
    public User findByCondition(User user) throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user2 = sqlSession.selectOne("user.selectOne", user);

        return user2;


    }
}
