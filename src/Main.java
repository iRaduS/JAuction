import Bootstrapers.AuditBootstraper;
import Bootstrapers.DatabaseBootstraper;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        var databaseInstance = DatabaseBootstraper.getInstance("jdbc:mysql://localhost:3306/auctions", "root", "");
        var auditInstance = AuditBootstraper.getInstance("data/system_audit.csv");

        try {
            auditInstance.addNewAuditLog("test action");
        } catch (IOException exception) {
            System.out.println(exception);
        }

        System.out.println("Hello world!");
    }
}