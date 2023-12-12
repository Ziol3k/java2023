// StudyMaterial.java
package org.studentresource;

// Represents a study material as a StudentResource.
public class StudyMaterial implements StudentResource {

    private final String id;
    private final String name;

    // Constructor.
    public StudyMaterial(String id, String name) {
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
