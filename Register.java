package Assignment;

public class Register {
        private Signup signUpDetails;
    
        // Constructor that links to SignUp details
        public Register(Signup signUpDetails) {
            this.signUpDetails = signUpDetails;
        }
    
        // Method to create a new Member using SignUp details
        public Member createMember() {
            if(signUpDetails.validateDetails()) {
                String memberID = generateMemberID();  // You can implement a method to generate unique member IDs
                Member newMember = new Member(memberID, signUpDetails.getName(), signUpDetails.getEmail());
                System.out.println("Member created successfully!");
                return newMember;
            } else {
                System.out.println("Sign-up details are not valid.");
                return null;
            }
        }
    
        // Helper method to generate a unique memberID
        private String generateMemberID() {
            return "M" + System.currentTimeMillis();  // Example unique ID generation
        }
    }
    