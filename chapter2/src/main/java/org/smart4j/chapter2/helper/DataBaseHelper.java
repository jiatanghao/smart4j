package org.smart4j.chapter2.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.smart4j.chapter2.util.PropertiesUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class DataBaseHelper {

    private DataBaseHelper() {
    }

    private static final String DRIVER;
    private static final String URL;
    private static final String USERNAME;
    private static final String PASSWORD;
    private static final QueryRunner QUERY_RUNNER;
    private static final ThreadLocal<Connection> THREAD_LOCAL;

    static {
        QUERY_RUNNER = new QueryRunner();
        THREAD_LOCAL = new ThreadLocal<>();
        Properties properties = PropertiesUtil.loadProperties("config.properties");
        DRIVER = PropertiesUtil.getString(properties, "jdbc.driver");
        URL = PropertiesUtil.getString(properties, "jdbc.url");
        USERNAME = PropertiesUtil.getString(properties, "jdbc.username");
        PASSWORD = PropertiesUtil.getString(properties, "jdbc.password");
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            log.error("无法加载jdbc驱动", e);
        }
    }

    public static Connection getConnection() {
        Connection connection = THREAD_LOCAL.get();
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            } catch (SQLException e) {
                log.error("获取连接失败", e);
            } finally {
                THREAD_LOCAL.set(connection);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        Connection connection = THREAD_LOCAL.get();
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                log.error("关闭连接失败", e);
            } finally {
                THREAD_LOCAL.remove();
            }
        }
    }

    public static<T> List<T> queryEntityList(Class<T> entityClass, String sql, Object... params) {
        List<T> entityList = new ArrayList<>();
        Connection connection = getConnection();
        try {
            entityList = QUERY_RUNNER.query(connection, sql, new BeanListHandler<>(entityClass), params);
        } catch (SQLException e) {
            log.error("查询失败", e);
        } finally {
            closeConnection();
        }
        return entityList;
    }

    public static<T> T queryEntity(Class<T> entityClass, String sql, Object... params) {
        Connection connection = getConnection();
        T result = null;
        try {
            result = QUERY_RUNNER.query(connection, sql, new BeanHandler<>(entityClass), params);
        } catch (SQLException e) {
            log.error("查询失败", e);
        } finally {
            closeConnection();
        }
        return result;
    }

    public static List<Map<String, Object>> executeQuery(String sql, Object... params) {
        Connection connection = getConnection();
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            result = QUERY_RUNNER.query(connection, sql, new MapListHandler(), params);
        } catch (SQLException e) {
            log.error("查询失败", e);
        } finally {
            closeConnection();
        }
        return result;
    }

    public static int executeUpdate(String sql, Object... params) {
        Connection connection = getConnection();
        int count = 0;
        try {
            count = QUERY_RUNNER.update(connection, sql, params);
        } catch (SQLException e) {
            log.error("更新失败", e);
        } finally {
            closeConnection();
        }
        return count;
    }

    public static<T> boolean insertEntity(Class<T> entityClass, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            log.error("无法插入对象, fieldMap为空");
            return Boolean.FALSE;
        }
        String sql = "INSERT INTO " + getTableName(entityClass);
        String[] columnsAndValues = getColumnsAndValues(fieldMap);
        String columns = columnsAndValues[0];
        String values = columnsAndValues[1];
        sql += columns + " VALUES " + values;
        return executeUpdate(sql, fieldMap.values().toArray()) == 1;
    }

    public static<T> boolean updateEntity(Class<T> entityClass, Long id, Map<String, Object> fieldMap) {
        if (MapUtils.isEmpty(fieldMap)) {
            log.error("无法更新对象, fieldMap为空");
            return Boolean.FALSE;
        }
        String sql = "UPDATE " + getTableName(entityClass);
        String[] columnsAndValues = getColumnsAndValues(fieldMap);
        String columns = columnsAndValues[0];
        String values = columnsAndValues[1];
        sql += columns + " SET " + values + " WHERE id = ?";
        Collection<Object> paramCollection = fieldMap.values();
        paramCollection.add(id);
        return executeUpdate(sql, paramCollection.toArray()) == 1;
    }

    public static<T> boolean deleteEntity(Class<T> entityClass, Long id) {
        String sql = "DELETE FROM " + getTableName(entityClass) + " WHERE id = ?";
        return executeUpdate(sql, id) == 1;
    }

    private static String[] getColumnsAndValues(Map<String, Object> fieldMap) {
        StringBuilder columns = new StringBuilder("(");
        StringBuilder values = new StringBuilder("(");
        for (String fieldName : fieldMap.keySet()) {
            columns.append(fieldName).append(", ");
            values.append("?, ");
        }
        columns.replace(columns.lastIndexOf(", "), columns.length(), ")");
        values.replace(values.lastIndexOf("?, "), values.length(), ")");
        return new String[] {columns.toString(), values.toString()};
    }

    private static <T> String getTableName(Class<T> entityClass) {
        String simpleName = entityClass.getSimpleName();
        String tableName = simpleName.replaceAll("([A-Z])([a-z\\d]*)", "$1" + "$2" + "_").toLowerCase();
        tableName = tableName.substring(0, tableName.length() - 1);
        return tableName;
    }
}
