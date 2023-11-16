import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final ObjectMapper mapper = new ObjectMapper();
    public static User loggedInUser;


    public static void main(String[] args) {
        while (true) {
            System.out.println();
            System.out.println();
            System.out.println("Inventory Management System");

            System.out.println("[1] Login");
            System.out.println("[2] Create account");
            System.out.println("[3] Exit");
            System.out.println();

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: {
                    loginMenu();
                    break;
                }
                case 2: {
                    createAccountMenu();
                    break;
                }
                case 3: {
                    System.out.println("Exiting...");
                    return;
                }
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("[1] View inventory");
            System.out.println("[2] View item details");
            System.out.println("[3] Edit item details");
            System.out.println("[4] Manage stock quantity");
            System.out.println();
            System.out.println("[5] Add new item");
            System.out.println("[6] Remove item");
            System.out.println();
            System.out.println("[0] Back");
            System.out.println();

            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();

                System.out.println();
                System.out.println();
                switch (choice) {
                    case 1: {
                        System.out.println("View Inventory");
                        Item.printItems(Item.getItems());
                        break;
                    }
                    case 2: {
                        viewItemMenu();
                        break;
                    }
                    case 3: {
                        editItemMenu();
                        break;
                    }
                    case 4: {
                        manageStockMenu();
                        break;
                    }
                    case 5: {
                        addItemMenu();
                        break;
                    }
                    case 6: {
                        removeItemMenu();
                        break;
                    }
                    case 0: {
                        return;
                    }
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
            System.out.println();
            System.out.println();
        }
    }

    private static void loginMenu() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        loggedInUser = User.getUser(username, password);
        if (loggedInUser == null) {
            System.out.println("Invalid username or password.");
            return;
        }

        System.out.println();
        System.out.println();
        userMenu();
    }

    private static void createAccountMenu() {
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Confirm your password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return;
        }

        User user = new User();
        user.username = username;
        user.password = password;
        user.isAdmin = false;

        List<User> users = User.getUsers();
        users.add(user);
        User.saveUsers();

        System.out.println("Account created successfully.");
    }

    private static void viewItemMenu() {
        System.out.println("View Item");
        Item.printItems(Item.getItems());

        System.out.print("Enter item ID to view details: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Item item = Item.getItem(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        Item.printItem(item);
    }

    private static void editItemMenu() {
        Item.printItems(Item.getItems());

        System.out.println();
        System.out.println();
        System.out.println("Update Item");
        System.out.println();

        System.out.print("Enter item ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Item item = Item.getItem(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        Item.printItem(item);

        System.out.println();
        System.out.println("Note: Leave blank if you do not want to update the field.");
        System.out.print("Enter new item name: ");
        String newName = scanner.nextLine();

        System.out.println();
        System.out.println("Note: Leave blank if you do not want to update the field.");
        System.out.print("Enter new item description: ");
        String newDescription = scanner.nextLine();

        System.out.println();
        System.out.println("Note: Leave blank if you do not want to update the field.");
        System.out.print("Enter new item category: ");
        String newCategory = scanner.nextLine();

        System.out.println();
        System.out.print("Enter new item minimum quantity: ");
        int newMinimumQuantity = scanner.nextInt();
        scanner.nextLine();

        item.name = newName.isBlank() ? item.name : newName;
        item.description = newDescription.isBlank() ? item.description : newDescription;
        item.category = newCategory.isBlank() ? item.category : newCategory;
        item.minimumQuantity = newMinimumQuantity;

        Item.saveItems();
        Item.printItem(item);

        System.out.println();
        System.out.println("Item updated successfully.");
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    private static void manageStockMenu() {
        Item.printItems(Item.getItems());

        System.out.println();
        System.out.println();
        System.out.println("Manage Stock Quantity");
        System.out.println();

        System.out.print("Enter item ID to manage stock quantity: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Item item = Item.getItem(id);
        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        Item.printItem(item);

        System.out.print("Enter new stock quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine();

        item.quantity = newQuantity;

        Item.saveItems();
        Item.printItem(item);

        System.out.println();
        System.out.println("Stock quantity updated successfully.");
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    private static void addItemMenu() {
        System.out.println("Add Item");

        System.out.print("Enter item name: ");
        String name = scanner.nextLine();

        System.out.print("Enter item description: ");
        String description = scanner.nextLine();

        System.out.print("Enter item quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter item minimum quantity: ");
        int minimumQuantity = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter item category: ");
        String category = scanner.nextLine();

        Item item = new Item();

        item.id = Item.getNextId();
        item.name = name;
        item.description = description;
        item.quantity = quantity;
        item.minimumQuantity = minimumQuantity;
        item.category = category;

        List<Item> items = Item.getItems();
        items.add(item);
        Item.saveItems();

        Item.printItem(item);

        System.out.println();
        System.out.println("Item added successfully.");
        System.out.println("Press enter to continue.");
        scanner.nextLine();
    }

    private static void removeItemMenu() {
        System.out.println("Remove Item");

        System.out.print("Enter item ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Item item = Item.getItem(id);

        if (item == null) {
            System.out.println("Item not found.");
            return;
        }

        Item.printItem(item);

        System.out.print("Are you sure you want to delete this item? (y/n): ");
        String confirm = scanner.nextLine();

        if (!confirm.equalsIgnoreCase("y")) {
            System.out.println("Item not deleted.");
            return;
        }

        List<Item> items = Item.getItems();
        items.remove(item);
        Item.saveItems();
    }
}
