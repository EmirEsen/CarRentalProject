package org.example;

import org.example.Controller.CustomerController;
import org.example.Controller.RentController;
import org.example.Controller.VehicleController;
import org.example.entity.Customer;
import org.example.entity.Rent;
import org.example.entity.Vehicle;
import org.example.util.DemoData;
import org.example.util.Util;

import java.util.List;

public class Main {

    static VehicleController vehicleController = new VehicleController();
    static CustomerController customerController = new CustomerController();
    static RentController rentController = new RentController();

    public static void main(String[] args) {

        //Bu satiri calistirarak veri tabanina musteri ve arac ekleyebilirsiniz.
//        new DemoData().createDemoData();

        menu();


    }

    public static void menu() {
        while (true) {
            System.out.println("""
                    ------- MENU -------
                     [1]- Add Vehicle
                     [2]- Search Vehicle
                     [3]- Add Customer
                     [4]- Rent Menu
                     [5]- Reports
                     [0]- Quit
                    """);
            int input = Util.intScanner("input: ");
            switch (input) {
                case 1 -> vehicleController.addNewVehicle();
                case 2 -> {
                    Vehicle vehicle = vehicleController.selectListedVehicles(vehicleController.searchVehicleWithFilter());
                    if (vehicle != null ) {
                        Customer customer = customerController.getCustomerByTckn();
                        rentController.newRent(customer, vehicle);
                    }
                }
                case 3 -> customerController.addNewCustomer();
                case 4 -> rentMenu();
                case 5 -> reportMenu();
                case 0 -> System.exit(0);

            }
        }
    }


    public static void rentMenu(){
        while (true) {
            System.out.println("""
                    ------- RENT MENU -------
                     [1]- New Rent
                     [2]- Return Rental
                     [0]- Back
                    """);
            int input = Util.intScanner("input: ");
            switch (input) {
                case 1 -> {
                    Customer customer = customerController.getCustomerByTckn();
                    if (customer.isHasRent()){
                        System.out.printf("WARNING: %s %s %s hasn't returned the vehicle %s yet\n",
                                customer.getTckn(), customer.getFirstname(), customer.getLastname(),
                               rentController.getCustomerActiveRent(customer).getVehicle().getModel());
                        break;
                    }
                    Vehicle vehicle = vehicleController.chooseAvailableVehicleBySegment();

                    rentController.newRent(customer, vehicle);
                }
                case 2 -> {
//                    List<Rent> activeRentOfCustomer = customerController.getActiveRentOfCustomerByTckn();
                    Rent activeRent = rentController.getCustomerActiveRent(customerController.getCustomerByTckn());
                    rentController.returnRent(activeRent);
                }
                case 0 -> menu();

            }
        }

    }

    public static void reportMenu() {
        while (true) {
            System.out.println("""
                    ------- REPORTS -------
                     [1]- Show Vehicles In Rent
                     [2]- Show Available Vehicles
                     [3]- Show Vehicles Rented By Customer
                     [0]- Quit
                    """);
            int input = Util.intScanner("input: ");
            switch (input) {
                case 1 -> vehicleController.printVehiclesInRent();
                case 2 -> vehicleController.printAvailableVehicles();
                case 3 -> {
                    Customer customer = customerController.getCustomerByTckn();
                    rentController.printVehiclesRentedByCustomer(customer);
                }
                case 0 -> menu();

            }
        }
    }


}