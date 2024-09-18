package Assignment;

public class Signup {
    private String desiredUsername;
    private String desiredPassword;
    private String name;
    private String email;
    private String phoneNo;

    public Signup(String desiredUsername, String desiredPassword, String name, String email, String phoneNo) {
        this.desiredUsername = desiredUsername;
        this.desiredPassword = desiredPassword;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    // Getters
    public String getDesiredUsername() {
        return desiredUsername;
    }

    public void setDesiredPassword(String desiredPassword) {
        this.desiredPassword = desiredPassword;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    // Setters

    public void setDesiredUsername(String desiredUsername) {
        this.desiredUsername = desiredUsername;
    }

    public String getDesiredPassword() {
        return desiredPassword;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    // Validate SignUpDetails
    public boolean validateDetails() {
        return (desiredUsername != null && !desiredUsername.isEmpty() &&
                desiredPassword != null && !desiredPassword.isEmpty() &&
                email != null && email.contains("@") && 
                phoneNo != null && phoneNo.length() >= 10);
    }

    public String toString() {
        return "Username: " + desiredUsername + ", Name: " + name + ", Email: " + email + ", PhoneNo: " + phoneNo;
    }
}
