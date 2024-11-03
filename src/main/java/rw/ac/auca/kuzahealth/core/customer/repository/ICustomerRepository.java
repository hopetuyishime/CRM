package rw.ac.auca.kuzahealth.core.customer.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import rw.ac.auca.kuzahealth.core.customer.domain.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository interface for managing Customer entities.
 * Provides standard CRUD operations and custom queries for customer management.
 */
@Repository
public interface ICustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

    /**
     * Find a customer by their email address
     *
     * @param email the email address to search for
     * @return an Optional containing the customer if found
     */
    Optional<Customer> findByEmailIgnoreCase(String email);

    /**
     * Find a customer by their phone number
     *
     * @param phoneNumber the phone number to search for
     * @return an Optional containing the customer if found
     */
    Optional<Customer> findByPhoneNumber(String phoneNumber);

    /**
     * Check if a customer exists with the given email
     *
     * @param email the email to check
     * @return true if a customer exists with this email
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Check if a customer exists with the given phone number
     *
     * @param phoneNumber the phone number to check
     * @return true if a customer exists with this phone number
     */
    boolean existsByPhoneNumber(String phoneNumber);

    /**
     * Search for customers by first name or last name
     *
     * @param searchTerm the search term to match against names
     * @param pageable   pagination information
     * @return a Page of matching customers
     */
    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Customer> searchByName(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find customers by partial email match
     *
     * @param emailDomain the email domain to search for
     * @return list of matching customers
     */
    @Query("SELECT c FROM Customer c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :emailDomain))")
    List<Customer> findByEmailDomain(@Param("emailDomain") String emailDomain);

    /**
     * Update customer's phone number
     *
     * @param customerId     the customer's UUID
     * @param newPhoneNumber the new phone number
     * @return number of affected rows
     */
    @Modifying
    @Query("UPDATE Customer c SET c.phoneNumber = :newPhoneNumber WHERE c.id = :customerId")
    int updatePhoneNumber(@Param("customerId") UUID customerId, @Param("newPhoneNumber") String newPhoneNumber);

    /**
     * Find customers whose email or phone number hasn't been verified yet
     *
     * @param pageable pagination information
     * @return Page of unverified customers
     */
    // Assuming you add isEmailVerified and isPhoneVerified fields to Customer entity
    @Query("SELECT c FROM Customer c WHERE c.isEmailVerified = false OR c.isPhoneVerified = false")
    Page<Customer> findUnverifiedCustomers(Pageable pageable);

    /**
     * Delete customers that match the given email domain
     *
     * @param emailDomain the email domain to match
     * @return number of deleted customers
     */
    @Modifying
    @Query("DELETE FROM Customer c WHERE LOWER(c.email) LIKE LOWER(CONCAT('%', :emailDomain))")
    int deleteCustomersByEmailDomain(@Param("emailDomain") String emailDomain);
}