package com.mycompany.muayene.DBUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Mustafa
 */
public class CRUDShortcuts {

    public static String createQuery(String tableName, String[] columns, String[] values) {
        String query = "INSERT INTO " + tableName + " (";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i];
            if (i != columns.length - 1) {
                query += ", ";
            }
        }
        query += ") VALUES (";
        for (int i = 0; i < values.length; i++) {
            query += "'" + values[i] + "'";
            if (i != values.length - 1) {
                query += ", ";
            }
        }
        query += ")";
        return query;
    }

    public static String readQuery(String tableName) {
        return "SELECT * FROM " + tableName;
    }
    public static String readQuery(String tableName, String column, String value) {
        return "SELECT * FROM " + tableName + " WHERE " + column + " = '" + value + "'";
    }
    public static String readQuery(String tableName, String[] columns, String[] values) {
        String query = "SELECT * FROM " + tableName;
        if (columns == null || values == null) return query;
        query += " WHERE ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + "=" + values[i];
            if (i != columns.length - 1) {
                query += " AND ";
            }
        }
        return query;
    }

    public static String updateQuery(String tableName, String[] columns, String[] values, String[] whereColumns, String[] whereValues) {
        String query = "UPDATE " + tableName + " SET ";
        for (int i = 0; i < columns.length; i++) {
            query += columns[i] + "=" + values[i];
            if (i != columns.length - 1) {
                query += ", ";
            }
        }
        query += " WHERE ";
        for (int i = 0; i < whereColumns.length; i++) {
            query += whereColumns[i] + "=" + whereValues[i];
            if (i != whereColumns.length - 1) {
                query += " AND ";
            }
        }
        return query;
    }

    public static String deleteQuery(String tableName, String column, String value) {
        return "DELETE FROM " + tableName + " WHERE " + column + " = '" + value + "'";
    }
    public static String deleteQuery(String tableName, String[] whereColumns, String[] whereValues) {
        String query = "DELETE FROM " + tableName + " WHERE ";
        for (int i = 0; i < whereColumns.length; i++) {
            query += whereColumns[i] + "= '" + whereValues[i] + "'";
            if (i != whereColumns.length - 1) {
                query += " AND ";
            }
        }
        return query;
    }

    public static void execute(String query) {
        Connection connection = Mssql.getConnection();
        try {
            connection.createStatement().execute(query);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int executeUpdate(String query) {
        Connection connection = Mssql.getConnection();
        int result = 0;
        try {
            result = connection.createStatement().executeUpdate(query);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}

