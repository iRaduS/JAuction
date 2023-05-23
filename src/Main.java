import Bootstrappers.AuditBootstrapper;
import Bootstrappers.DatabaseBootstrapper;
import Entities.AuctionEntity;
import Entities.BidderEntity;
import Entities.ProductEntity;
import Entities.SellerEntity;
import Entities.UserEntity;
import Services.AuctionService;
import Services.ProductService;
import Services.UserService;

import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void showMenu(UserEntity userEntity) {
        if (userEntity == null) {
            return;
        }

        if (userEntity instanceof SellerEntity) {
            System.out.println("1. Create a product and place it in auction.");
            System.out.println("2. Read all products details in alphabetical order.");
            System.out.println("3. Delete a product and delete it from auction.");
            System.out.println("4. Update a product's details.");
            System.out.println("Please choose from [1-4] or type STOP to exit: ");
            return;
        }

        System.out.println("1. Check all your bid history.");
        System.out.println("2. Bid for a product in an active auction.");
        System.out.println("3. Check your current winning bids.");
        System.out.println("Please choose from [1-3] or type STOP to exit: ");
    }
    public static void main(String[] args) {
        // let's suppose that we pass by parameters by arguments the user and email check for some hashing
        DatabaseBootstrapper databaseInstance = DatabaseBootstrapper.getInstance(
                "jdbc:mysql://localhost:3306/auctions", "root", ""
        );
        AuditBootstrapper auditInstance = AuditBootstrapper.getInstance("data/system_audit.csv");

        // the four services
        UserService userService = UserService.getInstance(databaseInstance);
        ProductService productService = ProductService.getInstance(databaseInstance);
        AuctionService auctionService = AuctionService.getInstance(databaseInstance);

        // prima functionalitate autentificarea unui utilizator.
        UserEntity authenticatedUser = null;
        try {
            authenticatedUser = userService.loginUser(args[0], args[1]);
            if (authenticatedUser == null) {
                throw new Exception("[ERROR]: Can't login.");
            }

            auditInstance.addNewAuditLog(authenticatedUser.getUserName() + " s-a autentificat cu succes!");

            String accountType = authenticatedUser instanceof BidderEntity ? "bidder" : "seller";
            System.out.printf("[AUCTION]: Hello %s(TYPE: %s) you have logged in with success!\n\n", authenticatedUser.getUserName(), accountType);
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        // a doua functionalitate crearea unor noi licitatii daca in baza de date sunt expirate
        try {
            List<AuctionEntity> availableAuctions = auctionService.getAvailableAuctions(ZonedDateTime.now());

            if (availableAuctions.size() == 0) {
                ZonedDateTime start = ZonedDateTime.now(),
                              finish = start.plusDays(2);

                auctionService.create(Map.of(
                "startingTime", start,
                "stoppageTime", finish,
                "name", "English-style Auction from %s - %s".formatted(start.toLocalDateTime(), finish.toLocalDateTime()),
                "description", "Here you can find the english auction",
                "location", "online",
                "type", "english",
                "incremental", 1
                ));

                auctionService.create(Map.of(
                "startingTime", start,
                "stoppageTime", finish,
                "name", "Dutch-style Auction from %s - %s".formatted(start.toLocalDateTime(), finish.toLocalDateTime()),
                "description", "Here you can find the Dutch auction",
                "location", "online",
                "type", "dutch",
                "minimum", 1
                ));
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        // 4 functionalitati daca utilizatorul e de tip seller sa poata adauga detalii despre obiecte
        Scanner scanner = new Scanner(System.in);
        String currentScanner = null; long currentOption;

        while (currentScanner == null || !currentScanner.equalsIgnoreCase("stop")) {
            showMenu(authenticatedUser); currentScanner = scanner.nextLine();

            try {
                currentOption = Long.parseLong(currentScanner);

                switch ((int) currentOption) {
                case 1 -> {
                    ProductEntity productEntity = productService.readProductDetails(scanner, authenticatedUser);

                    Long productId = productService.create(Map.of(
                        "name", productEntity.getProductName(),
                        "description", productEntity.getProductDescription(),
                        "startingPrice", productEntity.getProductStartingPrice(),
                        "user_id", productEntity.getProductSeller().getUserId()
                    ));
                    availableAuctions(productService, auctionService, scanner, productId);
                }
                case 2 -> {
                    List<ProductEntity> productsOnCurrentSeller = productService.getProductsFromSeller(authenticatedUser);
                    System.out.println("Sure here is a list with all the products: ");

                    productsOnCurrentSeller.sort(new ProductComparator());
                    for (ProductEntity productOnCurrentSeller: productsOnCurrentSeller) {
                        System.out.println(productOnCurrentSeller.toString());
                    }
                }
                case 3 -> {
                    List<ProductEntity> productsOnCurrentSeller = productService.getProductsFromSeller(authenticatedUser);

                    System.out.println("Available products on the current account:");
                    for (ProductEntity productOnCurrentSeller : productsOnCurrentSeller) {
                        System.out.println(productOnCurrentSeller.toString());
                    }
                    System.out.println("Please select an ID to delete the product");
                    Long idToDelete = scanner.nextLong();

                    productService.delete(idToDelete);
                    System.out.println("The selected ID of the product was deleted with success!");
                }
                case 4 -> {
                    List<ProductEntity> productsOnCurrentSeller = productService.getProductsFromSeller(authenticatedUser);
                    System.out.println("Available products on the current account:");
                    for (ProductEntity productOnCurrentSeller : productsOnCurrentSeller) {
                        System.out.println(productOnCurrentSeller.toString());
                    }
                    System.out.println("Please select an ID to update the product");
                    Long idToUpdate = scanner.nextLong();
                    scanner.nextLine();

                    ProductEntity productEntity = productService.readProductDetails(scanner, authenticatedUser);
                    productService.update(idToUpdate, Map.of(
                            "name", productEntity.getProductName(),
                            "description", productEntity.getProductDescription(),
                            "startingPrice", productEntity.getProductStartingPrice(),
                            "user_id", productEntity.getProductSeller().getUserId()
                    ));
                    availableAuctions(productService, auctionService, scanner, idToUpdate);
                }
                }
            } catch (NumberFormatException exception) {
                if (currentScanner.equalsIgnoreCase("stop")) {
                    continue;
                }
                currentScanner = scanner.nextLine();
                System.out.printf("[ERROR] Incorrect choice, exception: %s.\n", exception.getMessage());
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
            }
        }
    }

    private static void availableAuctions(ProductService productService, AuctionService auctionService, Scanner scanner, Long idToUpdate) throws Exception {
        List<AuctionEntity> availableAuctions = auctionService.getAvailableAuctions(ZonedDateTime.now());

        System.out.println("Please select the ID of the auction: ");
        availableAuctions.forEach(availableAuction -> {
            System.out.println(availableAuction.toString());
        });

        Long auctionId = scanner.nextLong();
        productService.attachAuction(idToUpdate, auctionId);
    }
}

class ProductComparator implements Comparator<ProductEntity> {
    public int compare(ProductEntity a, ProductEntity b) {
        return a.getProductName().compareTo(b.getProductName());
    }
}