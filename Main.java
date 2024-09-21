import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	
    static List<Staff> staffList = new ArrayList<>();
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        Payment payment = new Payment();
        Stock stock = new Stock();
        
        boolean exit = false;

       
        while (true) {
            System.out.println("\n|        Main Menu        |");
            System.out.println("===========================");
            System.out.println("|1. Supplier Management   |");
            System.out.println("|2. Product Management    |");
            System.out.println("|3. Order Management      |");
            System.out.println("|4. Transaction Management|");
            System.out.println("|5. Staff Management      |");
            System.out.println("|6. Display Stock         |");
            System.out.println("|7. Exit                  |");
            System.out.println("===========================");
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
                        Order.userInterface(scanner	); 
                        break;
                    case 4:
                        payment.paymentMenu(); 
                        break;
                    case 5:
                        Staff.staffMenu(scanner, staffList);
                        break;
                    case 6:
                        stock.displayStock();
                        System.out.println("Press Enter to return to the main menu...");
    					scanner.nextLine(); 
                        break;        
                    case 7:
                        System.out.println("Exiting...");
                        scanner.close();
                        return; 
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 6.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); 
            }
        }
    }
}
