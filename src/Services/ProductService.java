package Services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Bootstrappers.DatabaseBootstrapper;
import Entities.ProductEntity;
import Entities.SellerEntity;
import Entities.UserEntity;

public class ProductService extends CrudService<ProductEntity> {
    private static ProductService productServiceInstance;

    private final DatabaseBootstrapper databaseInstance;

    private ProductService(DatabaseBootstrapper databaseBootstrapper) {
        this.dbTable = ProductEntity.dbTable;
        this.databaseInstance = databaseBootstrapper;
    }

    public static ProductService getInstance(DatabaseBootstrapper databaseBootstrapper) {
        if (productServiceInstance == null) {
            productServiceInstance = new ProductService(databaseBootstrapper);
        }

        return productServiceInstance;
    }

    public Long create(Map<String, ?> dataToFill) {
        return super.create(this.databaseInstance.getConnectionInstance(), dataToFill);
    }

    public void attachAuction(Long productId, Long auctionId) throws Exception {
        Connection connection = this.databaseInstance.getConnectionInstance();

        String query = "INSERT INTO auctions_products (auction_id, product_id) VALUES (?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, auctionId);
        preparedStatement.setLong(2, productId);

        int successful = preparedStatement.executeUpdate();
        if (successful <= 0) {
            throw new Exception("can't insert product in auction");
        }
    }

    public ProductEntity readProductDetails(Scanner scanner, UserEntity authenticatedUser) {
        String optionScanner = null;
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductSeller((SellerEntity) authenticatedUser);

        System.out.println("Please insert the name of the product: ");
        optionScanner = scanner.nextLine();
        productEntity.setProductName(optionScanner);

        System.out.println("Please insert the description of the product: ");
        optionScanner = scanner.nextLine();
        productEntity.setProductDescription(optionScanner);

        System.out.println("Please insert the starting price of the product: ");
        Double price = scanner.nextDouble();
        productEntity.setProductStartingPrice(price);

        return productEntity;
    }

    public List<ProductEntity> getProductsFromSeller(UserEntity userEntity) throws Exception {
        Connection connection = this.databaseInstance.getConnectionInstance();
        List<ProductEntity> productsResult = new ArrayList<>();

        String query = "SELECT * FROM " + this.dbTable + " WHERE user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setLong(1, userEntity.getUserId());

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Double startingPrice = resultSet.getDouble("startingPrice");

            productsResult.add(
                new ProductEntity(id, name, description, startingPrice, (SellerEntity) userEntity)
            );
        }

        resultSet.close();
        preparedStatement.close();
        return productsResult;
    }

    public void delete(Long id) {
        super.delete(this.databaseInstance.getConnectionInstance(), id);
    }

    public void update(Long id, Map<String, ?> dataToFill) {
        super.update(this.databaseInstance.getConnectionInstance(), id, dataToFill);
    }
}
