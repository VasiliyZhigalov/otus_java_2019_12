package com.vasiliyzhigalov.jdbc.mapper;

import java.util.List;
import java.util.Set;

public class SqlQuery {
    private String insert;
    private String update;
    private String select;

    public SqlQuery() {
    }

    public String getInsert() {
        return insert;
    }

    void setInsert(String tableName, List<String> columns) {
        StringBuilder sql = new StringBuilder();
        StringBuilder sql2 = new StringBuilder();
        sql.append("INSERT INTO ")
                .append(tableName)
                .append("(");
        int i = 0;
        for (String column : columns) {
            sql.append(column);
            sql2.append("?");
            if (i < columns.size() - 1) {
                sql.append(", ");
                sql2.append(", ");
            }
            i++;
        }
        sql.append(") VALUES (");
        sql.append(sql2).append(")");
        this.insert = sql.toString();
    }

    public String getUpdate() {
        return update;
    }

    void setUpdate(String tableName, String idName, List<String> columns) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ")
                .append(tableName)
                .append(" SET ");
        int i = 0;
        for (String column : columns) {
            sql.append(column)
                    .append(" = ?");
            if (i < columns.size() - 1)
                sql.append(", ");
            i++;
        }
        sql.append(" WHERE ").append(idName).append(" = ?");
        this.update = sql.toString();
    }

    public String getSelect() {
        return select;
    }

    void setSelect(String tableName, String idName, List<String> columns) {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT ");
        int i = 0;
        sql.append(idName).append(", ");
        for (String column : columns) {
            sql.append(column);
            if (i < columns.size() - 1)
                sql.append(", ");
            i++;
        }
        sql.append(" FROM ")
                .append(tableName)
                .append(" WHERE ")
                .append(idName)
                .append(" = ?");
        this.select = sql.toString();
    }
}
