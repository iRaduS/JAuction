package Bootstrappers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseBootstrapper { // can't extend other classes
    private static DatabaseBootstrapper databaseInstance; // instance of the singleton

    private static String dbUrlConnectionString;

    private static String dbUserConnectionString;

    private static String dbPasswordConnectionString;

    private static Connection connectionInstance;

    private DatabaseBootstrapper(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        dbUrlConnectionString = dbUrl;
        dbUserConnectionString = dbUser;
        dbPasswordConnectionString = dbPassword;

        connectionInstance = getConnection();
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrlConnectionString, dbUserConnectionString, dbPasswordConnectionString);
    }

    public static DatabaseBootstrapper getInstance(String dbUrl, String dbUser, String dbPassword) {
        if (databaseInstance != null) {
            return databaseInstance;
        }

        try {
            databaseInstance = new DatabaseBootstrapper(dbUrl, dbUser, dbPassword);

            System.out.println("[Database Bootstrapper]: Connection established with success!");
            return databaseInstance;
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());

            return null;
        }
    }

    public Connection getConnectionInstance() {
        return connectionInstance;
    }
}
