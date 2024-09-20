package Assignment;

public class Signup {
    private String desiredPassword;
    private String email;
    private String name;

    // Constructor
    public Signup(String email, String desiredPassword, String name) {
        this.email = email;
        this.desiredPassword = desiredPassword;
        this.name = name;
    }

    // Getters
    public String getDesiredPassword() {
        return desiredPassword;
    }

    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }

    // Validate SignUp details
    public boolean validateDetails() {
        return (desiredPassword != null && !desiredPassword.isEmpty() &&
                email != null && email.contains("@") && name != null && !name.isEmpty());
    }

    @Override
    public String toString() {
        return "Email: " + email + ", Name: " + name;
    }
}
