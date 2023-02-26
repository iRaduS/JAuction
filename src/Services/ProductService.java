package Services;

import Entities.ProductEntity;

public class ProductService extends CrudService<ProductEntity> {
    private static ProductService productServiceInstance;

    private ProductService() {
        this.dbTable = ProductEntity.dbTable;
    }

    public static ProductService getInstance() {
        if (productServiceInstance == null) {
            productServiceInstance = new ProductService();
        }

        return productServiceInstance;
    }
}
