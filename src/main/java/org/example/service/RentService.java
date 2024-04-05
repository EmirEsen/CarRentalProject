package org.example.service;

import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.entity.enums.Status;
import org.example.repository.RentRepository;

public class RentService {

    RentRepository rentRepository = new RentRepository();

    public Rent saveRent(Rent rent){

        rent.getVehicle().setInRent(true);
        rent.getCustomer().setHasRent(true);
        System.out.printf("SUCCESS: %s %s rented -> %s %s\n", rent.getCustomer().getFirstname(),
                rent.getCustomer().getLastname(), rent.getVehicle().getBrand(), rent.getVehicle().getModel());
        return rentRepository.save(rent);
    }

    public Rent returnRent(Rent rent){
        rent.getVehicle().setInRent(false);
        rent.getCustomer().setHasRent(false);
        rent.setStatus(Status.PASSIVE);
        System.out.printf("SUCCESS: %s %s returned -> %s %s\n", rent.getCustomer().getFirstname(),
                rent.getCustomer().getLastname(), rent.getVehicle().getBrand(), rent.getVehicle().getModel());
        return rentRepository.returnRent(rent);
    }


}
