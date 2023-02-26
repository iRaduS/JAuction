package Services;

import Entities.AuctionEntity;
import Entities.BidEntity;

public class AuctionService extends CrudService<AuctionEntity> {
    private static AuctionService auctionServiceInstance;

    private AuctionService() {
        this.dbTable = AuctionEntity.dbTable;
    }

    public static AuctionService getInstance() {
        if (auctionServiceInstance == null) {
            auctionServiceInstance = new AuctionService();
        }

        return auctionServiceInstance;
    }
}
