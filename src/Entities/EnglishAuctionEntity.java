package Entities;

import java.time.ZonedDateTime;
import java.util.List;

public class EnglishAuctionEntity extends AuctionEntity { // EnglishAuction = go with a lower starting price and add incremental slowly.
    private Double incrementalCoeficient;

    public EnglishAuctionEntity(Long auctionId, List<ProductEntity> auctionProducts, ZonedDateTime auctionStartingTime,
                                String auctionName, String auctionDescription, String auctionLocation,
                                Double incrementalCoeficient) {
        super(auctionId, auctionProducts, auctionStartingTime, auctionName, auctionDescription, auctionLocation);
        this.incrementalCoeficient = incrementalCoeficient;
    }

    public EnglishAuctionEntity(Long auctionId, ZonedDateTime auctionStartingTime,
                                String auctionName, String auctionDescription, String auctionLocation,
                                Double incrementalCoeficient) {
        super(auctionId, auctionStartingTime, auctionName, auctionDescription, auctionLocation);
        this.incrementalCoeficient = incrementalCoeficient;
    }
}
