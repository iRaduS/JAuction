package Entities;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class AuctionEntity {
    protected final Long auctionId;

    protected List<ProductEntity> auctionProducts;

    protected ZonedDateTime auctionStartingTime;

    protected ZonedDateTime auctionStopageTime;

    protected String auctionName;

    protected String auctionDescription;

    protected String auctionLocation;

    public static final String dbTable = "auctions";

    public AuctionEntity(Long auctionId, List<ProductEntity> auctionProducts, ZonedDateTime auctionStartingTime, ZonedDateTime auctionStopageTimem,
                         String auctionName, String auctionDescription, String auctionLocation) {
        this.auctionId = auctionId;
        this.auctionProducts = auctionProducts;
        this.auctionStartingTime = auctionStartingTime;
        this.auctionStopageTime = auctionStopageTimem;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.auctionLocation = auctionLocation;
    }

    public AuctionEntity(Long auctionId, ZonedDateTime auctionStartingTime, ZonedDateTime auctionStopageTime,
                         String auctionName, String auctionDescription, String auctionLocation) {
        this.auctionId = auctionId;
        this.auctionProducts = new ArrayList<>();
        this.auctionStartingTime = auctionStartingTime;
        this.auctionStopageTime = auctionStopageTime;
        this.auctionName = auctionName;
        this.auctionDescription = auctionDescription;
        this.auctionLocation = auctionLocation;
    }
}
