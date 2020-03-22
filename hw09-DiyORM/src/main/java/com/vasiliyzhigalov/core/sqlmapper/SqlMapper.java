package com.vasiliyzhigalov.core.sqlmapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlMapper {
    private Class clazz;
    private Map<String, Class> columns = new HashMap<>();
    private Map<String, Object> values = new HashMap<>();
    private SqlQuery sqlQuery = new SqlQuery();
    private String tableName;
    private String idElementName;
    private Object idElementValue;

    public SqlMapper(Class clazz) {
        this.clazz = clazz;
        this.setColumns();
        sqlQuery.setInsert(tableName, columns.keySet());
        sqlQuery.setUpdate(tableName, idElementName, columns.keySet());
        sqlQuery.setSelect(tableName, idElementName, columns.keySet());
    }

    public Object getIdElementValue() {
        return idElementValue;
    }

    public List<String> getValuesToString() {
        List<String> valueList = new ArrayList<>();
        for (String name : values.keySet()) {
            valueList.add(values.get(name).toString());
        }
        return valueList;
    }

    public String getSqlQueryInsert() {

        return sqlQuery.getInsert();
    }

    public String getSqlQueryUpdate() {
        return sqlQuery.getUpdate();
    }

    public String getSqlQuerySelect() {
        return sqlQuery.getSelect();
    }

    private void setColumns() {
        this.tableName = this.clazz.getSimpleName();
        Field[] fields = this.clazz.getDeclaredFields();
        for (Field currentField : fields) {
            currentField.setAccessible(true);
            if (currentField.isAnnotationPresent(id.class)) {
                this.idElementName = currentField.getName();
            } else {
                this.columns.put(currentField.getName(), currentField.getClass());
            }
        }
    }

    public void setValues(Object obj) {
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field currentField : fields) {
            currentField.setAccessible(true);
            try {
                if (currentField.isAnnotationPresent(id.class)) {
                    this.idElementValue = currentField.get(obj);
                } else {
                    this.values.put(currentField.getName(), currentField.get(obj));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public Object getObjectResult(ResultSet resultSet) throws IllegalAccessException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Field[] fields = clazz.getDeclaredFields();
        Object object = clazz.getConstructor().newInstance();
        while (resultSet.next()) {
            for (Field currentField : fields) {
                currentField.setAccessible(true);
                currentField.set(object, resultSetToField(resultSet, currentField.getName(), currentField.getType()));
            }
        }
        return object;
    }

    private Object resultSetToField(ResultSet resultSet, String name, Class clazz) throws SQLException {
        Object value;
        if (clazz == String.class) {
            value = resultSet.getString(name);
        } else if (clazz == Byte.class || clazz == byte.class) {
            value = resultSet.getByte(name);
        } else if (clazz == Integer.class || clazz == int.class) {
            value = resultSet.getInt(name);
        } else if (clazz == Long.class || clazz == long.class) {
            value = resultSet.getLong(name);
        } else if (clazz == Double.class || clazz == double.class) {
            value = resultSet.getDouble(name);
        } else if (clazz == Float.class || clazz == float.class) {
            value = resultSet.getFloat(name);
        } else if (clazz == Boolean.class || clazz == boolean.class) {
            value = resultSet.getBoolean(name);
        } else {
            throw new NullPointerException();
        }
        return value;
    }
}
