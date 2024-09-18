import java.util.*;
import java.io.*;

public class Payment extends Transaction{
	private Order order;
	private String paymentMethod;
	
	private static final String productFile = "products.txt";
    private static final String orderFile = "orderSupplier.txt";
    
    public Payment(){
    }
    
    public Payment(int transactionID, Date transactionDate, double amount, String orderID, String supplierID, String productID, int quantity, String status, String paymentMethod){
    	super(amount);
 		this.order = new Order(orderID, supplierID, productID, quantity, status);
 		this.paymentMethod = paymentMethod;
    }
    
    
    public void setPaymentMethod(String paymentMethod){
    	this.paymentMethod = paymentMethod;
    }
    
    public String getPaymentMethod(){
    	return paymentMethod;
    }
    
    public Order getOrder(){
    	return order;
    }
        
    public void setOrder(Order order){
    	this.order = order;
    }
    
    public void paymentMenu(){
    	Scanner scanner = new Scanner(System.in);
    	String inputOrderID;
    	int selection = 0;
    	while(selection != 7){
    		System.out.println("-----------------------------");
    		System.out.println("\tPAYMENT MENU	    ");
    		System.out.println("-----------------------------\n");
    		System.out.println("|---------------------------|");
	    	System.out.println("|Select an option.	    |");
	    	System.out.println("|---------------------------|");
	    	System.out.println("|[1] Make payment	    |");
	    	System.out.println("|[7] Exit		    |");
	    	System.out.println("|---------------------------|\n");
	    	System.out.print("Option: ");
	    	try{
	    		selection = scanner.nextInt();
	    		scanner.nextLine();
	    		switch(selection){
	    			case 1:
	    				System.out.print("Enter order ID for payment: ");
	    				inputOrderID = scanner.nextLine().toUpperCase();
	    				if (isPaymentCompleted(inputOrderID)){
	    					System.out.println("Order already paid.");
	    					break;
	    				}
	    				double totalPrice = calculateTotalPrice(inputOrderID);
	    				break;
	    			case 7:
	    				System.out.println("Exiting system...");
	    				return;
	    		}
	    	} catch (InputMismatchException e){
	    		e.printStackTrace();
	    		scanner.nextLine();
	    	}	
	    }
    }
    
    private boolean isPaymentCompleted(String inputOrderID) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("orderSupplier.txt"))) {
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
    
    public double calculateTotalPrice(String inputOrderID){
    	double totalPrice = 0.0;
    	boolean foundOrder = false;
    	Product product = new Product();
    	Scanner scanner = new Scanner(System.in);
    	
    	
    	try(BufferedReader fileReader = new BufferedReader(new FileReader(orderFile))){
    		String line;
    		while ((line = fileReader.readLine()) != null) {
    			String[] orderDetails = line.split(",");
    			String orderID = orderDetails[0].trim();
    			
    			if (orderID.equals(inputOrderID)){
    				foundOrder = true;
    				String supplierID = orderDetails[1].trim();
	    			String productID = orderDetails[2].trim();
	    			int orderQuantity = Integer.parseInt(orderDetails[3].trim());
	    			double productPrice = getProductPrice(productID);
	    			
	    			System.out.println("Order ID: " + inputOrderID);
	    			System.out.println("Supplier ID: " + supplierID);
	    			System.out.println("Product ID: " + productID);
	    			System.out.println("Order Quantity: " + orderQuantity);
	    			System.out.println("Product Price: RM" + String.format("%.2f", productPrice));
	    			
					totalPrice += productPrice * orderQuantity;
					System.out.println("Total Price: RM" + String.format("%.2f", totalPrice));
					if(makePayment(inputOrderID, totalPrice)){
	    				saveTransaction(totalPrice);
    				}
					updateProductQuantity(productID, orderQuantity);
    			}
    		}
    		if (!foundOrder){
    			System.out.println("Order ID " + inputOrderID + " not found.");
    		}	    			
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    	return totalPrice;
    }
    
    public double getProductPrice(String productID) {
	    try (BufferedReader reader = new BufferedReader(new FileReader("products.txt"))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            if (line.contains("Product ID: " + productID)) {
	                reader.readLine();
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
	
	public boolean makePayment(String orderID, double totalPrice){
		Scanner scanner = new Scanner(System.in);
		int payChoice = 0;
		double paymentAmount = 0.0, change = 0.0;
		String selectedPaymentMethod = "";
		
		System.out.print("Do you want to make payment on " + orderID + " for a total of RM" + String.format("%.2f",totalPrice) + "?(Y/N):");
		char choice = scanner.next().charAt(0);
                    
        if (choice == 'Y' || choice == 'y'){
	      	System.out.println("Select Payment Method: ");
	        System.out.println("[1] Credit Card");
	        System.out.println("[2] TnG");
	        System.out.println("[3] DuitNow");
	        System.out.print("Enter your choice: ");
	        
	        int paymentChoice = scanner.nextInt();
	        scanner.nextLine(); 
	
	        switch (paymentChoice) {
	            case 1:
	                selectedPaymentMethod = "Credit Card";
	                break;
	            case 2:
	                selectedPaymentMethod = "TnG";
	                break;
	            case 3:
	                selectedPaymentMethod = "DuitNow";
	                break;
	            default:
	                System.out.println("Invalid choice. Payment cancelled.");
	                return false;
	        }
	        setPaymentMethod(selectedPaymentMethod);
	        System.out.println("Payment RM" + String.format("%.2f", totalPrice) + " for " + orderID + " has been processed.");
	        updateOrderStatus(orderID);
	        saveTransaction(totalPrice);
        	return true;
        } else {
        	System.out.println("Payment cancelled.\n\n");
        	return false;
        }
	}
	
	private void updateOrderStatus(String inputOrderID) {
		List<String> fileContent = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		
        try (BufferedReader reader = new BufferedReader(new FileReader(orderFile))) {
        	String line;
        	while ((line = reader.readLine()) != null) {
            	String[] orderDetails = line.split(",");
            	String orderID = orderDetails[0].trim();
            	
            	if (orderID.equals(inputOrderID)){
            		orderDetails[4] = "paid";
            		fileContent.add(String.join(",", orderDetails));
            	} else{
            		fileContent.add(line);
            	}
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(orderFile))) {
	        for (String order : fileContent) {
	            writer.write(order + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
    }
    
    public void updateProductQuantity(String productID, int quantityBought){
    	List<String> fileContent = new ArrayList<>();
    	boolean foundProduct = false;
    	
    	try(BufferedReader fileReader = new BufferedReader(new FileReader(orderFile))){
    		String line;
    		while ((line = fileReader.readLine()) != null) {
    			String[] orderDetails = line.split(",");
    			String orderID = orderDetails[0].trim();
    		}
    	} catch(IOException e){
    		e.printStackTrace();
    	}
    		
    	try (BufferedReader reader = new BufferedReader(new FileReader(productFile))) {
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
                } else {
                    fileContent.add(line);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(productFile))) {
                for (String lineContent : fileContent) {
                    writer.write(lineContent + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	//@Override
	public void saveTransaction(double totalPrice) {
		Transaction transaction = new Transaction(totalPrice);
        super.saveTransaction();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            writer.write("Payment Method: " + paymentMethod + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

/*1. User input orderid to calculate price
 *2. if found, display order details and total price
 *3. ask if user want to do payment
 *4. if yes, make virtual transaction and update status
 *5. add product quantity*/