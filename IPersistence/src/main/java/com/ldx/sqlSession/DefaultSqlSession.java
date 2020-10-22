package com.ldx.sqlSession;

import com.ldx.pojo.Configuration;
import com.ldx.pojo.MappedStatement;

import java.lang.reflect.*;
import java.util.List;

public class DefaultSqlSession implements SqlSession {
    private Configuration configuration;

    public DefaultSqlSession() {
    }

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public <E> List<E> selectList(String statementid, Object... params) throws Exception {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        MappedStatement mappedStatement = configuration.getMappedStatementMap().get(statementid);
        List<Object> list = simpleExecutor.query(configuration, mappedStatement, params);
        return (List<E>) list;
    }

    @Override
    public <T> T selectOne(String statementid, Object... params) throws Exception {
        List<Object> objects = selectList(statementid, params);
        if(objects.size() == 1) {
            return (T) objects.get(0);
        }else {
            throw  new RuntimeException("查询结果为空或过多");
        }
    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {
        //使用jdbc动态代理来为dao接口生成代理对象，并返回

        Object proxyInstance = Proxy.newProxyInstance(DefaultSqlSession.class.getClassLoader(), new Class[]{mapperClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //底层还是去执行JDBC代码 //根据不同情况，来调用selectList或者selectOne
                //准备参数 1：statementid：sql语句的唯一标识：namespace.id = 接口全限定名.方法名
                // 方法名
                String methodName = method.getName();
                String className = method.getDeclaringClass().getName();

                String statementid = className + "." + methodName;

                //准备参数 2：params:args
                // 获取被调用方法的返回值类型
                Type genericReturnType = method.getGenericReturnType();
                // 判断是否进行了泛型类型参数化
                if (genericReturnType instanceof ParameterizedType) {
                    List<Object> objects = selectList(statementid, args);
                    return objects;
                }

                return selectOne(statementid, args);
            }
        });
        return (T) proxyInstance;

    }
}
