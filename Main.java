import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        Product product = new Product();
        
        // Ensure that the products file exists
        try {
            File file = new File("products.txt");
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created: " + file.getName());
            } 
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Main menu loop
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Supplier Management");
            System.out.println("2. Product Management");
            System.out.println("3. Order Management");
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
                        product.productMenu(); 
                        break;
                    case 3:
                        Order.userInterface(scanner); 
                        break;    
                    case 4:
                        System.out.println("Exiting...");
                        scanner.close();
                        return; 
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
    }
}
