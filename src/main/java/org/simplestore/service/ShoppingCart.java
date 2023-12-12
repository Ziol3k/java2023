package org.simplestore.service;

import org.simplestore.model.Inventory;
import org.simplestore.model.Product;
import org.simplestore.model.ProductNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Inventory inventory;
    private final Map<Integer, Integer> cartItems = new HashMap<>();

    public ShoppingCart(Inventory inventory) {
        this.inventory = inventory;
    }

    public synchronized int getItemQuantity(int productId) {
        return cartItems.getOrDefault(productId, 0);
    }

    public synchronized void addItem(int productId, int quantity) {
        cartItems.merge(productId, quantity, Integer::sum);
    }

    public synchronized void removeItem(int productId, int quantity) {
        cartItems.computeIfPresent(productId, (key, val) -> (val - quantity) > 0 ? val - quantity : null);
    }

    public synchronized double calculateTotalCost() throws ProductNotFoundException {
        double total = 0;
        for (Map.Entry<Integer, Integer> entry : cartItems.entrySet()) {
            Product product = inventory.getProduct(entry.getKey());
            total += product.getPrice() * entry.getValue();
        }
        return total;
    }

    public synchronized void clearCart() {
        cartItems.clear();
    }
}
