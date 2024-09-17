import java.io.*;
import java.util.*;

public class Order {

    private String orderID;
    private String supplierID; 
    private String productID;
    private int quantity;
    private String status;

    private static final String SUPPLIER_FILE = "supplier.txt"; 
    private static final String PRODUCT_FILE = "products.txt";
    private static final String SUPPLIER_ORDER_FILE = "orderSupplier.txt";
    private static final String MEMBER_ORDER_FILE = "orderMember.txt";


    public Order(String orderID, String supplierID, String productID, int quantity, String status) {
        this.orderID = orderID;
        this.supplierID = supplierID;
        this.productID = productID;
        this.quantity = quantity;
        this.status = status;
    }


    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Generate the orderID
    private static String generateOrderID() {
        int orderCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(SUPPLIER_ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                orderCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return String.format("SO%03d", orderCount + 1);
    }

    private static boolean supplierExists(String supplierID) {
    File file = new File(SUPPLIER_FILE);
    if (!file.exists() || !file.canRead()) {
        System.out.println("Supplier file does not exist or cannot be read.");
        return false;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] details = line.split(",");
            if (details[0].trim().equals(supplierID)) {
                return true;
            }
        }
    } catch (IOException e) {
        System.out.println("An error occurred while reading the supplier file.");
        e.printStackTrace();
    }
    	return false;
	}


    // Check if productID exists in the product file
    private static boolean productExists(String productID) {
    File file = new File(PRODUCT_FILE);
    if (!file.exists() || !file.canRead()) {
        System.out.println("Product file does not exist or cannot be read.");
        return false;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        String currentProductID = null;
        while ((line = reader.readLine()) != null) {
            line = line.trim();
            if (line.isEmpty() || line.equals("--")) continue; 
            
            if (line.startsWith("Product ID:")) {
                currentProductID = line.split(":")[1].trim();
            }
            
            if (currentProductID != null && currentProductID.equals(productID)) {
                return true; 
            }
        }
    } catch (IOException e) {
        System.out.println("An error occurred while reading the product file.");
        e.printStackTrace();
    }
    return false;
}


    //add an order to the supplier file
    public static void addOrderToSupplier(Order order) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SUPPLIER_ORDER_FILE, true))) {
            writer.write(order.getOrderID() + "," + order.getSupplierID() + "," +
                         order.getProductID() + "," + order.getQuantity() + "," +
                         order.getStatus());
            writer.newLine();
            System.out.println("Order added to supplier successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while adding the order to supplier.");
            e.printStackTrace();
        }
    }

    //read all orders from the supplier file
    public static void readOrdersFromSupplier() {
        try (BufferedReader reader = new BufferedReader(new FileReader(SUPPLIER_ORDER_FILE))) {
            String line;
            System.out.println("\nOrders from Suppliers:");
            System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "OrderID", "SupplierID", "ProductID", "Quantity", "Status");
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] details = line.split(",");
                if (details.length == 5) {
                    System.out.printf("%-10s %-10s %-10s %-10d %-10s\n", details[0].trim(), details[1].trim(), details[2].trim(), Integer.parseInt(details[3].trim()), details[4].trim());
                } else {
                    System.out.println("Invalid order data: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the orders from supplier.");
            e.printStackTrace();
        }
    }

    
	//update an order status in the member file
	public static void updateOrderStatus(String orderID, String newStatus) {
    StringBuilder content = new StringBuilder();
    boolean updated = false;

    
    try (BufferedReader reader = new BufferedReader(new FileReader(MEMBER_ORDER_FILE))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] details = line.split(",");
            if (details[0].equals(orderID)) {
                
                line = orderID + "," + details[1] + "," + details[2] + "," + details[3] + "," + newStatus;
                updated = true;
                
            }
            content.append(line).append("\n");
        }
    } catch (IOException e) {
        e.printStackTrace();
    }

    if (!updated) {
        System.out.println("Order not found.");
        return;
    }

    // Write the updated content back to the file
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(MEMBER_ORDER_FILE))) {
        writer.write(content.toString());
        System.out.println("File updated successfully."); // Debug statement
    	} catch (IOException e) {
        	e.printStackTrace();
    	}
	}



    
    // Method to read all orders from the member file
	public static void readOrdersFromMember() {
    try (BufferedReader reader = new BufferedReader(new FileReader(MEMBER_ORDER_FILE))) {
        String line;
        System.out.println("\nOrders from Members:");
        System.out.printf("%-10s %-10s %-10s %-10s %-10s\n", "OrderID", "MemberID", "ProductID", "Quantity", "Status");
        	while ((line = reader.readLine()) != null) {
            	if (line.trim().isEmpty()) continue;
            	String[] details = line.split(",");
            	if (details.length == 5) {
                	System.out.printf("%-10s %-10s %-10s %-10d %-10s\n", details[0].trim(), details[1].trim(), details[2].trim(), Integer.parseInt(details[3].trim()), details[4].trim());
            	}else {
                	System.out.println("Invalid order data: " + line);
            	}
        	}
    	} catch (IOException e) {
        System.out.println("An error occurred while reading the orders from members.");
        e.printStackTrace();
   	 	}
	}


    // User interface method for order management
    public static void userInterface(Scanner scanner) {
        while (true) {
            System.out.println("\nOrder Management:");
            System.out.println("1. Manage Orders from Suppliers");
            System.out.println("2. Manage Orders from Members");
            System.out.println("3. Back to Main Menu");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        manageOrdersFromSupplier(scanner);
                        break;
                    case 2:
                        manageOrdersFromMember(scanner);
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private static void manageOrdersFromSupplier(Scanner scanner) {
        while (true) {
            System.out.println("\nSupplier Orders Management:");
            System.out.println("1. Add Order to Supplier");
            System.out.println("2. Read Orders from Supplier");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        addOrderUI(scanner);
                        break;
                    case 2:
                        readOrdersFromSupplier();
                        break;
                    case 3:
                        return; 
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();
            }
        }
    }

    private static void manageOrdersFromMember(Scanner scanner) {
        while (true) {
            System.out.println("\nMember Orders Management:");
            System.out.println("1. Read Orders from Member");
            System.out.println("2. Update Order Status");
            System.out.println("3. Back");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        readOrdersFromMember();
                        break;
                    case 2:
                        updateOrderStatusUI(scanner);
                        break;
                    case 3:
                        return; 
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
    }

    private static void addOrderUI(Scanner scanner) {
        System.out.print("Enter Supplier ID: ");
        String supplierID = scanner.nextLine();
        System.out.print("Enter Product ID: ");
        String productID = scanner.nextLine();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine(); 

        if (!supplierExists(supplierID)) {
            System.out.println("Supplier ID does not exist.");
            return;
        }
        if (!productExists(productID)) {
            System.out.println("Product ID does not exist.");
            return;
        }

        String orderID = generateOrderID();
        String status = "pending"; 
        Order order = new Order(orderID, supplierID, productID, quantity, status);
        addOrderToSupplier(order);
    }

    private static void updateOrderStatusUI(Scanner scanner) {
        System.out.print("Enter Order ID to update: ");
        String orderID = scanner.nextLine();
        System.out.print("Enter new Status (e.g., successful, rejected): ");
        String status = scanner.nextLine();
        updateOrderStatus(orderID, status);
    }
}
