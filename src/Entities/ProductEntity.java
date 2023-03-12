package Entities;

public class ProductEntity {
    private final Long productId;

    private String productName;

    private String productDescription;

    private Double productStartingPrice;

    private SellerEntity productSeller;

    public static final String dbTable = "products";

    @Override
    public String toString() {
        return "ProductEntity{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productStartingPrice=" + productStartingPrice +
                ", productSeller=" + productSeller +
                '}';
    }

    public ProductEntity(Long productId, String productName, String productDescription, Double productStartingPrice, SellerEntity productSeller) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productSeller = productSeller;
        this.productStartingPrice = productStartingPrice;
    }

    public ProductEntity() {
        this.productId = 0L;
        this.productName = null;
        this.productDescription = null;
        this.productSeller = null;
        this.productStartingPrice = 0D;
    }

    public Long getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public SellerEntity getProductSeller() {
        return productSeller;
    }

    public Double getProductStartingPrice() {
        return productStartingPrice;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public void setProductStartingPrice(Double productStartingPrice) {
        this.productStartingPrice = productStartingPrice;
    }

    public void setProductSeller(SellerEntity productSeller) {
        this.productSeller = productSeller;
    }
}
