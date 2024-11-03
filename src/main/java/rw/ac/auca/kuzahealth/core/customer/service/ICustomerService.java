package rw.ac.auca.kuzahealth.core.customer.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.ac.auca.kuzahealth.core.customer.domain.Customer;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerUpdateRequest;

import java.util.UUID;

public interface ICustomerService {
    Customer createCustomer(@Valid @NotNull Customer customer);

    Customer updateCustomer(@Valid @NotNull CustomerUpdateRequest updateRequest);

    void deleteCustomer(@NotNull UUID customerId);

    Customer getCustomerById(@NotNull UUID id);

    Page<Customer> getAllCustomers(Pageable pageable);

    Customer findByEmail(String email);

    Customer findByPhoneNumber(String phoneNumber);

    Page<Customer> searchCustomers(String searchTerm, Pageable pageable);

    void updatePhoneNumber(UUID customerId, String newPhoneNumber);
}