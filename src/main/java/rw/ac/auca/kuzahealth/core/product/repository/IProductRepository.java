package rw.ac.auca.kuzahealth.core.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.kuzahealth.core.ProductCategory.domain.ProductCategory;
import rw.ac.auca.kuzahealth.core.product.domain.Product;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface IProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByCategories(Set<ProductCategory> categories);

    List<Product> findAllByCategoriesContaining(ProductCategory category);
}
