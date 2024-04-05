package org.example.service;

import org.example.entity.Vehicle;
import org.example.entity.enums.Segment;
import org.example.repository.VehicleRepository;

import java.util.List;

public class VehicleService {

    VehicleRepository vehicleRepository = new VehicleRepository();

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }


    public List<Vehicle> getVehiclesBySegment(Segment segment) {
        return vehicleRepository.getVehiclesBySegment(segment);
    }

    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id).orElse(null);
    }

    public List<Vehicle> getVehicleWithFilter(String filtername, String filterValue){
        return vehicleRepository.getVehiclesWithFilter(filtername, filterValue);
    }



    public String[] getFieldNames() {
        String[] fieldnames = new String[3];
        for (int i = 0; i < fieldnames.length; i++) {
            fieldnames[i] = vehicleRepository.getFieldNames(Vehicle.class)[i+1].getName();
        }
        return fieldnames;
    }

    public List<Vehicle> getAvailableVehicles(){
        return vehicleRepository.getAvailableVehicles();
    }

    public List<Vehicle> getVehiclesInRent(){
        return vehicleRepository.getVehiclesInRent();
    }

    public List<Vehicle> getVehiclesRentedByCustomer(Long customerId){
        return vehicleRepository.getVehiclesRentedByCustomer(customerId);
    }

}
