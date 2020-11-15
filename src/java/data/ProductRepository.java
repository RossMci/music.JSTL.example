package data;

import business.Product;
import java.util.List;

public interface ProductRepository {

	Product getProduct(String code);

	List<Product> getProducts();
}
