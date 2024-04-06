package org.example.service;

import org.example.entity.Customer;
import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.repository.CustomerRepository;

import java.util.List;

public class CustomerService {

    CustomerRepository customerRepository = new CustomerRepository();

    public Customer saveCustomer(Customer customer){
        return customerRepository.save(customer);
    }

    public Customer getCustomerByTckn(String tckn){
        return customerRepository.findByTckn(tckn);
    }

//    public List<Rent> getActiveRentOfCustomer(Customer customer){
//        return customerRepository.getActiveRentOfCustomer(customer);
//    }


}
