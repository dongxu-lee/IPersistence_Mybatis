package com.ldx.sqlSession;

import java.util.List;

public interface SqlSession {

    public <E> List<E> selectList(String statementid, Object... params) throws Exception;

    public <T> T selectOne(String statementid, Object... params) throws Exception;

    //为dao接口生成代理实现类
    public <T> T getMapper(Class<?> mapperClass);

}
