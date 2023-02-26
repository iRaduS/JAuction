package Entities;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionEntity {
    protected final Long auctionId;

    protected List<ProductEntity> auctionProducts;

    protected ZonedDateTime auctionStartingTime;

    protected String auctionName;

    protected String auctionDescription;

    protected String auctionLocation;


    public AuctionEntity(Long auctionId, List<ProductEntity> auctionProducts, ZonedDateTime auctionStartingTime, String auctionName,
                         String auctionDescription, String auctionLocation) {
        this.auctionId = auctionId;
        this.auctionProducts = auctionProducts;
        this.auctionStartingTime = auctionStartingTime;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.auctionLocation = auctionLocation;
    }

    public AuctionEntity(Long auctionId, ZonedDateTime auctionStartingTime, String auctionName,
                         String auctionDescription, String auctionLocation) {
        this.auctionId = auctionId;
        this.auctionProducts = new ArrayList<>();
        this.auctionStartingTime = auctionStartingTime;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.auctionLocation = auctionLocation;
    }
}
