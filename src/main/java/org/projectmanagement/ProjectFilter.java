package org.projectmanagement;


public interface ProjectFilter<T> {
    boolean test(Project project, T criteria);
}

//

