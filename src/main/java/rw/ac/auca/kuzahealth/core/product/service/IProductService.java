package rw.ac.auca.kuzahealth.core.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.auca.kuzahealth.core.product.domain.Product;

import java.util.List;

public interface IProductService {
    Product registerProduct(Product product);

    List<Product> findAllProducts();

    Page<Product> findAllProducts(Pageable pageable);

    List<Product> getProductsByCategory(String category);
}
