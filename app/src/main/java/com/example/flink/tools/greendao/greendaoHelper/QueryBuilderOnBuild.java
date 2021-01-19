package com.example.flink.tools.greendao.greendaoHelper;

import org.greenrobot.greendao.query.QueryBuilder;

public interface QueryBuilderOnBuild<T> {

    QueryBuilder<T> addQueryParam(QueryBuilder<T> queryBuilder);

}
