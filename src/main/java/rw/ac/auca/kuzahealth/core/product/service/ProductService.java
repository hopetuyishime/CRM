package rw.ac.auca.kuzahealth.core.product.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import rw.ac.auca.kuzahealth.core.product.domain.Product;
import rw.ac.auca.kuzahealth.core.product.repository.IProductRepository;

import java.util.List;

@AllArgsConstructor
@Service
public class ProductService implements IProductService {

    private IProductRepository productRepository;


    @Override
    public Product registerProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return List.of();
    }

}
