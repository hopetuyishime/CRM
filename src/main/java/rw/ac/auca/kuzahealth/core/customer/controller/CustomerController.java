package rw.ac.auca.kuzahealth.core.customer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import rw.ac.auca.kuzahealth.core.customer.domain.Customer;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerCreateRequest;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerResponse;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerUpdateRequest;
import rw.ac.auca.kuzahealth.core.customer.mapper.CustomerMapper;
import rw.ac.auca.kuzahealth.core.customer.service.ICustomerService;

import java.util.UUID;

/**
 * REST controller for managing Customer operations.
 * Provides endpoints for CRUD operations and various customer-related functionalities.
 */
@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer Controller", description = "APIs for managing customers")
@Validated
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class CustomerController {

    private final ICustomerService customerService;
    private final CustomerMapper customerMapper;

    @PostMapping
    @Operation(summary = "Create a new customer",
            description = "Creates a new customer with the provided information")
    @ApiResponse(responseCode = "201", description = "Customer created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    @ApiResponse(responseCode = "409", description = "Customer with email or phone already exists")
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerCreateRequest request) {

        log.info("Creating new customer with email: {}", request.getEmail());

        Customer customer = customerMapper.toEntity(request);
        Customer createdCustomer = customerService.createCustomer(customer);
        CustomerResponse response = customerMapper.toResponse(createdCustomer);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing customer",
            description = "Updates a customer's information based on the provided data")
    @ApiResponse(responseCode = "200", description = "Customer updated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    @ApiResponse(responseCode = "400", description = "Invalid input data")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable UUID id,
            @Valid @RequestBody CustomerUpdateRequest request) {
        log.info("Updating customer with ID: {}", id);

        request.setId(id);
        Customer updatedCustomer = customerService.updateCustomer(request);
        CustomerResponse response = customerMapper.toResponse(updatedCustomer);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a customer",
            description = "Deletes a customer by their ID")
    @ApiResponse(responseCode = "204", description = "Customer deleted successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        log.info("Deleting customer with ID: {}", id);

        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get customer by ID",
            description = "Retrieves a customer's information by their ID")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable UUID id) {
        log.info("Fetching customer with ID: {}", id);

        Customer customer = customerService.getCustomerById(id);
        CustomerResponse response = customerMapper.toResponse(customer);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all customers",
            description = "Retrieves a paginated list of all customers")
    @ApiResponse(responseCode = "200", description = "Customers retrieved successfully")
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Fetching customers page: {}", pageable.getPageNumber());

        Page<Customer> customers = customerService.getAllCustomers(pageable);
        Page<CustomerResponse> response = customers.map(customerMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    @Operation(summary = "Search customers",
            description = "Search customers by name")
    @ApiResponse(responseCode = "200", description = "Search completed successfully")
    public ResponseEntity<Page<CustomerResponse>> searchCustomers(
            @Parameter(description = "Search term for customer name")
            @RequestParam String query,
            @ParameterObject @PageableDefault(size = 20) Pageable pageable) {
        log.info("Searching customers with query: {}", query);

        Page<Customer> customers = customerService.searchCustomers(query, pageable);
        Page<CustomerResponse> response = customers.map(customerMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-email/{email}")
    @Operation(summary = "Get customer by email",
            description = "Retrieves a customer's information by their email address")
    @ApiResponse(responseCode = "200", description = "Customer found")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<CustomerResponse> getCustomerByEmail(
            @PathVariable String email) {
        log.info("Fetching customer with email: {}", email);

        Customer customer = customerService.findByEmail(email);
        CustomerResponse response = customerMapper.toResponse(customer);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/phone-number")
    @Operation(summary = "Update customer's phone number",
            description = "Updates only the phone number of a customer")
    @ApiResponse(responseCode = "200", description = "Phone number updated successfully")
    @ApiResponse(responseCode = "404", description = "Customer not found")
    public ResponseEntity<Void> updatePhoneNumber(
            @PathVariable UUID id,
            @RequestParam String phoneNumber) {
        log.info("Updating phone number for customer ID: {}", id);

        customerService.updatePhoneNumber(id, phoneNumber);
        return ResponseEntity.ok().build();
    }
}