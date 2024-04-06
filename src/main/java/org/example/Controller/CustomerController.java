package org.example.Controller;

import org.example.entity.Customer;
import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.entity.enums.Segment;
import org.example.service.CustomerService;
import org.example.util.Util;

import java.util.List;

public class CustomerController {

    CustomerService customerService = new CustomerService();

    public Customer addNewCustomer(){
        String tckn = Util.stringScanner("Tckn: ");
        String firstname = Util.stringScanner("Firstname: ").toUpperCase();
        String lastname = Util.stringScanner("Lastname: ").toUpperCase();

        Customer newCustomer = Customer.builder()
                .tckn(tckn)
                .firstname(firstname)
                .lastname(lastname)
                .build();

        return customerService.saveCustomer(newCustomer);
    }

//    public List<Rent> getActiveRentOfCustomerByTckn(){
//        Customer customer = getCustomerByTckn();
//        return customerService.getActiveRentOfCustomer(customer);
//    }

    public Customer getCustomerByTckn(){
        String tckn = Util.stringScanner("Customer Tckn: ");
        return customerService.getCustomerByTckn(tckn);
    }


}
