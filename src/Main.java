import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Scanner;


public class Main {
    public static final Scanner scanner = new Scanner(System.in);
    public static final ObjectMapper mapper = new ObjectMapper();
    public static User loggedInUser;

    public static void main(String[] args) {
        while (true) {
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
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    loggedInUser = User.getUser(username, password);
                    if (loggedInUser == null) {
                        System.out.println("Invalid username or password.");
                    } else if (loggedInUser.isAdmin) {
                        adminMenu();
                    } else {
                        userMenu();
                    }
                    break;
                }
                case 2: {
                    System.out.print("Enter your username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter your password: ");
                    String password = scanner.nextLine();

                    System.out.print("Confirm your password: ");
                    String confirmPassword = scanner.nextLine();

                    if (!password.equals(confirmPassword)) {
                        System.out.println("Passwords do not match.");
                        break;
                    }

                    User user = new User();
                    user.username = username;
                    user.password = password;
                    user.isAdmin = false;

                    List<User> users = User.getUsers();
                    users.add(user);
                    User.saveUsers();

                    System.out.println("Account created successfully.");
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
            System.out.println("1. View Items");
            System.out.println("2. Checkout Item");
            System.out.println("3. Return Item");
            System.out.println("4. Back");
            System.out.println();

            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1:
                        List<Item> items = Item.getItems();
                        Item.printItems(items);
                        break;
                    case 2:
                        System.out.println("Checkout Item");

                        System.out.print("Enter name: ");
                        String name = scanner.nextLine();

                        Item item = Item.getItem(name);
                        if (item == null) {
                            System.out.println("Item not found.");
                            break;
                        }

                        if (item.isCheckedOut) {
                            System.out.println("Item is already checked out.");
                            break;
                        }

                        item.isCheckedOut = true;

                        break;
                    case 3:
                        System.out.println("Return Item");
                        break;
                    case 4:
                        System.out.println("Exit");
                        return;
                    default:
                        System.out.println("Invalid Input");
                        break;
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("1. Add Item");
            System.out.println("2. Remove Item");
            System.out.println("3. Update Item");
            System.out.println("4. View Items");
            System.out.println("5. Back");
            System.out.println();

            System.out.print("Enter your choice: ");
            try {
                int choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1: {
                        System.out.println("Add Item");

                        System.out.print("Enter item name: ");
                        String name = scanner.nextLine();

                        System.out.print("Enter item description: ");
                        String description = scanner.nextLine();

                        System.out.print("Enter item quantity: ");
                        int quantity = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter item category: ");
                        String category = scanner.nextLine();

                        Item item = new Item();

                        item.id = Item.getNextId();
                        item.name = name;
                        item.description = description;
                        item.quantity = quantity;
                        item.category = category;

                        List<Item> items = Item.getItems();
                        items.add(item);
                        Item.saveItems();

                        break;
                    }
                    case 2: {
                        System.out.println("Remove Item");

                        System.out.print("Enter item name to remove: ");
                        String name = scanner.nextLine();

                        Item item = Item.getItem(name);

                        if (item == null) {
                            System.out.println("Item not found.");
                            break;
                        }

                        Item.printItem(item);

                        System.out.print("Are you sure you want to delete this item? (y/n): ");
                        String confirm = scanner.nextLine();

                        if (!confirm.equalsIgnoreCase("y")) {
                            System.out.println("Item not deleted.");
                            break;
                        }

                        List<Item> items = Item.getItems();
                        items.remove(item);
                        Item.saveItems();

                        break;
                    }
                    case 3: {
                        System.out.println("Update Item");

                        System.out.print("Enter item name to update: ");
                        String name = scanner.nextLine();

                        Item item = Item.getItem(name);
                        if (item == null) {
                            System.out.println("Item not found.");
                            break;
                        }

                        Item.printItem(item);

                        System.out.print("Enter new item name: ");
                        String newName = scanner.nextLine();

                        System.out.print("Enter new item description: ");
                        String newDescription = scanner.nextLine();
                        System.out.print("Enter new item quantity: ");
                        int newQuantity = scanner.nextInt();

                        System.out.print("Enter new item category: ");
                        String newCategory = scanner.nextLine();

                        item.name = newName;
                        item.description = newDescription;
                        item.quantity = newQuantity;
                        item.category = newCategory;


                        break;
                    }
                    case 4: {
                        System.out.println("View Item");
                        Item.printItems(Item.getItems());

                        System.out.print("Enter item ID to view details: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        Item item = Item.getItem(id);
                        Item.printItem(item);
                        break;
                    }
                    case 5:
                        return;
                    default: {
                        System.out.println("Invalid Input");
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Invalid Input");
            }
        }
    }
}