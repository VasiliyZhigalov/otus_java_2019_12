package com.vasiliyzhigalov.jdbc.mapper;

import com.vasiliyzhigalov.core.model.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SqlMapper {
    private Class clazz;
    private List<String> columns = new ArrayList<>();
    private SqlQuery sqlQuery = new SqlQuery();
    private String tableName;
    private String idElementName;
    private Object idElementValue;

    public SqlMapper(Class clazz) {
        this.clazz = clazz;
        this.setColumns();
        sqlQuery.setInsert(tableName, columns);
        sqlQuery.setUpdate(tableName, idElementName, columns);
        sqlQuery.setSelect(tableName, idElementName, columns);
    }

    public Object getIdElementValue() {
        return idElementValue;
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
            if (currentField.isAnnotationPresent(Id.class)) {
                this.idElementName = currentField.getName();
            } else {
                this.columns.add(currentField.getName());
            }
        }
    }

    public List<Object> getValues(Object obj) {
        List<Object> values = new ArrayList<>();
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field currentField : fields) {
            currentField.setAccessible(true);
            try {
                if (currentField.isAnnotationPresent(Id.class)) {
                    this.idElementValue = currentField.get(obj);
                } else {
                    values.add(currentField.get(obj));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    public Object getObjectResult(ResultSet resultSet) throws IllegalAccessException, SQLException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Field[] fields = clazz.getDeclaredFields();
        Object object = clazz.getConstructor().newInstance();
        while (resultSet.next()) {
            for (Field currentField : fields) {
                currentField.setAccessible(true);
                Object value = resultSet.getObject(currentField.getName());
                currentField.set(object, value);
            }
        }
        return object;
    }
}
