package com.example.flink.tools.greendao.greendaoHelper;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

public class DbHelper<D extends AbstractDao<T, ?>, T> implements DbHelperInterface<D, T> {

    private D d;

    public DbHelper(D d) {
        this.d = d;
    }

    @Override
    public long insertOrReplace(T t, boolean tryReplace) {
        if (tryReplace) {
            return d.insertOrReplace(t);
        } else {
            return d.insert(t);
        }
    }

    @Override
    public List<T> query(QueryBuilderOnBuild<T> queryBuilderOnBuild) {
        return queryBuilderOnBuild.addQueryParam(d.queryBuilder()).list();
    }

    @Override
    public List<T> loadAll() {
        return d.loadAll();
    }


    @Override
    public void delete(T t) {
        d.delete(t);
    }

}
