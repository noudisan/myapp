package com.common.dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.common.sqlite.Column;
import com.common.sqlite.SQLiteHelper;
import com.common.sqlite.Id;
import com.common.sqlite.Table;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressLint("SimpleDateFormat")
public class BaseDAO<T extends Serializable, PK extends Serializable> implements IBaseDAO<T, PK> {
    private static final boolean DEBUG = true;

    protected static final int TYPE_CREATE = 0;
    protected static final int TYPE_UPDATE = 1;
    protected static final int TYPE_REPLACE = 2;

    protected SQLiteOpenHelper sqliteOpenHelper;
    protected String tableName;
    protected List<String> idColumnNameList;
    protected Class<T> modelClass;
    protected Class<PK> idClass;
    protected List<Field> allFields;
    protected List<Field> pkFields;

    @SuppressWarnings("unchecked")
    public BaseDAO(SQLiteOpenHelper sqliteOpenHelper) {
        this.sqliteOpenHelper = sqliteOpenHelper;
        modelClass = ((Class<T>) ((java.lang.reflect.ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
        if (modelClass.isAnnotationPresent(Table.class)) {
            Table table = (Table) modelClass.getAnnotation(Table.class);
            tableName = table.name();
        }

        idColumnNameList = new ArrayList<String>();
        allFields = SQLiteHelper.joinFields(modelClass.getDeclaredFields(), modelClass.getSuperclass().getDeclaredFields());
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Id.class)) {
                Id id = (Id) field.getAnnotation(Id.class);
                if (id.composite() == 0) {
                    Column column = (Column) field.getAnnotation(Column.class);
                    idColumnNameList.add(column.name());
                    pkFields = new ArrayList<Field>();
                    pkFields.add(field);
                } else {
                    idClass = ((Class<PK>) ((java.lang.reflect.ParameterizedType) super.getClass().getGenericSuperclass()).getActualTypeArguments()[1]);
                    pkFields = SQLiteHelper.joinFields(idClass.getDeclaredFields(), idClass.getSuperclass().getDeclaredFields());
                    for (Field idField : pkFields) {
                        Column column = (Column) idField.getAnnotation(Column.class);
                        idColumnNameList.add(column.name());
                    }
                }
                break;
            }
        }

        if (DEBUG) {
        }
    }

    @Override
    public SQLiteDatabase getDatabase(boolean writeable) {
        if (writeable) {
            SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();
            if (null != database && !database.isOpen()) {
                database = sqliteOpenHelper.getWritableDatabase();
            }
            return database;
        } else {
            SQLiteDatabase database = sqliteOpenHelper.getWritableDatabase();
            if (null != database && !database.isOpen()) {
                database = sqliteOpenHelper.getWritableDatabase();
            }
            return database;
        }
    }

    @Override
    public long insert(T entity) {
        SQLiteDatabase database = getDatabase(true);
        try {
            ContentValues values = createContentValues(entity, TYPE_CREATE);
            long rowId = database.insert(tableName, null, values);
            return rowId;
        } catch (Exception e) {
        } finally {
            database.close();
        }

        return 0L;
    }

    @Override
    public void insert(List<T> entityList) {
        if (entityList == null || entityList.size() == 0) {
            return;
        }

        SQLiteDatabase database = getDatabase(true);
        try {
            database.beginTransaction();

            for (T entity : entityList) {
                ContentValues values = createContentValues(entity, TYPE_CREATE);
                database.insert(tableName, null, values);
            }

            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Exception e) {
        } finally {
            database.close();
        }
    }

    @Override
    public long replace(T entity) {
        SQLiteDatabase database = getDatabase(true);
        try {
            ContentValues values = createContentValues(entity, TYPE_REPLACE);
            long rowId = database.replace(tableName, null, values);
            return rowId;
        } catch (Exception e) {
        } finally {
            database.close();
        }

        return 0L;
    }

