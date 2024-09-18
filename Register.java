package Assignment;

public class Register {
    private Signup signUpDetails;

    public Register(Signup signUpDetails) {
        this.signUpDetails = signUpDetails;
    }

    // Create new Member using SignUpDetails
    public Member createMember() {
        if (signUpDetails.validateDetails()) {
            String memberID = generateMemberID(); 
            Member newMember = new Member(memberID, signUpDetails.getName(), signUpDetails.getEmail());
            System.out.println("Member created successfully!");
            return newMember;
        } else {
            System.out.println("Sign-up details are not valid.");
            return null;
        }
    }

    // Generate unique memberID
    private String generateMemberID() {
        return "M" + System.currentTimeMillis();  // Example unique ID generation
    }
}
