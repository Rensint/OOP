import java.util.ArrayList;
import java.util.List;

public class Staff {
    private int staffID;
    private String staffName;
    private String department;
    private int phoneNumber;
    private String email;
    private List<Shift> assignedShifts;


    public Staff(int staffID, String staffName, String department, int phoneNumber, String email) {
        this.staffID = staffID;
        this.staffName = staffName;
        this.department = department;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.assignedShifts = new ArrayList<>();
    }


    public int getStaffID() {
        return staffID;
    }

    public void setStaffID(int staffID) {
        this.staffID = staffID;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void assignShift(Shift shift) {
        assignedShifts.add(shift);
    }

    public void removeShift(Shift shift) {
        assignedShifts.remove(shift);
    }


    public List<Shift> getAssignedShift() {
        return assignedShifts;
    }

    @Override
    public String toString() {
        return "Staff ID: " + staffID +
                ", Name: " + staffName +
                ", Department: " + department +
                ", Phone Number: " + phoneNumber +
                ", Email: " + email +
                ", Assigned Shifts: " + assignedShifts;
    }
}