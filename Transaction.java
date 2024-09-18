import java.io.*;
import java.util.*;

public class Transaction {
    private int transactionID;
    private Date transactionDate;
    private double amount;
    
    private static final String transactionFile = "transactions.txt";
	
	public Transaction(){
	}
	
    public Transaction(double amount) {
        this.transactionID = generateTransactionID();
        this.transactionDate = new Date();
        this.amount = amount;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }
    
   public int generateTransactionID() {
        int lastTransactionID = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(transactionFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line starts with "Transaction ID"
                if (line.startsWith("Transaction ID:")) {
                    String[] transactionDetails = line.split(":");
                    if (transactionDetails.length > 1) {
                        String transactionIDStr = transactionDetails[1].trim().split(",")[0];
                        try {
                            // Parse and update lastTransactionID
                            int transactionID = Integer.parseInt(transactionIDStr);
                            lastTransactionID = transactionID;
                        } catch (NumberFormatException e) {
                            System.out.println("Skipping invalid transaction ID: " + transactionIDStr);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Increment the last transaction ID by 1
        return lastTransactionID + 1;
    }

    public void saveTransaction() {
    	generateTransactionID();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(transactionFile, true))) {
            writer.write("Transaction ID: " + getTransactionID() + ", ");
            writer.write("Transaction Date: " + getTransactionDate() + ", ");
            writer.write("Amount: RM" + String.format("%.2f", amount) + ", ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
