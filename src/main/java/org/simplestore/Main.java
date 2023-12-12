package org.simplestore;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;
import org.simplestore.util.InventoryLoader;

public class Main {
    public static void main(String[] args) {
        try {
            Inventory inventory = loadInventory("src/main//resources/inventory.txt");

            addProductToInventory(inventory, new Product(11, "Bread", 3.0));
            printInventory(inventory);

            removeProductFromInventory(inventory, 1);
            printInventory(inventory);
        } catch (ProductNotFoundException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static Inventory loadInventory(String filePath) throws ProductNotFoundException {
        Inventory inventory = new Inventory();
        InventoryLoader.loadInventory(filePath, inventory);
        return inventory;
    }

    private static void addProductToInventory(Inventory inventory, Product product) {
        inventory.addProduct(product);
        System.out.println("Added new product to the inventory: " + product);
    }

    private static void printInventory(Inventory inventory) {
        System.out.println("Products in the inventory:");
        inventory.listAllProducts().forEach(System.out::println);
        System.out.println();
    }

    private static void removeProductFromInventory(Inventory inventory, int productId) {
        inventory.removeProduct(productId);
        System.out.println("Removed product with ID " + productId + " from the inventory.");
    }
}
