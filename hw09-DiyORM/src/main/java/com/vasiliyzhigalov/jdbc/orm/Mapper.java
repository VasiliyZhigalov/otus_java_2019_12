package com.vasiliyzhigalov.jdbc.orm;

import com.vasiliyzhigalov.core.model.Id;

import java.lang.reflect.Field;
import java.util.*;

public class Mapper {
    private List<String> columns = new ArrayList<>();
    private MetadataSource metadataSource;
    private SqlQuerySource sqlQuery;
    private String tableName;
    private String idElementName;

    public Mapper(Class clazz) {
        this.tableName = clazz.getSimpleName();
        this.metadataSource = new MetadataSource(clazz);
        this.setColumns();
        this.sqlQuery = new SqlQuerySource();
        this.sqlQuery.setInsertQuery(tableName, columns);
        this.sqlQuery.setUpdateQuery(tableName, idElementName, columns);
        this.sqlQuery.setSelectQuery(tableName, idElementName, columns);
    }

    public String getSqlQueryInsert() {
        return sqlQuery.getInsertQuery();
    }

    public String getSqlQueryUpdate() {
        return sqlQuery.getUpdateQuery();
    }

    public String getSqlQuerySelect() {
        return sqlQuery.getSelectQuery();
    }

    private void setColumns() {
        idElementName = metadataSource.getIdField().getName();
        for (Field currentField : metadataSource.getFields()) {
            currentField.setAccessible(true);
            this.columns.add(currentField.getName());
        }
    }

    public Object getIdElementValue(Object object) {
        return metadataSource.getIdValue(object);
    }

    public List<Object> getValues(Object object) {
        return metadataSource.getValues(object);
    }

}
