package com.ldx.sqlSession;

import com.ldx.pojo.Configuration;
import com.ldx.pojo.MappedStatement;

import java.util.List;

/**
 * @author lidongxu
 * @date 2020/10/11
 * @Description
 */
public interface Executor {
    public <E>List<E> query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws Exception;
}
