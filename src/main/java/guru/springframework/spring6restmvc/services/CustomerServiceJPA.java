package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.mappers.*;
import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.repositories.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    CustomerRepository customerRepository;
    CustomerMapper customerMapper;
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public List<CustomerDTO> listAllCustomers() {
        return null;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerById(UUID customerId) {

    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

    }
}
