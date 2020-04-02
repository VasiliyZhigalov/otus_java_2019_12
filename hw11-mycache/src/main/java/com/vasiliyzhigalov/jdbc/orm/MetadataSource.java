package com.vasiliyzhigalov.jdbc.orm;

import com.vasiliyzhigalov.core.model.Id;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class MetadataSource {
    private Field[] fields;
    private Field idField;

    public MetadataSource(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        this.fields = new Field[fields.length - 1];
        int fieldIndex = 0;
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Id.class)) {
                this.idField = field;
            } else {
                this.fields[fieldIndex++] = field;
            }
        }
    }

    public Field[] getFields() {
        return fields;
    }

    public Field getIdField() {
        return idField;
    }

    public List<Object> getValues(Object obj) {
        List<Object> values = new ArrayList<>();
        for (Field currentField : this.fields) {
            try {
                values.add(currentField.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return values;
    }

    public Object getIdValue(Object object) {
        Object result = new Object();
        try {
            result = idField.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

}
