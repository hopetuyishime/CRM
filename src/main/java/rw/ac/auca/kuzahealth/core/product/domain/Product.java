package rw.ac.auca.kuzahealth.core.product.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;
import rw.ac.auca.kuzahealth.core.ProductCategory.domain.ProductCategory;
import rw.ac.auca.kuzahealth.core.util.AbstractBaseEntity;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@FieldNameConstants
public class Product extends AbstractBaseEntity {

    @Column(name="product_name",nullable = false)
    private String productName;

    @Column(name = "serial_number", nullable = false, unique = true)
    private String serialNumber;
    @Column(name = "manufactured_date", nullable = false)
    private LocalDate manufactureDate;
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @ManyToMany
    @JoinTable(
            name = "product_belongs_to",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<ProductCategory> categories;

}
