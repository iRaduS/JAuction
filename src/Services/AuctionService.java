package Services;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Bootstrapers.DatabaseBootstrapper;
import Entities.AuctionEntity;
import Entities.DutchAuctionEntity;
import Entities.EnglishAuctionEntity;

public class AuctionService extends CrudService<AuctionEntity> {
    private static AuctionService auctionServiceInstance;

    private final DatabaseBootstrapper databaseInstance;

    private AuctionService(DatabaseBootstrapper databaseBootstrapper) {
        this.dbTable = AuctionEntity.dbTable;
        this.databaseInstance = databaseBootstrapper;
    }

    public static AuctionService getInstance(DatabaseBootstrapper databaseBootstrapper) {
        if (auctionServiceInstance == null) {
            auctionServiceInstance = new AuctionService(databaseBootstrapper);
        }

        return auctionServiceInstance;
    }

    public List<AuctionEntity> getAvailableAuctions(ZonedDateTime now) throws Exception {
        Connection connection = this.databaseInstance.getConnectionInstance();
        List<AuctionEntity> availableList = new ArrayList<>();

        String query = "SELECT * FROM " + this.dbTable + " WHERE stoppageTime >= ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        preparedStatement.setDate(1, new Date(java.util.Date.from(now.toInstant()).getTime()));

        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            Double incremental = (double) resultSet.getFloat("incremental");
            Double minimum = (double) resultSet.getFloat("minimum");
            String name = resultSet.getString("name");
            String description = resultSet.getString("description");
            String location = resultSet.getString("location");
            ZonedDateTime startingTime = ZonedDateTime.of(resultSet.getDate("startingTime").toLocalDate(), LocalTime.MIDNIGHT, ZoneId.systemDefault());
            ZonedDateTime stoppageTime = ZonedDateTime.of(resultSet.getDate("stoppageTime").toLocalDate(), LocalTime.MIDNIGHT, ZoneId.systemDefault());

            availableList.add(
                    resultSet.getString("type").equalsIgnoreCase("english")
                            ? new EnglishAuctionEntity(id, startingTime, stoppageTime, name, description, location, incremental)
                            : new DutchAuctionEntity(id, startingTime, stoppageTime, name, description, location, minimum)
            );
        }

        resultSet.close();
        preparedStatement.close();
        return availableList;
    }

    public void create(Map<String, ?> dataToFill) {
        super.create(this.databaseInstance.getConnectionInstance(), dataToFill);
    }
}