    @Override
    public void replace(List<T> entityList) {
        if (entityList == null || entityList.size() == 0) {
            return;
        }

        SQLiteDatabase database = getDatabase(true);
        try {
            database.beginTransaction();

            for (T entity : entityList) {
                ContentValues values = createContentValues(entity, TYPE_REPLACE);
                database.replace(tableName, null, values);
            }

            database.setTransactionSuccessful();
            database.endTransaction();
        } catch (Exception e) {
        } finally {
            database.close();
        }
    }

    protected Object[] getWhereClause(PK id) throws IllegalArgumentException, IllegalAccessException {
        Object[] whereArray = new Object[2];

        if (idColumnNameList.size() == 1) {
            whereArray[0] = idColumnNameList.get(0) + " = ?";
            whereArray[1] = new String[]{"" + id};
        } else {
            ContentValues values = new ContentValues();
            for (Field idField : pkFields) {
                valuesPutFromField(values, id, idField);
            }
            String[] whereArgs = new String[idColumnNameList.size()];
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < idColumnNameList.size(); i++) {
                String idColumnName = idColumnNameList.get(i);
                buffer.append(idColumnName + " = ? ");
                if (i < idColumnNameList.size() - 1) {
                    buffer.append("AND ");
                }
                whereArgs[i] = "" + values.get(idColumnName);
                values.remove(idColumnName);
            }
            whereArray[0] = buffer.toString();
            whereArray[1] = whereArgs;
        }

