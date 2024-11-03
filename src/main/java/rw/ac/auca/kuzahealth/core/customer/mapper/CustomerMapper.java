package rw.ac.auca.kuzahealth.core.customer.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import rw.ac.auca.kuzahealth.core.customer.domain.Customer;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerCreateRequest;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerResponse;
import rw.ac.auca.kuzahealth.core.customer.dto.CustomerUpdateRequest;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer toEntity(CustomerCreateRequest request);

    CustomerResponse toResponse(Customer customer);

    @Mapping(target = "id", ignore = true)
    void updateEntity(@MappingTarget Customer customer, CustomerUpdateRequest request);
}