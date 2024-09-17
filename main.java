import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    // Lists to store the products and staff

    static List<Product> products = new ArrayList<>();
    static List<Staff> staffList = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Supplier Management");
            System.out.println("2. Product Management");
            System.out.println("3. Staff Management");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");


            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        Supplier.userInterface(scanner);
                        break;
                    case 2:
                        productMenu(scanner);
                        break;
                    case 3:
                        staffMenu(scanner);
                        break;
                    case 4:
                        System.out.println("Exiting...");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // consume invalid input
            }
        }

        scanner.close();
    }

    // Product Management Menu
    public static void productMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\nProduct Management Menu:");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product Price");
            System.out.println("3. Display Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Product ID: ");
                    String productID = scanner.nextLine();
                    
                    System.out.print("Enter Product Name: ");
                    String productName = scanner.nextLine();
                    
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    
                    System.out.print("Enter Product Price: ");
                    double price = scanner.nextDouble();
                    
                    System.out.print("Enter Product Quantity: ");
                    int quantity = scanner.nextInt();
                    
                    scanner.nextLine();
                    System.out.print("Enter Category: ");
                    String category = scanner.nextLine();

                    Product product = new Product(productID, productName, description, price, quantity, category);
                    products.add(product);
                    System.out.println("Product added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Product ID to update price: ");
                    productID = scanner.nextLine();
                    for (Product p : products) {
                        if (p.getProductID().equals(productID)) {
                            System.out.print("Enter new price: ");
                            price = scanner.nextDouble();
                            p.setProductPrice(price);
                            System.out.println("Product price updated.");
                            break;
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter Product ID to display: ");
                    productID = scanner.nextLine();
                    for (Product p : products) {
                        if (p.getProductID().equals(productID)) {
                            System.out.println(p);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter Product ID to delete: ");
                    productID = scanner.nextLine();
                    products.removeIf(p -> p.getProductID().equals(productID));
                    System.out.println("Product removed.");
                    break;

                case 5:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Staff Management Menu
    public static void staffMenu(Scanner scanner) {
        boolean back = false;

        while (!back) {
            System.out.println("\nStaff Management Menu:");
            System.out.println("1. Add Staff");
            System.out.println("2. Assign Shift to Staff");
            System.out.println("3. Display Staff");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Staff ID: ");
                    int staffID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Staff Name: ");
                    String staffName = scanner.nextLine();
                    System.out.print("Enter Department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter Phone Number: ");
                    int phoneNumber = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();

                    Staff staff = new Staff(staffID, staffName, department, phoneNumber, email);
                    staffList.add(staff);
                    System.out.println("Staff added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Staff ID: ");
                    staffID = scanner.nextInt();
                    scanner.nextLine();
                    for (Staff s : staffList) {
                        if (s.getStaffID() == staffID) {
                            System.out.print("Enter Shift ID: ");
                            int shiftID = scanner.nextInt();
                            scanner.nextLine();
                            System.out.print("Enter Shift Name: ");
                            String shiftName = scanner.nextLine();
                            System.out.print("Enter Start Time: ");
                            int startTime = scanner.nextInt();
                            System.out.print("Enter End Time: ");
                            int endTime = scanner.nextInt();
                            scanner.nextLine();

                            Shift shift = new Shift(shiftID, shiftName, startTime, endTime);
                            s.assignShift(shift);
                            System.out.println("Shift assigned to staff.");
                            break;
                        }
                    }
                    break;

                case 3:
                    System.out.println("All Staff Details:");
                    for (Staff s : staffList) {
                        System.out.println(s);
                    }
                    break;

                case 4:
                    back = true;
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
