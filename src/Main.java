import Bootstrapers.AuditBootstraper;
import Bootstrapers.DatabaseBootstraper;
import Entities.UserEntity;
import Services.UserService;

import java.io.IOException;
import java.security.MessageDigest;

public class Main {
    public static void main(String[] args) {
        // let's suppose that we pass by parameters by arguments the user and email check for some hashing
        DatabaseBootstraper databaseInstance = DatabaseBootstraper.getInstance(
                "jdbc:mysql://localhost:3306/auctions", "root", ""
        );
        AuditBootstraper auditInstance = AuditBootstraper.getInstance("data/system_audit.csv");
        UserService userService = UserService.getInstance(databaseInstance);

        // prima functionalitate autentificarea unui utilizator.
        UserEntity authenticatedUser;
        try {
            authenticatedUser = userService.loginUser(args[0], args[1]);
            auditInstance.addNewAuditLog(authenticatedUser.getUserName() + " s-a autentificat cu succes!");
        } catch (Exception exception) {
            System.out.println(exception);
        }

        // a doua functionalitate daca utilizatorul e de tip seller sa poata adauga un obiect intr-o licitatie



        try {
            auditInstance.addNewAuditLog("test action");
        } catch (IOException exception) {
            System.out.println(exception);
        }

        System.out.println("Hello world!");
    }
}