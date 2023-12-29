package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.entities.*;
import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.repositories.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.springframework.http.*;
import org.springframework.test.annotation.*;
import org.springframework.transaction.annotation.*;


import java.util.*;

@SpringBootTest
public class CustomerControllerIT {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository  customerRepository;

    @Test
    void listCustomers(){
        List<CustomerDTO> customers = customerController.listAllCustomers();
        assertThat(customers.size()).isEqualTo(2);
    }

    @Test
    void getById(){
        UUID customerId = customerRepository.findAll().get(0).getId();
        CustomerDTO customerDTO = customerController.getCustomerById(customerId);
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void getByIdNotFound(){
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    @Transactional
    @Rollback
    void updateById(){
        Customer existingCustomer = customerRepository.findAll().get(0);
        final String newCustomerName = "New name";
        CustomerDTO dto = CustomerDTO.builder()
                .customerName(newCustomerName)
                .build();
        ResponseEntity responseEntity = customerController.updateCustomerById(existingCustomer.getId(), dto);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);

        Customer updatedCustomer = customerRepository.findById( existingCustomer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(newCustomerName);
    }

    @Test
    void updateCustomerNotFound(){
        CustomerDTO dto = CustomerDTO.builder().build();
        assertThrows(NotFoundException.class, () -> customerController.updateCustomerById(UUID.randomUUID(), dto));
    }


    @Test
    @Transactional
    @Rollback
    void patchById(){
        Customer existingCustomer = customerRepository.findAll().get(0);
        final String newCustomerName = "New name";
        CustomerDTO dto = CustomerDTO.builder()
                .customerName(newCustomerName)
                .build();
        ResponseEntity responseEntity = customerController.patchCustomerById(existingCustomer.getId(), dto);
        assertThat(responseEntity.getStatusCode().value()).isEqualTo(204);

        Customer updatedCustomer = customerRepository.findById( existingCustomer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo(newCustomerName);
    }

    @Test
    void patchCustomerNotFound(){
        CustomerDTO dto = CustomerDTO.builder().build();
        assertThrows(NotFoundException.class, () -> customerController.patchCustomerById(UUID.randomUUID(), dto));
    }


    @Test
    @Transactional
    @Rollback
    void saveNewCustomer(){
        final String newCustomerName = "New name";
        CustomerDTO dto = CustomerDTO.builder()
                .customerName(newCustomerName)
                .build();

        ResponseEntity responseEntity = customerController.handlePost(dto);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(201));
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();

        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);

        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }


    @Test
    @Transactional
    @Rollback
    void deleteById(){
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteCustomerById(customer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(customer.getId()));
    }

    @Test
    @Transactional
    @Rollback
    void deleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomerById(UUID.randomUUID()));
    }
}
