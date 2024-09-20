package Assignment;

public class Member {
    private String memberID;
    private String name;
    private String email;
    private String phoneNo;

    public Member(String memberID, String name, String email, String phoneNo) {
        this.memberID = memberID;
        this.name = name;
        this.email = email;
        this.phoneNo = phoneNo;
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

    public String getPhoneNo() {
        return phoneNo;
    }

    // Update profile information
    public void updateProfile(String name, String phoneNo) {
        this.name = name;
        this.phoneNo = phoneNo;
    }

    public String toString() {
        return "Member ID: " + memberID + ", Name: " + name + ", Email: " + email + ", Phone No: " + phoneNo;
    }
}
