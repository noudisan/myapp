package com.common.sqlite;

import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SQLiteHelper {
    public static <T> void createTablesByClasses(SQLiteDatabase db, Class<?>[] clazzs) {
        for (Class<?> clazz : clazzs) {
            createTable(db, clazz);
        }
    }

    public static <T> void dropTablesByClasses(SQLiteDatabase db, Class<?>[] clazzs) {
        for (Class<?> clazz : clazzs) {
            dropTable(db, clazz);
        }
    }

    public static <T> void createTable(SQLiteDatabase db, Class<T> clazz) {
        String tableName = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            tableName = table.name();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName).append("(");

        List<Field> allFields = SQLiteHelper.joinFields(clazz.getDeclaredFields(), clazz.getSuperclass()
                .getDeclaredFields());

        List<String> idColumnNameList = new ArrayList<String>();

        boolean composite = false;
        for (Field field : allFields) {
            if (field.isAnnotationPresent(Id.class)) {
                Id id = (Id) field.getAnnotation(Id.class);
                if (id.composite() > 0) {
                    composite = true;
                    Class<?> idClass = field.getType();
                    List<Field> idFields = SQLiteHelper.joinFields(idClass.getDeclaredFields(), idClass.getSuperclass()
                            .getDeclaredFields());
                    for (Field idField : idFields) {
                        Column column = (Column) idField.getAnnotation(Column.class);
                        idColumnNameList.add(column.name());
                        appendBufferFromField(sb, idField);
                    }
                }
            }
            appendBufferFromField(sb, field);
        }

        if (composite) {
            sb.append("PRIMARY KEY (");
            for (int i = 0; i < idColumnNameList.size(); i++) {
                String idColumnName = idColumnNameList.get(i);
                sb.append(idColumnName);
                if (i < idColumnNameList.size() - 1) {
                    sb.append(", ");
                }
            }
            sb.append("));");
        } else {
            sb.delete(sb.length() - 2, sb.length() - 1);
            sb.append(");");
        }

        String sql = sb.toString();

        db.execSQL(sql);
    }

    private static void appendBufferFromField(StringBuilder sb, Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            Column column = (Column) field.getAnnotation(Column.class);

            String columnType = "";
            if (column.type().equals("")) {
                columnType = getColumnType(field.getType());
            } else {
                columnType = column.type();
            }

            sb.append(column.name() + " " + columnType);

            if (column.length() != 0) {
                sb.append("(" + column.length() + ")");
            }

            if (field.isAnnotationPresent(Id.class)) {
                Id id = (Id) field.getAnnotation(Id.class);
                if (id.composite() == 0) {
                    if (id.generator().equals("AUTOINCREMENT")) {
                        sb.append(" PRIMARY KEY AUTOINCREMENT");
                    } else {
                        sb.append(" PRIMARY KEY");
                    }
                }
            }

            sb.append(", ");
        }
    }

    public static <T> void dropTable(SQLiteDatabase db, Class<T> clazz) {
        String tableName = "";
        if (clazz.isAnnotationPresent(Table.class)) {
            Table table = (Table) clazz.getAnnotation(Table.class);
            tableName = table.name();
        }
        String sql = "DROP TABLE IF EXISTS " + tableName + ";";
        db.execSQL(sql);
    }

    public static List<Field> joinFields(Field[] fields1, Field[] fields2) {
        Map<String, Field> map = new LinkedHashMap<String, Field>();
        for (Field field : fields1) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = (Column) field.getAnnotation(Column.class);
                map.put(column.name(), field);
            } else if (field.isAnnotationPresent(Id.class)) {
                map.put("", field);
            }
        }
        for (Field field : fields2) {
            if (field.isAnnotationPresent(Column.class)) {
                Column column = (Column) field.getAnnotation(Column.class);
                if (!map.containsKey(column.name())) {
                    map.put(column.name(), field);
                }
            } else if (field.isAnnotationPresent(Id.class)) {
                if (!map.containsKey("")) {
                    map.put("", field);
                }
            }
        }
        List<Field> list = new ArrayList<Field>();

        for (String key : map.keySet()) {
            Field tempField = map.get(key);
            if (tempField.isAnnotationPresent(Id.class)) {
                list.add(0, tempField);
            } else {
                list.add(tempField);
            }
        }
        return list;
    }

    private static String getColumnType(Class<?> fieldType) {
        if (String.class == fieldType) {
            return "TEXT";
        }
        if ((Integer.TYPE == fieldType) || (Integer.class == fieldType)) {
            return "INTEGER";
        }
        if ((Long.TYPE == fieldType) || (Long.class == fieldType)) {
            return "BIGINT";
        }
        if ((Float.TYPE == fieldType) || (Float.class == fieldType)) {
            return "FLOAT";
        }
        if ((Short.TYPE == fieldType) || (Short.class == fieldType)) {
            return "INT";
        }
        if ((Double.TYPE == fieldType) || (Double.class == fieldType)) {
            return "DOUBLE";
        }
        if (Blob.class == fieldType) {
            return "BLOB";
        }

        return "TEXT";
    }
}
