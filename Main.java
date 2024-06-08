import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private String carId;
    private String type;
    private String model;
    private double pricePerDay;
    private boolean isAvailable;

    public Car(String carId, String type, String model, double pricePerDay) {
        this.carId = carId;
        this.type = type;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.isAvailable = true;
    }

    public String getCarId() {
        return carId;
    }

    public String getType() {
        return type;
    }

    public String getModel() {
        return model;
    }

    public double calculatePrice(int rentalDays) {
        return rentalDays * pricePerDay;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returnCar() {
        isAvailable = true;
    }
}

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = customerId;
        this.name = name;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int rentalDays;

    public Rental(Car car, Customer customer, int rentalDays) {
        this.car = car;
        this.customer = customer;
        this.rentalDays = rentalDays;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getRentalDays() {
        return rentalDays;
    }
}

class RentalAgency {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public RentalAgency() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCars(Car car) {
        cars.add(car);
    }

    public void addCustomers(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("The car you are trying to rent is not available. Select another brand.");
        }
    }

    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car wasn't rented.");
        }
    }

    public void menu() {
        Scanner enter = new Scanner(System.in);
        while (true) {
            System.out.println("===== *Car Rental System* =====");
            System.out.println("1. Rent Car");
            System.out.println("2. Return Car");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = enter.nextInt();
            enter.nextLine();
            if (choice == 1) {
                System.out.println("\n*** Rent a Car ***\n");
                System.out.print("Enter your full name: ");
                String customerName = enter.nextLine();

                System.out.println("\nAvailable Cars include:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarId() + " - " + car.getType() + " " + car.getModel());
                    }
                }
                System.out.println("\nEnter the car id of the car you want to rent: ");
                String carId = enter.nextLine();
                System.out.print("Enter the number of days for rental: ");
                int rentalDays = enter.nextInt();
                enter.nextLine();
                Customer newCustomer = new Customer("CUS" + (customers.size() + 1), customerName);
                addCustomers(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && car.isAvailable()) {
                        selectedCar = car;
                        break;
                    }
                }
                if (selectedCar != null) {
                    double totalPrice = selectedCar.calculatePrice(rentalDays);
                    System.out.println("\n== Rental Information ==\n");
                    System.out.println("Customer ID: " + newCustomer.getCustomerId());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getType() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalPrice);
                    System.out.print("\nConfirm rental (Y/N): ");
                    String confirm = enter.nextLine();
                    if (confirm.equalsIgnoreCase("Y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully.");
                    } else {
                        System.out.println("\nRental canceled.");
                    }
                } else {
                    System.out.println("\nInvalid car selection or car not available for rent.");
                }
            } else if (choice == 2) {
                System.out.println("\n== Return a Car ==\n");
                System.out.print("Enter the car ID you want to return: ");
                String carId = enter.nextLine();
                Car carToReturn = null;
                for (Car car : cars) {
                    if (car.getCarId().equals(carId) && !car.isAvailable()) {
                        carToReturn = car;
                        break;
                    }
                }
                if (carToReturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == carToReturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(carToReturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing.");
                    }
                } else {
                    System.out.println("Invalid car ID or car is not rented.");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
        System.out.println("\nThank you for using the Car Rental System!");
    }
}

public class Main {
    public static void main(String[] args) {
        RentalAgency rentalSystem = new RentalAgency();
        Car car1 = new Car("C01", "Toyota", "Camry", 60.0);
        Car car2 = new Car("C02", "Honda", "Accord", 70.0);
        Car car3 = new Car("C03", "Mahindra", "Thar", 150.0);
        Car car4 = new Car("C04", "Ford", "Mustang", 120.0);
        Car car5 = new Car("C05", "Chevrolet", "Cruze", 80.0);
        Car car6 = new Car("C06", "BMW", "X5", 200.0);
        Car car7 = new Car("C07", "Mercedes-Benz", "E-Class", 180.0);
        Car car8 = new Car("C08", "Audi", "Q7", 220.0);
        Car car9 = new Car("C09", "Nissan", "Altima", 75.0);
        Car car10 = new Car("C10", "Hyundai", "Tucson", 90.0);

        rentalSystem.addCars(car1);
        rentalSystem.addCars(car2);
        rentalSystem.addCars(car3);
        rentalSystem.addCars(car4);
        rentalSystem.addCars(car5);
        rentalSystem.addCars(car6);
        rentalSystem.addCars(car7);
        rentalSystem.addCars(car8);
        rentalSystem.addCars(car9);
        rentalSystem.addCars(car10);

        rentalSystem.menu();
    }
}