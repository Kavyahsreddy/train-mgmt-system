package com.bnpp.wm.actimize.service;

import com.bnpp.wm.actimize.enums.TrainType;
import com.bnpp.wm.actimize.model.Customer;
import com.bnpp.wm.actimize.model.Order;
import com.bnpp.wm.actimize.model.Train;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class TrainService {
    private List<Train> trains = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Customer> customers = new ArrayList<>();
    private int orderCounter = 1;
    private final Scanner scanner = new Scanner(System.in);

    private static final Path TRAIN_FILE = Paths.get("src/main/resources/trains.csv");
    private static final Path CUSTOMER_FILE = Paths.get("src/main/resources/customers.csv");
    private static final Path ORDER_FILE = Paths.get("src/main/resources/orders.csv");

    public void start() {
        loadData();
        while (true) {
            System.out.println("Welcome to the Train Management System\n1. Admin Panel\n2. Customer Panel\n3. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> adminPanel();
                case 2 -> customerPanel();
                case 3 -> { saveData(); return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    private void loadData() {
        loadTrains();
        loadCustomers();
        loadOrders();
    }

    private void saveData() {
        saveTrains();
        saveCustomers();
        saveOrders();
    }

    private void loadTrains() {
    try (BufferedReader reader = Files.newBufferedReader(TRAIN_FILE)) {
        String line = reader.readLine(); // Skip the header line
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length < 8) {
                System.out.println("Skipping invalid line: " + line);
                continue;
            }
            int trainNumber = Integer.parseInt(parts[0]);  // This will work now
            String trainName = parts[1];
            String source = parts[2];
            String destination = parts[3];
            TrainType type = TrainType.valueOf(parts[4]);
            double price = Double.parseDouble(parts[5]);
            int seats = Integer.parseInt(parts[6]);
            List<String> subStops = Arrays.asList(parts[7].split(";"));
            trains.add(new Train(trainNumber, trainName, source, destination, subStops, type, price, seats));
        }
    } catch (IOException e) {
        System.out.println("Error loading train data.");
    }
}


    private void loadCustomers() {
    try (BufferedReader reader = Files.newBufferedReader(CUSTOMER_FILE)) {
        String line;
        // Skip the header row
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 3) {
                System.out.println("Skipping invalid line: " + line);
                continue;
            }
            try {
                int id = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                Date dob = new SimpleDateFormat("dd-MM-yyyy").parse(parts[2].trim());
                customers.add(new Customer(id, name, dob));
            } catch (Exception e) {
                System.out.println("Error parsing customer: " + line);
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading customer data.");
    }
}

    private void loadOrders() {
    try (BufferedReader reader = Files.newBufferedReader(ORDER_FILE)) {
        String line;
        // Skip the header row
        reader.readLine();
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length != 6) {
                System.out.println("Skipping invalid line: " + line);
                continue;
            }
            try {
                int orderId = Integer.parseInt(parts[0]);
                int customerId = Integer.parseInt(parts[1]);
                int numberOfTickets = Integer.parseInt(parts[2]);
                double totalAmount = Double.parseDouble(parts[3]);
                int trainNumber = Integer.parseInt(parts[4]);
                String trainName = parts[5];
                orders.add(new Order(orderId, customerId, numberOfTickets, totalAmount, trainNumber, trainName));
            } catch (Exception e) {
                System.out.println("Error parsing order: " + line);
            }
        }
    } catch (IOException e) {
        System.out.println("Error loading order data.");
    }
}

    private void saveTrains() {
    try (BufferedWriter writer = Files.newBufferedWriter(TRAIN_FILE)) {
        // Write the header row
        writer.write("Train Number,Train Name,Source,Destination,Train Type,Price,Total Seats,Sub Stops");
        writer.newLine();

        // Write train data
        for (Train train : trains) {
            writer.write(train.trainNumber + "," + train.trainName + "," + train.source + "," +
                    train.destination + "," + train.type + "," + train.price + "," + train.totalSeats + "," +
                    String.join(";", train.subStops));
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving train data.");
    }
}


    private void saveCustomers() {
    try (BufferedWriter writer = Files.newBufferedWriter(CUSTOMER_FILE)) {
        // Write the header row
        writer.write("Customer ID,Name,DOB");
        writer.newLine();

        // Write customer data
        for (Customer customer : customers) {
            writer.write(customer.id + "," + customer.name + "," + new SimpleDateFormat("dd-MM-yyyy").format(customer.dob));
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving customer data.");
    }
}


    private void saveOrders() {
    try (BufferedWriter writer = Files.newBufferedWriter(ORDER_FILE)) {
        // Write the header row
        writer.write("Order ID,Customer ID,Number of Tickets,Total Amount,Train Number,Train Name");
        writer.newLine();

        // Write order data
        for (Order order : orders) {
            writer.write(order.orderId + "," + order.customerId + "," + order.numberOfTickets + "," +
                    order.totalAmount + "," + order.trainNumber + "," + order.trainName);
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Error saving order data.");
    }
}


    public void adminPanel() {
        while (true) {
            System.out.println("\nAdmin Panel\n1. Add Train\n2. Delete Train\n3. Back");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1 -> addTrain();
                case 2 -> deleteTrain();
                case 3 -> { return; }
                default -> System.out.println("Invalid option");
            }
        }
    }

    public void customerPanel() {
        try {
            System.out.print("Enter Customer ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter DOB (dd-MM-yyyy): ");
            String dobStr = scanner.nextLine();

            Date dob = new SimpleDateFormat("dd-MM-yyyy").parse(dobStr);
            Customer customer = new Customer(id, name, dob);

            while (true) {
                System.out.println("\nCustomer Panel\n1. View All Trains\n2. Search Trains\n3. Book Train\n4. Back");
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> viewAllTrains();
                    case 2 -> searchTrains();
                    case 3 -> bookTrain(customer);
                    case 4 -> { return; }
                    default -> System.out.println("Invalid option");
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong. Returning to menu.");
        }
    }

    private void addTrain() {
        try {
            System.out.print("Enter Train Number: ");
            int number = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter Train Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Source: ");
            String source = scanner.nextLine();

            System.out.print("Enter Destination: ");
            String destination = scanner.nextLine();

            System.out.print("Enter Sub Stops (comma-separated): ");
            List<String> subStops = List.of(scanner.nextLine().split(","));

            System.out.print("Enter Train Type (FULL_AC, SUPERFAST, EXPRESS): ");
            TrainType type = TrainType.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter Price: ");
            double price = Double.parseDouble(scanner.nextLine());

            System.out.print("Enter Total Seats: ");
            int seats = Integer.parseInt(scanner.nextLine());

            Train newTrain = new Train(number, name, source, destination, subStops, type, price, seats);
            trains.add(newTrain);
            saveTrains();

            System.out.println("Train added successfully.");
        } catch (Exception e) {
            System.out.println("Invalid input. Try again.");
        }
    }

    private void deleteTrain() {
        System.out.print("Enter Train Number to Delete: ");
        int number = scanner.nextInt();
        scanner.nextLine();

        boolean removed = trains.removeIf(t -> t.trainNumber == number);
        if (removed) {
            saveTrains();
            System.out.println("Train deleted.");
        } else {
            System.out.println("Train not found.");
        }
    }

    private void viewAllTrains() {
        if (trains.isEmpty()) {
            System.out.println("No trains available.");
            return;
        }

        System.out.println("View Trains:\n1. Sort by Train Number\n2. Sort by Train Name");
        int sortChoice = scanner.nextInt();
        scanner.nextLine();

        switch (sortChoice) {
            case 1 -> trains.sort(Comparator.comparingInt(t -> t.trainNumber));
            case 2 -> trains.sort(Comparator.comparing(t -> t.trainName.toLowerCase()));
            default -> System.out.println("Invalid choice. Showing unsorted list.");
        }

        System.out.println("Trains:");
        trains.forEach(System.out::println);
    }

    private void searchTrains() {
        System.out.print("Enter Source: ");
        String source = scanner.nextLine();
        System.out.print("Enter Destination: ");
        String dest = scanner.nextLine();

        boolean found = false;
        for (Train t : trains) {
            if (t.source.equalsIgnoreCase(source) && t.destination.equalsIgnoreCase(dest)) {
                System.out.println(t);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No trains found.");
        }
    }

    private void bookTrain(Customer customer) {
        System.out.print("Enter Train Number to Book: ");
        int trainNo = scanner.nextInt();
        scanner.nextLine();

        Train selectedTrain = null;
        for (Train t : trains) {
            if (t.trainNumber == trainNo) {
                selectedTrain = t;
                break;
            }
        }

        if (selectedTrain == null) {
            System.out.println("Train not found.");
            return;
        }

        System.out.print("Enter number of tickets: ");
        int tickets = scanner.nextInt();
        scanner.nextLine();

        if (tickets > selectedTrain.totalSeats) {
            System.out.println("Not enough seats available.");
            return;
        }

        double totalAmount = tickets * selectedTrain.price;
        Order newOrder = new Order(orderCounter++, customer.id, tickets, totalAmount, selectedTrain.trainNumber, selectedTrain.trainName);
        orders.add(newOrder);
        selectedTrain.totalSeats -= tickets;

        if (customers.stream().noneMatch(c -> c.id == customer.id)) {
            customers.add(customer);
        }

        saveOrders();
        saveCustomers();
        saveTrains();

        System.out.println("Booking successful! Order details:");
        System.out.println(newOrder);
    }
}
