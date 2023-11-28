// StudentResourceManager.java
package org.studentresource;

import java.util.ArrayList;
import java.util.List;

// Manages a collection of student resources.
public class StudentResourceManager<T extends StudentResource> {

    private final List<T> resources;

    // Constructor.
    public StudentResourceManager() {
        this.resources = new ArrayList<>();
    }

    // Adds a resource to the collection.
    public void addResource(T resource) {
        resources.add(resource);
    }

    // Removes a resource from the collection.
    public void removeResource(T resource) {
        resources.remove(resource);
    }

    // Retrieves a resource by its ID.
    public T getResource(String resourceId) {
        for (T resource : resources) {
            if (resource.getId().equals(resourceId)) {
                return resource;
            }
        }
        return null;
    }
}
