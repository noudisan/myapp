package com.common.dao;

import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseDAO<T extends Serializable, PK extends Serializable> {
    SQLiteDatabase getDatabase(boolean writeable);

    long insert(T entity);

    void insert(List<T> entityList);

    long replace(T entity);

    void replace(List<T> entityList);

    int delete(PK id);

    // void delete(PK... ids);

    int deleteAll();

    int update(T entity);

    T query(PK id);

    List<T> queryAll(String orderBy);

    List<T> queryByField(String field, Object value, String orderBy);

    List<T> query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having,
                  String orderBy, String limit);

    Map<String, Object> queryMap(PK id);

    List<Map<String, Object>> queryMapAll(String orderBy);

    List<Map<String, Object>> queryMapByField(String field, Object value, String orderBy);

    List<Map<String, Object>> queryMap(String[] columns, String selection, String[] selectionArgs, String groupBy,
                                       String having, String orderBy, String limit);

    int count();

    void execSql(String sql, Object[] selectionArgs);

    public List<T> queryAllWithLimit(String groupBy, String orderBy, String limit);
}
