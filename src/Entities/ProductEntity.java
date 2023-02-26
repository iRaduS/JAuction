package Entities;

public class ProductEntity {
    private final Long productId;

    private String productName;

    private String productDescription;

    private Double productStartingPrice;

    private SellerEntity productSeller;

    public ProductEntity(Long productId, String productName, String productDescription, Double productStartingPrice, SellerEntity productSeller) {
        this.productId = productId;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productStartingPrice = productStartingPrice;
        this.productSeller = productSeller;
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

    public Double getProductStartingPrice() {
        return productStartingPrice;
    }

    public SellerEntity getProductSeller() {
        return productSeller;
    }

    /*
    public void setProductName(String newProductName) {
        productName = newProductName;
    }
    */
}
