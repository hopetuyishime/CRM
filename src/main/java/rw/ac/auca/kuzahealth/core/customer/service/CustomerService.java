package rw.ac.auca.kuzahealth.core.customer.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import rw.ac.auca.kuzahealth.core.customer.domain.Customer;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerUpdateRequest;
import rw.ac.auca.kuzahealth.core.customer.exception.CustomerAlreadyExistsException;
import rw.ac.auca.kuzahealth.core.customer.exception.CustomerNotFoundException;
import rw.ac.auca.kuzahealth.core.customer.repository.ICustomerRepository;
import rw.ac.auca.kuzahealth.core.util.EDomainState;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

/**
 * Service class for managing customer operations.
 * Provides business logic for customer management including CRUD operations
 * and various customer-specific functionalities.
 */
@Service
@Validated
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer createCustomer(@Valid @NotNull Customer customer) {
        log.info("Creating new customer with email: {}", customer.getEmail());

        // Check if customer already exists
        if (customerRepository.existsByEmailIgnoreCase(customer.getEmail())) {
            log.error("Customer with email {} already exists", customer.getEmail());
            throw new CustomerAlreadyExistsException("Customer with this email already exists");
        }

        if (customerRepository.existsByPhoneNumber(customer.getPhoneNumber())) {
            log.error("Customer with phone number {} already exists", customer.getPhoneNumber());
            throw new CustomerAlreadyExistsException("Customer with this phone number already exists");
        }

        LocalDateTime now = LocalDateTime.now();


      customer.setState(EDomainState.CREATED);
        customer.setCreatedAt(now); // Set created date to now
        customer.setUpdatedAt(now); // Set updated date to now
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Successfully created customer with ID: {}", savedCustomer.getId());
        return savedCustomer;
    }

//    @Override
//    public Customer updateCustomer(Customer customer) {
//        return null;
//    }
//
//    @Override
//    public void deleteCustomer(Customer customer) {
//
//    }
//
//    @Override
//    public Customer getCustomerById(Integer id) {
//        return null;
//    }
//
//    @Override
//    public List<Customer> getAllCustomers() {
//        return List.of();
//    }

    @Override
    @Transactional
    public Customer updateCustomer(@Valid @NotNull CustomerUpdateRequest updateRequest) {
        log.info("Updating customer with ID: {}", updateRequest.getId());

        Customer existingCustomer = getCustomerById(updateRequest.getId());

        // Check if email is being changed and if new email is already in use
        if (!existingCustomer.getEmail().equals(updateRequest.getEmail()) &&
                customerRepository.existsByEmailIgnoreCase(updateRequest.getEmail())) {
            throw new CustomerAlreadyExistsException("Email already in use");
        }

        // Check if phone number is being changed and if new number is already in use
        if (!existingCustomer.getPhoneNumber().equals(updateRequest.getPhoneNumber()) &&
                customerRepository.existsByPhoneNumber(updateRequest.getPhoneNumber())) {
            throw new CustomerAlreadyExistsException("Phone number already in use");
        }

        // Update customer fields
        existingCustomer.setFirstName(updateRequest.getFirstName());
        existingCustomer.setLastName(updateRequest.getLastName());
        existingCustomer.setEmail(updateRequest.getEmail());
        existingCustomer.setPhoneNumber(updateRequest.getPhoneNumber());

        Customer updatedCustomer = customerRepository.save(existingCustomer);
        log.info("Successfully updated customer with ID: {}", updatedCustomer.getId());
        return updatedCustomer;
    }

    @Override
    @Transactional
    public void deleteCustomer(@NotNull UUID customerId) {
        log.info("Deleting customer with ID: {}", customerId);

        Customer customer = getCustomerById(customerId);
        customerRepository.delete(customer);

        log.info("Successfully deleted customer with ID: {}", customerId);
    }

    @Override
    public Customer getCustomerById(@NotNull UUID id) {
        log.debug("Fetching customer with ID: {}", id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
    }

    @Override
    public Page<Customer> getAllCustomers(Pageable pageable) {
        log.debug("Fetching customers page: {}", pageable.getPageNumber());
        return customerRepository.findAll(pageable);
    }

    @Override
    public Customer findByEmail(String email) {
        log.debug("Fetching customer with email: {}", email);
        return customerRepository.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with email: " + email));
    }

    @Override
    public Customer findByPhoneNumber(String phoneNumber) {
        log.debug("Fetching customer with phone number: {}", phoneNumber);
        return customerRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with phone number: " + phoneNumber));
    }

    @Override
    public Page<Customer> searchCustomers(String searchTerm, Pageable pageable) {
        log.debug("Searching customers with term: {}", searchTerm);
        return customerRepository.searchByName(searchTerm, pageable);
    }

    @Override
    @Transactional
    public void updatePhoneNumber(UUID customerId, String newPhoneNumber) {
        log.info("Updating phone number for customer ID: {}", customerId);

        if (customerRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new CustomerAlreadyExistsException("Phone number already in use");
        }

        int updatedCount = customerRepository.updatePhoneNumber(customerId, newPhoneNumber);
        if (updatedCount == 0) {
            throw new CustomerNotFoundException("Customer not found with ID: " + customerId);
        }

        log.info("Successfully updated phone number for customer ID: {}", customerId);
    }
}