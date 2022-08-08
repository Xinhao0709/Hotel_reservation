package service;

import model.Customer;

import java.util.*;

public class CustomerService {
    private Map <String, Customer> customers = new HashMap<String, Customer>();
    private static CustomerService customerService = null;

    private CustomerService() {}
    public static CustomerService getInstance() {
        if (null == customerService) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public void addCustomer(String email, String firstName, String lastName) {
       customers.put(email, new Customer(firstName, lastName, email));
    }
    public Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }
    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }


}
