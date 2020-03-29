package com.vasiliyzhigalov.jdbc.orm;

import java.util.*;
import java.util.stream.Collectors;

public class SqlQuerySource {
    private String insertQuery;
    private String updateQuery;
    private String selectQuery;

    public SqlQuerySource() {
    }

    public String getInsertQuery() {
        return insertQuery;
    }

    void setInsertQuery(String tableName, List<String> columns) {
        List<String> columnList = new ArrayList<>(columns);
        String columnsFormatter = columnList.stream().collect(Collectors.joining(", ", "(", ")"));
        Collections.fill(columnList, "?");
        String valuesFormatter = columnList.stream().collect(Collectors.joining(", ", " (", ")"));
        this.insertQuery = "INSERT INTO " + tableName + columnsFormatter + "VALUES" + valuesFormatter;
    }

    public String getUpdateQuery() {
        return updateQuery;
    }

    void setUpdateQuery(String tableName, String idName, List<String> columns) {
        String columnsFormatter = columns.stream().collect(Collectors.joining(" = ?, ", "", " = ?"));
        this.updateQuery = "UPDATE " + tableName + " SET " + columnsFormatter + " WHERE " + idName + " = ?";
    }

    public String getSelectQuery() {
        return selectQuery;
    }

    void setSelectQuery(String tableName, String idName, List<String> columns) {
        String columnsFormatter = columns.stream().collect(Collectors.joining(", ", "", ""));
        this.selectQuery = "SELECT " + idName + ", " + columnsFormatter + " FROM " + tableName + " WHERE " + idName + " = ?";
    }
}
