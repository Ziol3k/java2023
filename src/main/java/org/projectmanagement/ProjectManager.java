package org.projectmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ProjectManager {
    private final List<Project> projects;

    public ProjectManager() {
        this.projects = new ArrayList<>();
    }



    public Project findProjectByName(String name) throws ProjectNotFoundException {
        for (Project project : projects) {
            if (project.getName().equals(name)) {
                return project;
            }
        }
        throw new ProjectNotFoundException("Project with name " + name + " not found");
    }

    public void updateProjectDeadline(String projectName, Date newDeadline) throws ProjectNotFoundException {
        Project project = findProjectByName(projectName);
        project.setDeadline(newDeadline);

        long daysUntilDeadline = TimeUnit.DAYS.convert(newDeadline.getTime() - new Date().getTime(), TimeUnit.MILLISECONDS);

        if (daysUntilDeadline < 0) {
            System.out.println("Warning: The new deadline is in the past.");
        } else {
            for (Task task : project.getTasks()) {
                if (task.getStatus().equals("Open")) {
                    task.setStatus("Updated due to deadline change");

                    if (daysUntilDeadline < 7) {
                        task.setPriority(task.getPriority() + 1);
                    }
                }
            }
        }
    }





    public void addProject(Project project) throws ProjectDuplicatedNameException {
        for (Project existingProject : projects) {
            if (existingProject.getName().equals(project.getName())) {
                throw new ProjectDuplicatedNameException("Project " + project.getName() + " already exists");
            }
        }
        projects.add(project);
    }

    public <T> List<Project> filterProjects(ProjectFilter<T> filter, T criteria) {
        List<Project> filteredProjects = new ArrayList<>();
        for (Project project : projects) {
            if (filter.test(project, criteria)) {
                filteredProjects.add(project);
            }
        }
        return filteredProjects;
    }


}
//