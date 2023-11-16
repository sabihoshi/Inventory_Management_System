import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class Item {

    private static LinkedList<Item> items;
    public int id;
    public String name;
    public int quantity;
    public int minimumQuantity;
    public String description;
    public String category;

    /**
     * Retrieves the items from the "items.json" file.
     *
     * @return a list of items retrieved from the file, or an empty list if the file is not found or an error occurs during reading
     */
    public static LinkedList<Item> getItems() {
        if (items != null) return items;

        try {
            File file = new File("items.json");
            return items = Main.mapper.readValue(file, new TypeReference<>() {
            });
        } catch (IOException e) {
            return items = new LinkedList<>();
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
            Main.mapper.writerWithDefaultPrettyPrinter().writeValue(file, items);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save items.");
        }
    }

    /**
     * Retrieves the next available ID for an item.
     * If there are no items, the next ID will be 1.
     *
     * @return the next available ID for an item
     */
    public static int getNextId() {
        return getItems().stream().mapToInt(item -> item.id).max().orElse(0) + 1;
    }

    /**
     * Prints the details of a single item.
     *
     * @param item the item to print
     */
    public static void printItem(Item item) {
        System.out.println();
        System.out.println("+--------------+");
        System.out.println("| Item Details |");
        System.out.println("+--------------+");
        System.out.println();

        System.out.println();
        System.out.printf("+--------------------------------------------------------------+%n");
        System.out.printf("|               ID: %-42d |%n", item.id);
        System.out.printf("|             Name: %-42s |%n", item.name);
        System.out.printf("|      Description: %-42s |%n", item.description);
        System.out.printf("|         Quantity: %-42d |%n", item.quantity);
        System.out.printf("|         Category: %-42s |%n", item.category);
        System.out.printf("| Minimum Quantity: %-42d |%n", item.minimumQuantity);
        System.out.printf("+--------------------------------------------------------------+%n");
        System.out.println();
    }

    /**
     * Sorts a collection of items based on their quantity and name.
     *
     * @param items the collection of items to be sorted
     * @return a linked list of items sorted by quantity and name
     */
    public static LinkedList<Item> sortItems(Collection<Item> items) {
        List<Item> sortedItems = new ArrayList<>(items);
        sortedItems.sort((item1, item2) -> {
            if (item1.quantity == item2.quantity) {
                return item1.name.compareTo(item2.name);
            }

            return item2.quantity - item1.quantity;
        });

        return new LinkedList<>(sortedItems);
    }

    /**
     * Prints the details of the items in a collection.
     *
     * @param items the collection of items to be printed
     */
    public static void printItems(Collection<Item> items) {
        Collection<Item> lowStocks = items.stream().filter(item -> item.quantity <= item.minimumQuantity).toList();

        if (!lowStocks.isEmpty()) {
            System.out.println("!! Low Stocks !!");
            String lowStocksFormat = "| %-5s | %-25s | %-30s | %-10d | %-10d | %-25s |%n";
            System.out.printf("+-------+---------------------------+--------------------------------+------------+------------+---------------------------+%n");
            System.out.printf("| Id    | Name                      | Description                    | Quantity   | Minimum    | Category                  |%n");
            System.out.printf("+-------+---------------------------+--------------------------------+------------+------------+---------------------------+%n");
            for (Item item : sortItems(lowStocks)) {
                String description = item.description.length() > 30 ? item.description.substring(0, 27) + "..." : item.description;
                System.out.format(lowStocksFormat, item.id, item.name, description, item.quantity, item.minimumQuantity, item.category);
            }
            System.out.printf("+-------+---------------------------+--------------------------------+------------+------------+---------------------------+%n");
            System.out.println("!! Low Stocks !!");

            System.out.println();
            System.out.println();
        }
        String leftAlignFormat = "| %-5s | %-25s | %-30s | %-10d | %-25s |%n";
        System.out.printf("+-------+---------------------------+--------------------------------+------------+---------------------------+%n");
        System.out.printf("| Id    | Name                      | Description                    | Quantity   | Category                  |%n");
        System.out.printf("+-------+---------------------------+--------------------------------+------------+---------------------------+%n");
        for (Item item : sortItems(items)) {
            String description = item.description.length() > 30 ? item.description.substring(0, 27) + "..." : item.description;
            System.out.format(leftAlignFormat, item.id, item.name, description, item.quantity, item.category);
        }
        System.out.printf("+-------+---------------------------+--------------------------------+------------+---------------------------+%n");
    }

    /**
     * Retrieves an item with the specified ID.
     *
     * @param id the ID of the item to retrieve
     * @return the item with the specified ID, or null if no item is found
     */
    public static Item getItem(int id) {
        LinkedList<Item> items = getItems();

        for (Item item : items) {
            if (item.id == id) {
                return item;
            }
        }

        return null;
    }
}

