package com.common.dao;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.SparseArray;
import com.example.dao.LocationDao;
import com.example.sqlite.StoreClientSQLiteOpenHelper;

public class DaoFactory {
    public static final int TYPE_NOTIFICATION = 19;
    public static final int TYPE_LOCATION = 20;


    private static DaoFactory instance;

    private SQLiteOpenHelper sqliteOpenHelper;
    private SparseArray<IBaseDAO<?, ?>> daoArray;

    private DaoFactory(Context context) {
        daoArray = new SparseArray<IBaseDAO<?, ?>>();
        sqliteOpenHelper = new StoreClientSQLiteOpenHelper(context);
    }

    public synchronized static DaoFactory getInstance(Context context) {
        if (instance == null) {
            instance = new DaoFactory(context);
        }
        return instance;
    }

    public IBaseDAO<?, ?> getDAO(int type) {
        IBaseDAO<?, ?> dao = daoArray.get(type);
        if (dao == null) {
            if (type == TYPE_LOCATION) {
                dao = new LocationDao(sqliteOpenHelper);
            }
            if (dao != null) {
                daoArray.put(type, dao);
            }
        }

        return dao;
    }
}
