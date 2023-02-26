import Bootstrap.DatabaseBootstraper;

public class Main {
    public static void main(String[] args) {
        var databaseInstance = DatabaseBootstraper.getInstance("jdbc:mysql://localhost:3306/auctions", "root", "");

        System.out.println("Hello world!");
    }
}