package com.ldx.test;

import com.ldx.dao.IUserDao;
import com.ldx.io.Resources;
import com.ldx.pojo.User;
import com.ldx.sqlSession.SqlSession;
import com.ldx.sqlSession.SqlSessionFactory;
import com.ldx.sqlSession.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.util.List;

public class IPersistenceTest {

    @Test
    public void test() throws Exception{
        InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User user = new User();
        user.setId(1);
        user.setUsername("jack");
//        User user2 = sqlSession.selectOne("user.selectOne", user);
//        List<User> user3 = sqlSession.selectList("user.selectList");
//        for (User u: user3) {
//            System.out.println(u);
//        }

        //这里得到的对象实际上是代理对象
        IUserDao userDao = sqlSession.getMapper(IUserDao.class);
        //这里执行接口的任意方法，都会执行代理中的invoke()方法
        List<User> all = userDao.findAll();
        User user1 = userDao.findByCondition(user);
        System.out.println(user1);
        for (User u : all) {
            System.out.println(u);
        }

    }

}
