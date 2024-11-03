package rw.ac.auca.kuzahealth.core.ProductCategory.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.kuzahealth.core.util.AbstractBaseEntity;
import rw.ac.auca.kuzahealth.core.util.EUnit;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class ProductCategory extends AbstractBaseEntity {
    @Column(name = "product_category_name", nullable = false, unique = true)
    private String productCategoryName;
    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;
    @Column(name = "quantity", nullable = false)
    private BigDecimal quantity = BigDecimal.ZERO;
    @Column(name = "unit")
    private EUnit unit;
}
