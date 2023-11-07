import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Item {

    private static List<Item> items;
    public int id;
    public String name;
    public int quantity;
    public String description;
    public String category;
    public boolean isCheckedOut;
    public String checkedOutBy;

    /**
     * Retrieves the items from the "items.json" file.
     *
     * @return a list of items retrieved from the file, or an empty list if the file is not found or an error occurs during reading
     */
    public static List<Item> getItems() {
        if (items != null) return items;

        try {
            File file = new File("items.json");
            return items = Main.mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            return items = new ArrayList<>();
        }
    }

    /**
     * Saves the items to a JSON file named "items.json".
     *
     * @throws RuntimeException if there are no items to save or an error occurs during saving
     */
    public static void saveItems() {
        if (items == null) throw new RuntimeException("Tried saving, but there are no items to save.");

        try {
            File file = new File("items.json");
            Main.mapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(file, items);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save items.");
        }
    }

    public static int getNextId() {
        return getItems().stream().mapToInt(item -> item.id).max().orElse(0) + 1;
    }

    public static void printItems(Collection<Item> items, String name) {
        List<Item> foundItems = new ArrayList<>();

        for (Item item : items) {
            if (item.name.toLowerCase().contains(name.toLowerCase())) {
                foundItems.add(item);
            }
        }

        printItems(foundItems);
    }

    public static void printItem(Item item) {
        printItems(List.of(item));
    }

    public static void printItems(Collection<Item> items) {
        String leftAlignFormat = "| %-5s | %-15s | %-20s | %-10d | %-15s | %-20s |%n";

        System.out.format("+-------+-----------------+----------------------+------------+-----------------+----------------------+%n");
        System.out.format("| Id    | Name            | Description          | Quantity   | Category        | Checked Out By       |%n");
        System.out.format("+-------+-----------------+----------------------+------------+-----------------+----------------------+%n");

        for (Item item : items) {
            // If the description is longer than 20 characters, truncate it and add "..." to the end.
            String description = item.description.length() > 20 ? item.description.substring(0, 17) + "..." : item.description;
            String name = item.checkedOutBy == null ? "N/A" : item.checkedOutBy;
            System.out.format(leftAlignFormat, item.id, item.name, description, item.quantity, item.category, name);
        }

        System.out.format("+-------+-----------------+----------------------+------------+-----------------+----------------------+%n");
    }

    /**
     * Retrieves an item with the specified ID.
     *
     * @param id the ID of the item to retrieve
     * @return the item with the specified ID, or null if no item is found
     */
    public static Item getItem(int id) {
        Collection<Item> items = getItems();

        for (Item item : items) {
            if (item.id == id) {
                return item;
            }
        }

        return null;
    }

    /**
     * Retrieves an item with the specified name.
     *
     * @param name the name of the item to retrieve
     * @return the item with the specified name, or null if no item is found
     */
    public static Item getItem(String name) {
        List<Item> items = getItems();
        List<Item> itemsWithName = new ArrayList<>();

        for (Item item : items) {
            if (item.name.toLowerCase().contains(name.toLowerCase())) {
                itemsWithName.add(item);
            }
        }

        if (items.isEmpty()) return null;
        if (items.size() == 1) return items.get(0);

        printItems(itemsWithName);
        System.out.println();
        while (true) {
            System.out.print("Multiple items with same name found. Enter ID: ");
            String id = Main.scanner.nextLine();

            Item item = getItem(Integer.parseInt(id));
            if (item != null) return item;

            System.out.println("Invalid ID. Press enter to try again.");
            Main.scanner.nextLine();
            System.out.println();
        }
    }
}

