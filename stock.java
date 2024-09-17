import java.util.ArrayList;
import java.util.List;

public class Stock {
    private Product product;
    private List<Shift> shifts;


    public Stock(Product product) {
        this.product = product;
        this.shifts = new ArrayList<>();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Shift> getShifts() {
        return shifts;
    }

    public void setShifts(List<Shift> shifts) {
        this.shifts = shifts;
    }


    public void addShift(Shift shift) {
        shifts.add(shift);
    }

 
    public void removeShift(Shift shift) {
        shifts.remove(shift);
    }


    public int increaseStock() {
        product.setProductQuantity(product.getProductQuantity() + 1);
        return product.getProductQuantity();
    }

    public int decreaseStock() {
        if (product.getProductQuantity() > 0) {
            product.setProductQuantity(product.getProductQuantity() - 1);
        }
        return product.getProductQuantity();
    }
}
