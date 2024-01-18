package lab01.richard.group04.a2;

import java.util.Scanner;

public class UserInterface {
    public void start() {
        System.out.println("You have now entered Edstemus! Please select from the following options:");
        System.out.println("Press M to return to the menu at whichever time!");
        System.out.println("1. View Scrolls");
        System.out.println("2. Edit/Update Scrolls");
        System.out.println("3. Add Scrolls");
        System.out.println("4. Manage User Profile");

        Scanner scanner = new Scanner(System.in);
        String choice = scanner.nextLine();

        switch (choice) {
            case "1":
                break;
            case "2":
                // Edit/Update Scrolls
                break;
            case "3":
                // Add Scrolls
                break;
            case "4":
                ProfileManagement profileManagement = new ProfileManagement();
                profileManagement.editUserInformation();
                break;
            case "M":
                // return to the menu
                break;

        }
    }
}