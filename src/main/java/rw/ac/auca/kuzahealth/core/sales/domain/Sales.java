package rw.ac.auca.kuzahealth.core.sales.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import rw.ac.auca.kuzahealth.core.customer.domain.Customer;
import rw.ac.auca.kuzahealth.core.product.domain.Product;
import rw.ac.auca.kuzahealth.core.util.AbstractBaseEntity;
import rw.ac.auca.kuzahealth.core.util.ESalesStatus;

import java.util.List;


@Entity
@Getter
@Setter
public class Sales extends AbstractBaseEntity {
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESalesStatus status = ESalesStatus.INITIATED;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @OneToMany
    @JoinColumn(name = "product_ids", nullable = false)
    private List<Product> products;


}
