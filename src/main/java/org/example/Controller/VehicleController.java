package org.example.Controller;

import org.example.entity.Customer;
import org.example.entity.Vehicle;
import org.example.entity.enums.Segment;
import org.example.service.VehicleService;
import org.example.util.Util;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class VehicleController {
    VehicleService vehicleService = new VehicleService();

    public Vehicle addNewVehicle() {
        String brand = Util.stringScanner("Brand: ").toUpperCase();
        String model = Util.stringScanner("Model: ").toUpperCase();
        Segment segment = selectSegment();

        Vehicle newVehicle = Vehicle.builder()
                .brand(brand)
                .model(model)
                .segment(segment)
                .build();

        Vehicle savedVehicle = vehicleService.saveVehicle(newVehicle);
        System.out.printf("Success: %s %s %s added into inventory.\n", savedVehicle.getId(),
                savedVehicle.getBrand(), savedVehicle.getModel());
        return savedVehicle;
    }

    public Vehicle getVehicleWithId(Long id) {
        return vehicleService.getVehicleWithId(id);
    }

    public List<Vehicle> searchVehicleWithFilter() {
        String[] vehicleFields = vehicleService.getFieldNames();
        Util.printMenuHeader("SEARCH VEHICLE W.FILTER", 0);
        for (int i = 0; i < vehicleFields.length; i++) {
            System.out.printf("[%d]- %s\n", i + 1, vehicleFields[i]);
        }
        int filterIndex = Util.intScanner("Select Search filter: ");
        if (filterIndex == 3) {
            Segment segment = selectSegment();
            return vehicleService.getVehiclesBySegment(segment);
        }
        String vehicleField = vehicleFields[filterIndex - 1];
        String filterValue = Util.stringScanner(vehicleField + ":").toUpperCase();

        return vehicleService.getVehicleWithFilter(vehicleField, filterValue);
    }

    //todo burda vehicle iki kere donduruluyor list olarak donduruldugu icin mi pk constraint veriyor
    public Vehicle chooseAvailableVehicleBySegment() {
        Segment segment = selectSegment();
        String content = "| %-2s | %-15s | %-15s |";
        String contentFormatted = content.formatted("#", "Brand", "Model");
        int headerLength = Util.printMenuHeader(segment.name(), contentFormatted.length());
        System.out.println(contentFormatted);
        System.out.println("-".repeat(headerLength));
        AtomicInteger count = new AtomicInteger();
        List<Vehicle> vehicles = vehicleService.getVehiclesBySegment(segment);
        vehicles.stream()
                .filter(v -> !v.isInRent()) //bosta olan araclari gosterir.
                .forEach(v -> {

                    System.out.printf("| %-2d | %-15s | %-15s |%n", count.incrementAndGet(), v.getBrand(), v.getModel());

                });
        System.out.println("-".repeat(headerLength));
        int vehicleId = Util.intScanner("Choose Vehicle: ");

        return vehicles.get(vehicleId - 1);
//        return vehicleService.getVehicleById(vehicleId);
    }

    private Segment selectSegment() {
        System.out.println("Select Segment: ");
        for (Segment s : Segment.values()) {
            System.out.printf("[%s] - %s\n", s.ordinal() + 1, s.name());
        }
        int input = Util.intScanner("Input: ");
        return Segment.values()[input - 1];
    }


    public Vehicle selectListedVehicles(List<Vehicle> vehicles) {
        String content = "| %-2s | %-15s | %-15s | %-7s |";
        String contentFormatted = content.formatted("Id", "Brand", "Model", "Available");
        int headerLength = Util.printMenuHeader("", contentFormatted.length());
        System.out.println(contentFormatted);
        System.out.println("-".repeat(headerLength));

        vehicles.forEach(v -> {
            System.out.printf((content) + "%n", v.getId(), v.getBrand(), v.getModel(), !v.isInRent());
        });

        System.out.println("-".repeat(headerLength));

        Vehicle vehicle = null;
        String isProceed = Util.stringScanner("Would you like to rent any of it: (y/n)");
        if (isProceed.equalsIgnoreCase("y")) {
            do {
                Long vehicleId = Util.longScanner("Chose vehicle[id]: ");
                vehicle = vehicleService.getVehicleById(vehicleId);

                if (vehicle.isInRent()) {
                    System.out.printf("%d %s %s is not available, select another vehicle.\n", vehicle.getId(),
                            vehicle.getBrand(), vehicle.getModel());
                }else {
                    return vehicle;
                }
            } while (vehicle.isInRent());
        }
        return null;
    }


    public void printAvailableVehicles() {
        String content = "| %-2s | %-15s | %-15s |";
        String contentFormatted = content.formatted("Id", "Brand", "Model");
        int headerLength = Util.printMenuHeader("Available Vehicles", contentFormatted.length());
        System.out.println(contentFormatted);
        System.out.println("-".repeat(headerLength));

        vehicleService.getAvailableVehicles().forEach(v -> {
            System.out.printf((content) + "%n", v.getId(), v.getBrand(), v.getModel());
        });

        System.out.println("-".repeat(headerLength));
    }


    public void printVehiclesInRent() {
        String content = "| %-2s | %-15s | %-15s | %-12s | %-5s %-6s |";
        String contentFormatted = content.formatted("Id", "Brand", "Model", "TCKN", "Name", "Surname");
        int headerLength = Util.printMenuHeader("Vehicles In Rent", contentFormatted.length());
        System.out.println(contentFormatted);
        System.out.println("-".repeat(headerLength));
        vehicleService.getVehiclesInRent()
                .stream()
                .filter(Vehicle::isInRent)
                .forEach(v -> {
                    System.out.printf((content) + "%n", v.getId(), v.getBrand(), v.getModel(), v.getRents().getFirst().getCustomer().getTckn(),
                            v.getRents().getFirst().getCustomer().getFirstname(), v.getRents().getFirst().getCustomer().getLastname());
                });
        System.out.println("-".repeat(headerLength));
    }

}