        return whereArray;
    }

    @Override
    public int delete(PK id) {
        try {
            Object[] whereArray = getWhereClause(id);
            String whereClause = (String) whereArray[0];
            String[] whereArgs = (String[]) whereArray[1];

            SQLiteDatabase database = getDatabase(true);
            int numberOfRows = database.delete(tableName, whereClause, whereArgs);
            database.close();
            return numberOfRows;
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        return 0;
    }

    // @Override
    // public void delete(PK... ids)
    // {
    // if (ids != null && ids.length > 0)
    // {
    // StringBuffer sb = new StringBuffer();
    // for (int i = 0; i < ids.length; i++)
    // {
    // sb.append('?').append(',');
    // }
    // sb.deleteCharAt(sb.length() - 1);
    // String sql = "DELETE FROM " + tableName + " WHERE " + idColumnName +
    // " in (" + sb + ")";
    // if (DEBUG)
    // {
    // }
    // SQLiteDatabase database = getDatabase(true);
    // database.execSQL(sql, (Object[]) ids);
    // database.close();
    // }
    // }

    @Override
    public int deleteAll() {
        SQLiteDatabase database = getDatabase(true);
        int numberOfRows = database.delete(tableName, null, null);
        database.close();
        return numberOfRows;
    }

    @Override
    public int update(T entity) {
        SQLiteDatabase database = getDatabase(true);
        try {
            ContentValues values = createContentValues(entity, TYPE_UPDATE);
            String[] whereArgs = new String[idColumnNameList.size()];
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < idColumnNameList.size(); i++) {
                String idColumnName = idColumnNameList.get(i);
                buffer.append(idColumnName + " = ? ");
                if (i < idColumnNameList.size() - 1) {
                    buffer.append("AND ");
                }
                whereArgs[i] = "" + values.get(idColumnName);
                values.remove(idColumnName);
            }
            String whereClause = buffer.toString();
            int numberOfRows = database.update(tableName, values, whereClause, whereArgs);
            return numberOfRows;
        } catch (Exception e) {
        } finally {
            database.close();
        }

        return 0;
    }

    @Override
    public T query(PK id) {
        try {
            Object[] whereArray = getWhereClause(id);
            String selection = (String) whereArray[0];
            String[] selectionArgs = (String[]) whereArray[1];

            List<T> list = query(null, selection, selectionArgs, null, null, null, null);
            if ((list != null) && (list.size() > 0)) {
                return list.get(0);
            }
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        return null;
    }

    @Override
    public List<T> queryAll(String orderBy) {
        return query(null, null, null, null, null, orderBy, null);
    }

    @Override
    public List<T> queryAllWithLimit(String groupBy, String orderBy, String limit) {
        return query(null, null, null, groupBy, null, orderBy, limit);
    }

    @Override
    public List<T> queryByField(String field, Object value, String orderBy) {
        String selection = field + " = ?";
        String[] selectionArgs = {"" + value};

        return query(null, selection, selectionArgs, null, null, orderBy, null);
    }

    @Override
    public List<T> query(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase database = getDatabase(true);
        Cursor cursor = database.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        List<T> list = new ArrayList<T>();
        try {
            createEntityListFromCursor(cursor, list);
        } catch (Exception e) {

        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return list;
    }

    @Override
    public Map<String, Object> queryMap(PK id) {
        try {
            Object[] whereArray = getWhereClause(id);
            String selection = (String) whereArray[0];
            String[] selectionArgs = (String[]) whereArray[1];

            List<Map<String, Object>> list = queryMap(null, selection, selectionArgs, null, null, null, null);
            if ((list != null) && (list.size() > 0)) {
                return list.get(0);
            }
        } catch (IllegalArgumentException e) {
        } catch (IllegalAccessException e) {
        }

        return null;
    }

    @Override
    public List<Map<String, Object>> queryMapAll(String orderBy) {
        return queryMap(null, null, null, null, null, orderBy, null);
    }

    @Override
    public List<Map<String, Object>> queryMapByField(String field, Object value, String orderBy) {
        String selection = field + " = ?";
        String[] selectionArgs = {"" + value};

        return queryMap(null, selection, selectionArgs, null, null, orderBy, null);
    }

    @Override
    public List<Map<String, Object>> queryMap(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit) {
        SQLiteDatabase database = getDatabase(true);
        Cursor cursor = database.query(tableName, columns, selection, selectionArgs, groupBy, having, orderBy, limit);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            createMapFromCursor(cursor, list);
        } catch (Exception e) {
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close();
        }
        return list;
    }

    @Override
    public int count() {
        SQLiteDatabase database = getDatabase(true);
        Cursor cursor = database.rawQuery("select count(*) from " + tableName, null);
        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    @Override
    public void execSql(String sql, Object[] selectionArgs) {
        SQLiteDatabase database = getDatabase(true);
        if (selectionArgs == null) {
            database.execSQL(sql);
        } else {
            database.execSQL(sql, selectionArgs);
        }
        database.close();
    }

    @SuppressWarnings("unchecked")
    protected ContentValues createContentValues(T entity, int type) throws IllegalAccessException, SQLException {
        ContentValues values = new ContentValues();
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Id.class)) {
                Id id = (Id) field.getAnnotation(Id.class);
                if (type == TYPE_CREATE && id.generator().equals("AUTOINCREMENT")) {
                    continue;
                }
                if (id.composite() > 0) {
                    field.setAccessible(true);
                    PK pk = (PK) field.get(entity);
                    if (pk == null) {
                        continue;
                    }
                    for (Field idField : pkFields) {
                        valuesPutFromField(values, pk, idField);
                    }
                }
            }

            valuesPutFromField(values, entity, field);
        }
        return values;
    }

    protected void valuesPutFromField(ContentValues values, Object object, Field field) throws IllegalArgumentException, IllegalAccessException {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = (Column) field.getAnnotation(Column.class);
            field.setAccessible(true);
            Object fieldValue = field.get(object);
            if (fieldValue == null) {
                return;
            }
            Class<?> fieldType = field.getType();

            if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                values.put(column.name(), (Integer) fieldValue);
            } else if (String.class == fieldType) {
                values.put(column.name(), (String) fieldValue);
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                values.put(column.name(), (Long) fieldValue);
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                values.put(column.name(), (Float) fieldValue);
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                values.put(column.name(), (Short) fieldValue);
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                values.put(column.name(), (Double) fieldValue);
            } else if (byte[].class == fieldType) {
                values.put(column.name(), (byte[]) fieldValue);
            } else if (Date.class == fieldType) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                values.put(column.name(), dateFormat.format((Date) fieldValue));
            } else {
                values.put(column.name(), fieldValue.toString());
            }
        }
    }

    /**
     * ?��????�??对象??ursor???�?? �??????��?类�?????��??? *
     *
     * @param cursor
     * @param list
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InstantiationException
     * @throws java.text.ParseException
     */
    protected void createEntityListFromCursor(Cursor cursor, List<T> list) throws IllegalArgumentException, IllegalAccessException, InstantiationException, ParseException {
        if (cursor == null) {
            return;
        }

        while (cursor.moveToNext()) {
            list.add(createEntityFromCursor(cursor));
        }
    }

    protected T createEntityFromCursor(Cursor cursor) throws IllegalArgumentException, IllegalAccessException, InstantiationException, ParseException {
        T entity = modelClass.newInstance();

        for (Field field : allFields) {
            if (field.isAnnotationPresent(Id.class)) {
                Id id = (Id) field.getAnnotation(Id.class);
                if (id.composite() > 0) {
                    PK pk = idClass.newInstance();
                    for (Field idField : pkFields) {
                        setFieldFromCursor(pk, idField, cursor);
                    }
                    field.setAccessible(true);
                    field.set(entity, pk);
                }
            }
            setFieldFromCursor(entity, field, cursor);
        }

        return entity;
    }

    protected void setFieldFromCursor(Object object, Field field, Cursor cursor) throws IllegalArgumentException, IllegalAccessException, ParseException {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = (Column) field.getAnnotation(Column.class);
            field.setAccessible(true);
            Class<?> fieldType = field.getType();

            int c = cursor.getColumnIndex(column.name());
            if (c < 0) {
                return;
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                field.set(object, cursor.getInt(c));
            } else if (String.class == fieldType) {
                field.set(object, cursor.getString(c));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                field.set(object, Long.valueOf(cursor.getLong(c)));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                field.set(object, Float.valueOf(cursor.getFloat(c)));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                field.set(object, Short.valueOf(cursor.getShort(c)));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                field.set(object, Double.valueOf(cursor.getDouble(c)));
            } else if (byte[].class == fieldType) {
                field.set(object, cursor.getBlob(c));
            } else if (Date.class == fieldType) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                field.set(object, dateFormat.parse(cursor.getString(c)));
            } else if (Character.TYPE == fieldType) {
                String fieldValue = cursor.getString(c);

                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    field.set(object, Character.valueOf(fieldValue.charAt(0)));
                }
            }
        }
    }

    protected void createMapFromCursor(Cursor cursor, List<Map<String, Object>> list) throws ParseException {
        if (cursor == null) {
            return;
        }

        while (cursor.moveToNext()) {
            Map<String, Object> map = new HashMap<String, Object>();

            for (Field field : allFields) {
                if (field.isAnnotationPresent(Id.class)) {
                    Id id = (Id) field.getAnnotation(Id.class);
                    if (id.composite() > 0) {
                        field.setAccessible(true);
                        Map<String, Object> idMap = new HashMap<String, Object>();
                        for (Field idField : pkFields) {
                            putMapFromCursor(idMap, idField, cursor);
                        }
                        map.put(field.getName(), idMap);
                    }
                }
                putMapFromCursor(map, field, cursor);
            }

            list.add(map);
        }
    }

    protected void putMapFromCursor(Map<String, Object> map, Field field, Cursor cursor) throws ParseException {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = (Column) field.getAnnotation(Column.class);

            field.setAccessible(true);
            Class<?> fieldType = field.getType();

            int c = cursor.getColumnIndex(column.name());
            if (c < 0) {
                return;
            } else if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
                map.put(field.getName(), cursor.getInt(c));
            } else if (String.class == fieldType) {
                map.put(field.getName(), cursor.getString(c));
            } else if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
                map.put(field.getName(), cursor.getLong(c));
            } else if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
                map.put(field.getName(), cursor.getFloat(c));
            } else if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
                map.put(field.getName(), cursor.getShort(c));
            } else if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
                map.put(field.getName(), cursor.getDouble(c));
            } else if (byte[].class == fieldType) {
                map.put(field.getName(), cursor.getBlob(c));
            } else if (Date.class == fieldType) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                map.put(field.getName(), dateFormat.parse(cursor.getString(c)));
            } else if (Character.TYPE == fieldType) {
                String fieldValue = cursor.getString(c);

                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    map.put(field.getName(), Character.valueOf(fieldValue.charAt(0)));
                }
            }
        }
    }
}
