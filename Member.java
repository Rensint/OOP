package Assignment;

public class Member {
    private String memberID;
    private String name;
    private String email;
    private String passwordHash;
    private boolean activation;  // Account active or not

    public Member(String memberID, String name, String email, String passwordHash) {
        this.memberID = memberID;
        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.activation = false; // Default inactive
    }

    // Getters
    public String getMemberID() {
        return memberID;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activation;
    }

    // Setters
    public void setMemberID(String memberID) {
        this.memberID = memberID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Activate the account
    public void activateAccount() {
        this.activation = true;
        System.out.println(memberID + ", your account has been activated.");
    }

    // Update profile
    public void updateProfile(String name, String email) {
        // No activated
        if (!activation) {
            System.out.println("Error: Account is not activated. Please activate your account to update the profile.");
            return;
        }
        // Validate 
        if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
            System.out.println("Error: Name or Email cannot be empty.");
            return;
        }

        this.name = name;
        this.email = email;
        System.out.println("Profile updated successfully.");
    }

    // Delete account
    public void deleteAcc() {
        //Not activated
        if (!activation) {
            System.out.println("Error: Account is not activated. Please activate your account to delete. ");
            return;
        }
    // Activated
        System.out.println("Account with ID, " + memberID + " has been deleted.");
        // Here you could add logic to remove the account from a database or list
    }

    public String toString() {
        return "MemberID: " + memberID + ", Name: " + name + ", Email: " + email + ", Activation: " + (activation ? "Activated" : "Not Activated");
    }
}
