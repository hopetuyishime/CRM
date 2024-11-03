package rw.ac.auca.kuzahealth.core.ProductCategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.ac.auca.kuzahealth.core.ProductCategory.domain.ProductCategory;

import java.util.UUID;

@Repository
public interface IProductCategoryRepository extends JpaRepository<ProductCategory, UUID> {
}
