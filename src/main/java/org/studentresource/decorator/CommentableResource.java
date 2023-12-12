// CommentableResource.java
package org.studentresource.decorator;

import org.studentresource.StudentResource;

// Adds commenting functionality to a StudentResource.
public class CommentableResource extends ResourceDecorator {

    private String comment;

    // Constructor.
    public CommentableResource(StudentResource decoratedResource) {
        super(decoratedResource);
    }

    // Adds a comment.
    public void addComment(String comment) {
        this.comment = comment;
    }

    // Retrieves the added comment.
    public String getComment() {
        return comment;
    }

    // Overrides to provide access to the decorated resource.
    @Override
    public StudentResource getDecoratedResource() {
        return super.getDecoratedResource();
    }
}
