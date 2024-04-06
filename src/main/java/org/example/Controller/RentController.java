package org.example.Controller;

import org.example.entity.Customer;
import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.service.CustomerService;
import org.example.service.RentService;
import org.example.service.VehicleService;

public class RentController {

    RentService rentService = new RentService();

    public Rent newRent(Customer customer, Vehicle vehicle){
        Rent rent = Rent.builder()
                .customer(customer)
                .vehicle(vehicle)
                .build();

        return rentService.saveRent(rent);
    }

    public Rent returnRent(Rent rent){
        return rentService.returnRent(rent);
    }

    public Rent getCustomerActiveRent(Customer customer){
        return rentService.getCustomerActiveRent(customer);
    }
}
