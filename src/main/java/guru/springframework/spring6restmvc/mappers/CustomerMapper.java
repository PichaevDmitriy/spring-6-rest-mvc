package guru.springframework.spring6restmvc.mappers;

import guru.springframework.spring6restmvc.entities.*;
import guru.springframework.spring6restmvc.model.*;
import org.mapstruct.*;


@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDTO dto);
    CustomerDTO customerToCustomerDTO(Customer customer);
}
