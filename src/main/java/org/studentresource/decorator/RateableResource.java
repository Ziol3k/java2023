// RateableResource.java
package org.studentresource.decorator;

import org.studentresource.StudentResource;

// Adds rating functionality to a StudentResource.
public class RateableResource extends ResourceDecorator {

    private double rating;

    // Constructor.
    public RateableResource(StudentResource decoratedResource) {
        super(decoratedResource);
    }

    // Sets the rating.
    public void setRating(double rating) {
        this.rating = rating;
    }

    // Retrieves the rating.
    public double getRating() {
        return rating;
    }

    // Overrides to provide access to the decorated resource.
    @Override
    public StudentResource getDecoratedResource() {
        return super.getDecoratedResource();
    }
}
