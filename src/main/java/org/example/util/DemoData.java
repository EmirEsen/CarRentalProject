package org.example.util;

import org.example.entity.Customer;
import org.example.entity.Vehicle;
import org.example.entity.enums.Segment;
import org.example.repository.CustomerRepository;
import org.example.repository.VehicleRepository;

import java.util.Random;


public class DemoData {

    private final VehicleRepository vehicleRepository;
    private final CustomerRepository customerRepository;


    public DemoData() {
        this.customerRepository = new CustomerRepository();
        this.vehicleRepository = new VehicleRepository();

    }

    public void createDemoData() {
        createVehicles();
        generateSampleCustomers(30);
    }

    private void createVehicles() {
        vehicleRepository.save(Vehicle.builder()
                .brand("MERCEDES")
                .model("A180")
                .segment(Segment.PRESTIGE)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("BMW")
                .model("116i")
                .segment(Segment.PRESTIGE)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("AUDI")
                .model("A1")
                .segment(Segment.PRESTIGE)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("MERCEDES")
                .model("E180")
                .segment(Segment.PREMIUM)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("BMW")
                .model("520i")
                .segment(Segment.PREMIUM)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("AUDI")
                .model("A5")
                .segment(Segment.PREMIUM)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("RENAULT")
                .model("MEGANE")
                .segment(Segment.ECONOMY)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("RENAULT")
                .model("CLIO")
                .segment(Segment.ECONOMY)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("FIAT")
                .model("EAGEA")
                .segment(Segment.ECONOMY)
                .build());
        vehicleRepository.save(Vehicle.builder()
                .brand("NISSAN")
                .model("QASHQAI")
                .segment(Segment.CONFORM)
                .build());


    }


    private static final String[] FIRST_NAMES = {"Ali", "Veli", "Ahmet", "Mehmet", "Ayse", "Fatma", "Zeynep", "Mustafa", "Huseyin", "Osman"};
    private static final String[] LAST_NAMES = {"Yilmaz", "Kaya", "Demir", "Celik", "Arslan", "Koc", "Sahin", "Yıldırım", "Cetin", "Kurt"};

    private void generateSampleCustomers(int count) {
        Random random = new Random();
        for (int i = 0; i < count; i++) {
            String firstName = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
            String lastName = LAST_NAMES[random.nextInt(LAST_NAMES.length)];
            String tckn = generateRandomTCKN();

            customerRepository.save(Customer.builder()
                    .firstname(firstName)
                    .lastname(lastName)
                    .tckn(tckn)
                    .build());
        }
    }


    private static String generateRandomTCKN() {
        Random random = new Random();
        StringBuilder tcknBuilder = new StringBuilder("1");
        for (int i = 1; i < 11; i++) {
            tcknBuilder.append(random.nextInt(10));
        }
        return tcknBuilder.toString();
    }


}
