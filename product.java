class Product {
    private String productID;
    private String productName;
    private String description;
    private double productPrice;
    private int productQuantity;
    private String category;


    public Product() {
        this.productID = "";
        this.productName = "";
        this.description = "";
        this.productPrice = 0.0;
        this.productQuantity = 0;
        this.category = "";
    }

 
    public Product(String productID, String productName, String description, double productPrice, int productQuantity, String category) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.category = category;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

 
    public void addProduct(String productID, String productName, int productQuantity, double productPrice) {
        this.productID = productID;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.productPrice = productPrice;
    }

    public String displayProduct(String productID) {
        if (this.productID.equals(productID)) {
            return toString();
        } else {
            return "Product not found.";
        }
    }

    public void updateProductQuantity(String productID, int productQuantity) {
        if (this.productID.equals(productID)) {
            this.productQuantity = productQuantity;
        } else {
            System.out.println("Product not found.");
        }
    }


    public void updateProductPrice(String productID, double productPrice) {
        if (this.productID.equals(productID)) {
            this.productPrice = productPrice;
        } else {
            System.out.println("Product not found.");
        }
    }
    
    public void deleteProduct(String productID) {
        if (this.productID.equals(productID)) {
            this.productID = null;
            this.productName = null;
            this.description = null;
            this.productPrice = 0.0;
            this.productQuantity = 0;
            this.category = null;
        } else {
            System.out.println("Product not found.");
        }
    }

    public void generateReportProduct() {
        System.out.println("Product Report:");
        System.out.println(toString());
    }

  
    public double calculateTotalProductPrice(String productID, int productQuantity, double productPrice) {
        if (this.productID.equals(productID)) {
            return productQuantity * productPrice;
        } else {
            System.out.println("Product not found.");
            return 0.0;
        }
    }

    public boolean availableProduct() {
        return this.productQuantity > 0;
    }


    @Override
    public String toString() {
        return "ProductID: " + productID +
                ", Name: " + productName +
                ", Description: " + description +
                ", Price: " + productPrice +
                ", Quantity: " + productQuantity +
                ", Category: " + category;
    }
}
