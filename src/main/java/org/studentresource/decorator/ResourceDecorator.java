// ResourceDecorator.java
package org.studentresource.decorator;

import org.studentresource.StudentResource;

// Base class for resource decorators.
public class ResourceDecorator implements StudentResource {

    // Decorated resource.
    protected StudentResource decoratedResource;

    // Constructor.
    public ResourceDecorator(StudentResource decoratedResource) {
        this.decoratedResource = decoratedResource;
    }

    // Overrides to provide access to the decorated resource.
    @Override
    public String getId() {
        return decoratedResource.getId();
    }

    // Overrides to provide access to the decorated resource.
    @Override
    public String getName() {
        return decoratedResource.getName();
    }

    // Gets the decorated resource.
    public StudentResource getDecoratedResource() {
        return decoratedResource;
    }
}
