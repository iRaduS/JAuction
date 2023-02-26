package Entities;

import java.time.ZonedDateTime;
import java.util.List;

public class DutchAuctionEntity extends AuctionEntity { // DutchAuction = go with the higher price and incremental decrease the price.
    private Double maximumCoeficient;
    public DutchAuctionEntity(Long auctionId, List<ProductEntity> auctionProducts, ZonedDateTime auctionStartingTime,
                              String auctionName, String auctionDescription, String auctionLocation,
                              Double maximumCoeficient) {
        super(auctionId, auctionProducts, auctionStartingTime, auctionName, auctionDescription, auctionLocation);

        this.maximumCoeficient = maximumCoeficient;
    }

    public DutchAuctionEntity(Long auctionId, ZonedDateTime auctionStartingTime,
                              String auctionName, String auctionDescription, String auctionLocation,
                              Double maximumCoeficient) {
        super(auctionId, auctionStartingTime, auctionName, auctionDescription, auctionLocation);
        this.maximumCoeficient = maximumCoeficient;
    }
}
