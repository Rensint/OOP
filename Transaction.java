import java.io.*;
import java.util.*;

public class Transaction {
    private String transactionID;
    private Date transactionDate;
    private double amount;
    
    private static final String TRANSACTION_FILE = "transactions.txt";
    
    public Transaction(){
    }
    
    public Transaction(double amount) {
        this.transactionID = generateTransactionID();
        this.transactionDate = new Date(); 
        this.amount = amount;
    }	

    public String getTransactionID() {
        return transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }
   
  	public String getTransactionFile(){
  		return TRANSACTION_FILE;
  	}

	public String generateTransactionID() {
	    int highestID = 0; 
	
	    try (BufferedReader reader = new BufferedReader(new FileReader(TRANSACTION_FILE))) {
	        String line;
	        while ((line = reader.readLine()) != null) {
	            
	            if (line.startsWith("Transaction ID:")) {
	                
	                String[] transactionDetails = line.split(",");
	                for (String detail : transactionDetails) {
	                    if (detail.trim().startsWith("Transaction ID:")) {
	                        String id = detail.split(":")[1].trim();
	                       
	                        if (id.length() > 1 && id.startsWith("T")) {
	                            try {
	                                int num = Integer.parseInt(id.substring(1)); // Remove 'T'
	                                if (num > highestID) {
	                                    highestID = num;
	                                }
	                            } catch (NumberFormatException e) {
	                                
	                            }
	                        }
	                    }
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	
	    highestID++; 
	
	    
	    return String.format("T%03d", highestID);
	}




    public void saveTransaction(String transactionID, Date transactionDate, double amount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRANSACTION_FILE, true))) {
            writer.write("Transaction ID: " + transactionID + ", ");
            writer.write("Transaction Date: " + transactionDate + ", ");
            writer.write("Amount: RM" + String.format("%.2f", amount) + ", ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
