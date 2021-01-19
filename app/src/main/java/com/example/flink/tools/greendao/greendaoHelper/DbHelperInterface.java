package com.example.flink.tools.greendao.greendaoHelper;


import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public interface DbHelperInterface<D extends AbstractDao<T, ?>, T> {

    long insertOrReplace(T t, boolean replace);

    List<T> query(QueryBuilderOnBuild<T> queryBuilderOnBuild);

    List<T> loadAll();

    void delete(T t);
}
