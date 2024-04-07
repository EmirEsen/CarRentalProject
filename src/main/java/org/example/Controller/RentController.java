package org.example.Controller;

import org.example.entity.Customer;
import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.service.CustomerService;
import org.example.service.RentService;
import org.example.service.VehicleService;
import org.example.util.Util;

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

    public void printVehiclesRentedByCustomer(Customer customer) {
        String content = "| %-2s | %-15s | %-15s | %-12s |";
        String contentFormatted = content.formatted("Id", "Brand", "Model", "Still Rented");
        String header = "Vehicles Rented By %s %s %s".formatted(customer.getTckn(),
                customer.getFirstname(), customer.getLastname());
        int headerLength = Util.printMenuHeader(header, contentFormatted.length());
        System.out.println(contentFormatted);
        System.out.println("-".repeat(headerLength));
        rentService.getVehiclesRentedByCustomer(customer.getTckn()).forEach(rent -> {
            System.out.printf((content) + "%n",
                    rent.getVehicle().getId(), rent.getVehicle().getBrand(), rent.getVehicle().getModel(),
                    rent.getStatus());

        });
        System.out.println("-".repeat(headerLength));
    }
}
