package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;



public class SQLHelper {
    private static QueryRunner runner = new QueryRunner();

    private static String url = System.getProperty("datasource.url");
    private static String user = System.getProperty("datasource.user");
    private static String password = System.getProperty("datasource.password");

    private SQLHelper() {
    }

    @SneakyThrows
    private static Connection getConn() {
        return DriverManager.getConnection(url, user, password);
    }

    @SneakyThrows
    public static String getBuyStatus() {
        String statusSQL = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1";
        return getResult(statusSQL);
    }

    @SneakyThrows
    public static String getCreditStatus() {
        String statusSQL = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1";
        return getResult(statusSQL);
    }

    @SneakyThrows
    private static String getResult(String query) {
        String result = "";
        var runner = new QueryRunner();
        try (var connection = getConn()) {
            result = runner.query(connection, query, new ScalarHandler<>());
        }
        return result;
    }

    @SneakyThrows
    public static void clearDB() {
        var connection = getConn();
        runner.execute(connection, "DELETE FROM credit_request_entity");
        runner.execute(connection, "DELETE FROM order_entity");
        runner.execute(connection, "DELETE FROM payment_entity");
    }
}
