package Assignment;

import java.util.ArrayList;
import java.util.Scanner;

public class Register {
    private Signup signUpDetails;

    // List to store all registered members
    private static ArrayList<Member> registeredMembers = new ArrayList<>();

    public Register(Signup signUpDetails) {
        this.signUpDetails = signUpDetails;
    }

    // Create new Member using SignUpDetails
    public Member createMember(String phoneNo) {
        String memberID = generateMemberID();
        Member newMember = new Member(memberID, signUpDetails.getName(), signUpDetails.getEmail(), phoneNo);
        registeredMembers.add(newMember);
        return newMember;
    }

    // Generate unique memberID
    private String generateMemberID() {
        return "M" + System.currentTimeMillis();  // Example unique ID generation
    }

    // View all registered members
    public static void viewMembers() {
        if (registeredMembers.isEmpty()) {
            System.out.println("No members registered.");
        } else {
            for (Member member : registeredMembers) {
                System.out.println(member.toString());
            }
        }
    }

    // Update a member's details
    public static void updateMember(Scanner scanner) {
        System.out.print("Enter Member ID to update: ");
        String memberID = scanner.nextLine();
        for (Member member : registeredMembers) {
            if (member.getMemberID().equals(memberID)) {
                System.out.print("Enter new Name: ");
                String name = scanner.nextLine();
                System.out.print("Enter new Phone No: ");
                String phoneNo = scanner.nextLine();
                member.updateProfile(name, phoneNo);
                System.out.println("Member details updated successfully!");
                return;
            }
        }
        System.out.println("Member not found.");
    }

    // Delete a member
    public static void deleteMember(Scanner scanner) {
        System.out.print("Enter Member ID to delete: ");
        String memberID = scanner.nextLine();
        for (Member member : registeredMembers) {
            if (member.getMemberID().equals(memberID)) {
                registeredMembers.remove(member);
                System.out.println("Member deleted successfully.");
                return;
            }
        }
        System.out.println("Member not found.");
    }
}
