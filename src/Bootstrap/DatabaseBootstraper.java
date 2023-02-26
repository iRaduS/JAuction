package Bootstrap;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseBootstraper { // can't extend other classes
    private static DatabaseBootstraper databaseInstance; // instance of the singleton

    private static String dbUrlConnectionString;

    private static String dbUserConnectionString;

    private static String dbPasswordConnectionString;

    private static Connection connectionInstance;

    private DatabaseBootstraper(String dbUrl, String dbUser, String dbPassword) throws SQLException {
        dbUrlConnectionString = dbUrl;
        dbUserConnectionString = dbUser;
        dbPasswordConnectionString = dbPassword;

        connectionInstance = getConnection();
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrlConnectionString, dbUserConnectionString, dbPasswordConnectionString);
    }

    public static DatabaseBootstraper getInstance(String dbUrl, String dbUser, String dbPassword) {
        if (databaseInstance != null) {
            return databaseInstance;
        }

        try {
            databaseInstance = new DatabaseBootstraper(dbUrl, dbUser, dbPassword);

            System.out.println("[Database Bootstraper]: Connection established with success!");
            return databaseInstance;
        } catch (SQLException exception) {
            System.out.println(exception);

            return null;
        }
    }
}
