package Services;

import Entities.BidEntity;

public class BidService extends CrudService<BidEntity> {
    private static BidService bidServiceInstance;

    private BidService() {
        this.dbTable = BidEntity.dbTable;
    }

    public static BidService getInstance() {
        if (bidServiceInstance == null) {
            bidServiceInstance = new BidService();
        }

        return bidServiceInstance;
    }
}
