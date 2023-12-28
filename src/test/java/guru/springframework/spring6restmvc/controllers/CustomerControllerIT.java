package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.*;
import guru.springframework.spring6restmvc.repositories.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


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
}
