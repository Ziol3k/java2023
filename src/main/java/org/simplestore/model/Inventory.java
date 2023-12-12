package org.simplestore.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Inventory {
    private final Map<Integer, Product> products = new HashMap<>();

    public synchronized void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public synchronized void removeProduct(int id) {
        products.remove(id);
    }

    public synchronized List<Product> listAllProducts() {
        return new ArrayList<>(products.values());
    }

    public Product getProduct(int id) throws ProductNotFoundException {
        Product product = products.get(id);
        if (product == null) {
            throw new ProductNotFoundException("Product with ID " + id + " not found.");
        }
        return product;
    }
}
