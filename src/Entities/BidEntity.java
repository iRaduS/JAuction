package Entities;

public class BidEntity {
    private final Long bidId;

    private AuctionEntity bidAuction;

    private BidderEntity bidBidder;

    private ProductEntity bidProduct;

    private Double bidSum;

    public BidEntity(Long bidId, AuctionEntity bidAuction, BidderEntity bidBidder,
                     ProductEntity bidProduct, Double bidSum) {
        this.bidId = bidId;
        this.bidAuction = bidAuction;
        this.bidBidder = bidBidder;
        this.bidProduct = bidProduct;
        this.bidSum = bidSum;
    }
}
