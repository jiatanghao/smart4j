package org.smart4j.chapter2.service;

import lombok.extern.slf4j.Slf4j;
import org.smart4j.chapter2.helper.DataBaseHelper;
import org.smart4j.chapter2.model.Customer;

import java.util.List;
import java.util.Map;

@Slf4j
public class CustomerService {

    public List<Customer> getCustomerList() {
        String sql = "SELECT * FROM customer";
        return DataBaseHelper.queryEntityList(Customer.class, sql);
    }

    public Customer getCustomerById(Long id) {
        String sql = "SELECT * FROM customer WHERE id = ?";
        return DataBaseHelper.queryEntity(Customer.class, sql, id);
    }

    public boolean createCustomer(Map<String, Object> fieldMap) {
        return DataBaseHelper.insertEntity(Customer.class, fieldMap);
    }

    public boolean updateCustomer(Long id, Map<String, Object> fieldMap) {
        return DataBaseHelper.updateEntity(Customer.class, id, fieldMap);
    }

    public boolean deleteCustomer(Long id) {
        return DataBaseHelper.deleteEntity(Customer.class, id);
    }
}
