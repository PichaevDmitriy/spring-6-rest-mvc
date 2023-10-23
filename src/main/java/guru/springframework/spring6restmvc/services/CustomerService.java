package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.model.*;

import java.util.*;

public interface CustomerService {
    Customer getCustomerById(UUID id);
    List<Customer> listCustomers();
}
