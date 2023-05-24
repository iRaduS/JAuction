package Entities;

public class BidEntity {
    private final Long bidId;

    private BidderEntity bidBidder;

    private ProductEntity bidProduct;

    private Double bidSum;

    public static final String dbTable = "bids";

    @Override
    public String toString() {
        return "BidEntity{" +
                "bidId=" + bidId +
                ", bidBidder=" + bidBidder +
                ", bidProduct=" + bidProduct +
                ", bidSum=" + bidSum +
                '}';
    }

    public BidEntity(Long bidId, BidderEntity bidBidder,
                     ProductEntity bidProduct, Double bidSum) {
        this.bidId = bidId;
        this.bidBidder = bidBidder;
        this.bidProduct = bidProduct;
        this.bidSum = bidSum;
    }
}
