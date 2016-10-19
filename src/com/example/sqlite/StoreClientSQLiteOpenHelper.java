package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.common.sqlite.SQLiteHelper;
import com.example.model.Location;

/**
 * Created by zhoutaotao on 6/9/15.
 */
public class StoreClientSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "storeclient.db";

    private Class<?>[] modelClasses;

    public StoreClientSQLiteOpenHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public StoreClientSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        modelClasses = new Class<?>[]{Location.class};
        super.getWritableDatabase();
//        SQLiteHelper.createTablesByClasses(this.getWritableDatabase(), modelClasses);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        SQLiteHelper.createTablesByClasses(db, modelClasses);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < DATABASE_VERSION) {
            SQLiteHelper.dropTable(db, Location.class);
            SQLiteHelper.createTablesByClasses(db, modelClasses);
        }

    }
}
