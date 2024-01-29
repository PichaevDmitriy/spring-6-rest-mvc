package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.entities.*;
import guru.springframework.spring6restmvc.mappers.*;
import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.repositories.*;
import lombok.*;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;

import java.util.*;
import java.util.concurrent.atomic.*;
import java.util.stream.*;

@Service
@Primary
@AllArgsConstructor
public class CustomerServiceJPA implements CustomerService {
    CustomerRepository repository;
    CustomerMapper customerMapper;
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {

        return Optional.ofNullable(customerMapper.customerToCustomerDTO(repository.findById(id)
                .orElse(null)));
    }

    @Override
    public List<CustomerDTO> listAllCustomers() {
        return repository.findAll().stream()
                .map(customerMapper::customerToCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        Customer customerEntity = repository.save(customerMapper.customerDtoToCustomer(customer));
        return customerMapper.customerToCustomerDTO(customerEntity);
    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        repository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            foundCustomer.setName(customer.getName());
            repository.save(foundCustomer);
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDTO(repository.save(foundCustomer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }

    @Override
    public Boolean deleteCustomerById(UUID customerId) {
        if (repository.existsById(customerId)){
            repository.deleteById(customerId);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID customerId, CustomerDTO customer) {
        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        repository.findById(customerId).ifPresentOrElse(foundCustomer -> {
            if (StringUtils.hasText(customer.getName())){
                foundCustomer.setName(customer.getName());
            }
            repository.save(foundCustomer);
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDTO(repository.save(foundCustomer))));
        }, () -> atomicReference.set(Optional.empty()));

        return atomicReference.get();
    }
}
