package Entities;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public abstract class AuctionEntity {
    protected final Long auctionId;

    protected List<ProductEntity> auctionProducts;

    protected ZonedDateTime auctionStartingTime;

    protected ZonedDateTime auctionStoppageTime;

    protected String auctionName;

    protected String auctionDescription;

    protected String auctionLocation;

    public static final String dbTable = "auctions";

    public AuctionEntity(Long auctionId, List<ProductEntity> auctionProducts, ZonedDateTime auctionStartingTime, ZonedDateTime auctionStoppageTime,
                         String auctionName, String auctionDescription, String auctionLocation) {
        this.auctionId = auctionId;
        this.auctionProducts = auctionProducts;
        this.auctionStartingTime = auctionStartingTime;
        this.auctionStoppageTime = auctionStoppageTime;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.auctionLocation = auctionLocation;
    }

    public AuctionEntity(Long auctionId, ZonedDateTime auctionStartingTime, ZonedDateTime auctionStoppageTime,
                         String auctionName, String auctionDescription, String auctionLocation) {
        this.auctionId = auctionId;
        this.auctionProducts = new ArrayList<>();
        this.auctionStartingTime = auctionStartingTime;
        this.auctionStoppageTime = auctionStoppageTime;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.auctionLocation = auctionLocation;
    }
}
