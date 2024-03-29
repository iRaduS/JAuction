package Services;

import Bootstrappers.DatabaseBootstrapper;
import Entities.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BidService extends CrudService<BidEntity> {
    private static BidService bidServiceInstance;

    private final UserService userService;

    private final DatabaseBootstrapper databaseInstance;

    private BidService(DatabaseBootstrapper databaseBootstrapper) {
        this.databaseInstance = databaseBootstrapper;
        this.dbTable = BidEntity.dbTable;
        this.userService = UserService.getInstance(this.databaseInstance);
    }

    public static BidService getInstance(DatabaseBootstrapper databaseBootstrapper) {
        if (bidServiceInstance == null) {
            bidServiceInstance = new BidService(databaseBootstrapper);
        }

        return bidServiceInstance;
    }

    public List<BidEntity> getBidHistoryPerUser(BidderEntity user) throws Exception {
        Connection connection = this.databaseInstance.getConnectionInstance();
        List<BidEntity> availableList = new ArrayList<>();

        String query = "SELECT b.id as b_id, b.sum, p.* FROM " + this.dbTable + " b JOIN products p ON b.product_id = p.id WHERE b.user_id = ?";
        return getBidEntities(user, connection, availableList, query);
    }

    private List<BidEntity> getBidEntities(BidderEntity user, Connection connection, List<BidEntity> availableList, String query) throws Exception {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setLong(1, user.getUserId());

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long b_id = resultSet.getLong("b_id");
            Double sum = resultSet.getDouble("sum");

            Long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            Double startingPrice = resultSet.getDouble("startingPrice");
            Long seller_id = resultSet.getLong("user_id");

            availableList.add(new BidEntity(b_id, user, new ProductEntity(id, name, description, startingPrice, (SellerEntity) this.userService.getUserById(seller_id)), sum));
        }

        resultSet.close();
        preparedStatement.close();
        return availableList;
    }

    public List<BidEntity> getWinningBidsOnProducts(BidderEntity user) throws Exception {
        Connection connection = this.databaseInstance.getConnectionInstance();
        List<BidEntity> availableList = new ArrayList<>();

        String query = "SELECT b.id as b_id, b.sum, p.* FROM " + this.dbTable + " b JOIN ("
                + "SELECT product_id, MAX(sum) AS max_sum FROM bids GROUP BY product_id" +
        ") subquery ON b.product_id = subquery.product_id AND b.sum = subquery.max_sum" +
        " JOIN products p ON subquery.product_id = p.id" +
        " JOIN auctions_products ap ON subquery.product_id = ap.product_id" +
        " JOIN auctions a ON ap.auction_id = a.id" +
        " WHERE b.user_id = ? AND a.stoppageTime < NOW()";
        return getBidEntities(user, connection, availableList, query);
    }

    public void create(Map<String, ?> dataToFill) {
        super.create(this.databaseInstance.getConnectionInstance(), dataToFill);
    }
}
