import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("\nMain Menu:");
            System.out.println("1. Supplier Management");
            System.out.println("2. Product Management");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
        
        if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    Supplier.userInterface(scanner);
                    break;
                case 2:
                    //Product.productMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            } 
            	}else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); 
            	
            	
            }
         
        
    }
}
