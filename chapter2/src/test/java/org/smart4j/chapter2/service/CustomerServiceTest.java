package org.smart4j.chapter2.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.smart4j.chapter2.model.Customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CustomerServiceTest {

    private final CustomerService customerService;

    CustomerServiceTest() {
        this.customerService = new CustomerService();
    }

    @BeforeEach
    void init() {

    }

    @Test
    void getCustomerList() {
        List<Customer> customerList = customerService.getCustomerList();
        Assertions.assertEquals(2, customerList.size());
    }

    @Test
    void getCustomerById() {
        Long id = 1L;
        Customer customer = customerService.getCustomerById(id);
        Assertions.assertNotNull(customer);
    }

    @Test
    void createCustomer() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "customer100");
        map.put("contact", "John");
        map.put("telephone", "13512345678");
        boolean result = customerService.createCustomer(map);
        Assertions.assertTrue(result);
    }

    @Test
    void updateCustomer() {
        Long id = 1L;
        Map<String, Object> map = new HashMap<>();
        map.put("contact", "Eric");
        boolean result = customerService.updateCustomer(id, map);
        Assertions.assertTrue(result);
    }

    @Test
    void deleteCustomer() {
        Long id = 1L;
        boolean result = customerService.deleteCustomer(id);
        Assertions.assertTrue(result);
    }
}