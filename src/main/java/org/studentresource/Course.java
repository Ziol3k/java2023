// Course.java
package org.studentresource;

// Represents a course as a StudentResource.
public class Course implements StudentResource {

    private final String id;
    private final String name;

    // Constructor.
    public Course(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Implements getId method from StudentResource interface.
    @Override
    public String getId() {
        return id;
    }

    // Implements getName method from StudentResource interface.
    @Override
    public String getName() {
        return name;
    }
}
