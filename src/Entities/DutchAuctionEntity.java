package Entities;

import java.time.ZonedDateTime;
import java.util.List;

public class DutchAuctionEntity extends AuctionEntity { // DutchAuction = go with the higher price and incremental decrease the price.
    private Double maximumCoeficient;
    public DutchAuctionEntity(Long auctionId, List<ProductEntity> auctionProducts, ZonedDateTime auctionStartingTime,
                              ZonedDateTime auctionStoppageTime,
                              String auctionName, String auctionDescription, String auctionLocation,
                              Double maximumCoeficient) {
        super(auctionId, auctionProducts, auctionStartingTime, auctionStoppageTime, auctionName, auctionDescription, auctionLocation);
        this.maximumCoeficient = maximumCoeficient;
    }

    public DutchAuctionEntity(Long auctionId, ZonedDateTime auctionStartingTime,
                              ZonedDateTime auctionStoppageTime,
                              String auctionName, String auctionDescription, String auctionLocation,
                              Double maximumCoeficient) {
        super(auctionId, auctionStartingTime, auctionStoppageTime, auctionName, auctionDescription, auctionLocation);
        this.maximumCoeficient = maximumCoeficient;
    }
}
