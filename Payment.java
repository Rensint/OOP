import java.io.*;
import java.util.*;

public class Payment extends Transaction{
    private Order order;
    private String paymentMethod;
    private static final String PRODUCT_FILE = "products.txt";
    private static final String ORDER_FILE = "orderSupplier.txt";
    private static final String BALANCE_FILE = "balance.txt";

    public Payment() {
    }

    public Payment(double amount, Order order, String paymentMethod) {
    	super(amount);	
        this.order = order;
        this.paymentMethod = paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void paymentMenu() {
        Scanner scanner = new Scanner(System.in);
        String inputOrderID;
        int selection = 0;
        while (selection != 7) {
            System.out.println("-----------------------------");
            System.out.println("\tPAYMENT MENU    ");
            System.out.println("-----------------------------\n");
            System.out.println("|---------------------------|");
            System.out.println("|Select an option.          |");
            System.out.println("|---------------------------|");
            System.out.println("|[1] Make payment           |");
            System.out.println("|[2] View payment record    |");
            System.out.println("|[3] Exit                   |");
            System.out.println("|---------------------------|\n");
            System.out.print("Option: ");
            try {
                selection = scanner.nextInt();
                scanner.nextLine();
                switch (selection) {
                    case 1:
                        System.out.print("Enter order ID for payment: ");
                        inputOrderID = scanner.nextLine().toUpperCase();
                        if (isPaymentCompleted(inputOrderID)) {
                            System.out.println("Order already paid.");
                            break;
                        }
                        calculateTotalPrice(inputOrderID);
                        break;
                    case 2:
                        readTransactionRecords();
                        break;
                    case 3:
                    	System.out.println("Exiting system...");
                        return;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please try again.");
                scanner.nextLine();
            }
        }
    }

    private boolean isPaymentCompleted(String inputOrderID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] orderDetails = line.split(",");
                if (orderDetails.length == 5) {
                    String orderID = orderDetails[0].trim();
                    String status = orderDetails[4].trim();
                    if (orderID.equals(inputOrderID)) {
                        return status.equalsIgnoreCase("paid");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean calculateTotalPrice(String inputOrderID) {
        double totalPrice = 0.0;
        boolean foundOrder = false;
        Scanner scanner = new Scanner(System.in);
        try (BufferedReader fileReader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                String[] orderDetails = line.split(",");
                String orderID = orderDetails[0].trim();
                if (orderID.equals(inputOrderID)) {
                    foundOrder = true;
                    String productID = orderDetails[2].trim();
                    int orderQuantity = Integer.parseInt(orderDetails[3].trim());
                    double productPrice = getProductPrice(productID);
                    System.out.println("\n\nOrder ID	: " + inputOrderID);
                    System.out.println("Product ID	: " + productID);
                    System.out.println("Order Quantity	: " + orderQuantity);
                    System.out.println("Product Price	: RM" + String.format("%.2f", productPrice));
                    totalPrice += productPrice * orderQuantity;
                    System.out.println("Total Price	: RM" + String.format("%.2f", totalPrice));
                    System.out.println("===========================================");
                    if (makePayment(inputOrderID, totalPrice)) {
                    	updateProductQuantity(productID, orderQuantity);
                    	updateBalance(totalPrice);
                        saveTransaction(totalPrice);
                    }
                }
            }
            if (!foundOrder) {
                System.out.println("Order ID " + inputOrderID + " not found.");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public double getProductPrice(String productID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Product ID: " + productID)) {
                    reader.readLine(); // Skip to the next line
                    reader.readLine();
                    reader.readLine();
                    String priceLine = reader.readLine();
                    String priceString = priceLine.split("RM")[1].trim();
                    return Double.parseDouble(priceString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public boolean makePayment(String orderID, double totalPrice) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to make payment on " + orderID + " for a total of RM" + String.format("%.2f", totalPrice) + "? (Y/N): ");
        char choice = scanner.next().charAt(0);
        if (choice == 'Y' || choice == 'y') {
        	System.out.println("\n--------------------------");
            System.out.println("Select Payment Method: ");
            System.out.println("[1] Credit Card");
            System.out.println("[2] TnG");
            System.out.println("[3] DuitNow");
            System.out.print("Enter your choice: ");
            int paymentChoice = scanner.nextInt();
            scanner.nextLine();
            switch (paymentChoice) {
                case 1:
                    setPaymentMethod("Credit Card");
                    break;
                case 2:
                    setPaymentMethod("TnG");
                    break;
                case 3:
                    setPaymentMethod("DuitNow");
                    break;
                default:
                    System.out.println("Invalid choice. Payment cancelled.");
                    return false;
            }
            System.out.println("Payment price of RM" + String.format("%.2f", totalPrice) + " for " + orderID + " has been processed.");
            updateOrderStatus(orderID);
            return true;
        } else {
            System.out.println("Payment cancelled.\n\n");
            return false;
        }
    }

    private void updateOrderStatus(String inputOrderID) {
        List<String> fileContent = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] orderDetails = line.split(",");
                String orderID = orderDetails[0].trim();
                if (orderID.equals(inputOrderID)) {
                    orderDetails[4] = "paid";
                    fileContent.add(String.join(",", orderDetails));
                } else {
                    fileContent.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ORDER_FILE))) {
            for (String order : fileContent) {
                writer.write(order + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateProductQuantity(String productID, int quantityBought){
    	List<String> fileContent = new ArrayList<>();
    	boolean foundProduct = false;
			
    	try(BufferedReader fileReader = new BufferedReader(new FileReader(ORDER_FILE))){
    		String line;
    		while ((line = fileReader.readLine()) != null) {
    			String[] orderDetails = line.split(",");
    			String orderID = orderDetails[0].trim();
    			productID = orderDetails[2].trim();
    			quantityBought = Integer.parseInt(orderDetails[3].trim());
    		}
    	} catch(IOException e){
    		e.printStackTrace();
    	}
    		
    	try (BufferedReader reader = new BufferedReader(new FileReader(PRODUCT_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Product ID: " + productID)) {
                    foundProduct = true;
                    fileContent.add(line);
                    fileContent.add(reader.readLine());
                    fileContent.add(reader.readLine());
                    fileContent.add(reader.readLine());
					fileContent.add(reader.readLine());
                    
                    String quantityLine = reader.readLine();
                    int currentQuantity = Integer.parseInt(quantityLine.split(":")[1].trim());
                    int newQuantity = currentQuantity + quantityBought;
                    fileContent.add("Quantity: " + newQuantity);
                    reader.readLine();
                    fileContent.add("--");
                } else {
                    fileContent.add(line);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(PRODUCT_FILE))) {
                for (String lineContent : fileContent) {
                    writer.write(lineContent + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void updateBalance(double totalPrice){
    	double currentBalance = 0.0, newBalance = 0.0;
    	try (BufferedReader reader = new BufferedReader(new FileReader(BALANCE_FILE))) {
    		String line = reader.readLine();
    		if (line.startsWith("Current Balance: RM")){
    			String balanceString = line.replace("Current Balance: RM", "").trim();
            	currentBalance = Double.parseDouble(balanceString);
    		}
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    	newBalance = currentBalance - totalPrice;
    	System.out.println("================================================================");
    	System.out.println("Balance after payment: \tRM" + String.format("%.2f", newBalance) + "\n\n");
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter(BALANCE_FILE))) {
	        writer.write(String.format("Current Balance: RM%.2f", newBalance));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }

	private void readTransactionRecords() {
	    double totalAmount = 0.0;
	    try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
	        String line;
	        System.out.printf("%-20s %-40s %-20s %-20s\n", "Transaction ID", "Transaction Date", "Transaction Amount", "Payment Method");
	        System.out.println("--------------------------------------------------------------------------------------------------");
	        while ((line = reader.readLine()) != null) {
	            String[] transactionDetails = line.split(", ");
	            
	            if (transactionDetails.length >= 4) {
	                String transactionID = transactionDetails[0].substring(15).trim();
	                String transactionDate = transactionDetails[1].substring(18).trim();
	                String transactionAmountString = transactionDetails[2].substring(10).trim();
	                double transactionAmount = Double.parseDouble(transactionAmountString);
	                String paymentMethod = transactionDetails[3].substring(16).trim();
	                totalAmount += transactionAmount;
	
	                System.out.printf("%-20s %-40s RM%-20s %-20s\n", transactionID, transactionDate, String.format("%.2f", transactionAmount), paymentMethod);
	            } else {
	                System.out.println("Malformed line in transactions file: " + line);
	            }
	        }
	        System.out.println("--------------------------------------------------------------------------------------------------");
	        System.out.println("Total Transaction Amount: RM" + String.format("%.2f", totalAmount));
	        System.out.println("\n\n");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
    
    //Override
    public void saveTransaction(double totalPrice) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(getTransactionFile(), true))) {
	        writer.write("Transaction ID: " + generateTransactionID() + ", ");
	        writer.write("Transaction Date: " + getTransactionDate() + ", ");
	        writer.write("Amount: RM" + String.format("%.2f", totalPrice) + ", ");
	        writer.write("Payment Method: " + getPaymentMethod() + "\n");
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}

