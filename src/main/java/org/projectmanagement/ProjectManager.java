package org.projectmanagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProjectManager {
    private final List<Project> projects;

    public ProjectManager() {
        this.projects = new ArrayList<>();
    }

    public Project findProjectByName(String name) throws ProjectNotFoundException {
        // TODO Znajdź projekt po nazwie. W razie braku takiego projektu,
        // rzuć błedem ProjectNotFoundException (jest już zdefiniowany w kodzie)
        throw new ProjectNotFoundException("Project with name " + name + " not found");
    }

    // TODO Funkcja updateProjectDeadline służy do aktualizacji terminu zakończenia określonego projektu.
    //  Po znalezieniu projektu o podanej nazwie, funkcja ustawia nowy termin zakończenia.
    //  Następnie, dla każdego zadania w projekcie, które ma status "Open",
    //  funkcja aktualizuje status zadania na "Updated due to deadline change".
    //  Dodatkowo, jeśli nowy termin zakończenia projektu jest bliższy niż 7 dni,
    //  funkcja zwiększa priorytet zadania o 1.
    public void updateProjectDeadline(String projectName, Date newDeadline) throws ProjectNotFoundException {
        Project project = findProjectByName(projectName);
        project.setDeadline(newDeadline);

        // TODO Aktualizacja zadań w zależności od nowego terminu

        // TODO Zwiększanie priorytetu zadania, gdy zbliża się nowy termin
        // Mniej niż 7 dni do deadline
        // Zwiększ priorytet + 1
            }
        }

    public void addProject(Project project) throws ProjectDuplicatedNameException {
        // TODO Dokończ dodawanie projektu do kolekcji projects.
        // W przypadku gdy istnieje już taki projekt,
        // rzuć błędem ProjectDuplicatedNameException (trzeba dokonczyć implementację).
        projects.add(project);
    }

    // TODO Zdefiniuj funkcję, która zwraca listę projektów, które spełniają pewne kryterium.
    // Przy braku projektów spełniających kryterium, zwróć pustą kolekcję.
    // Zobacz w testach, jak używana jest ta funkcja.
    // UWAGA: będzie potrzebne użycie typu generycznego, co nie jest oddane w tej sygnaturze:
    // List<Project> filterProjects(ProjectFilter filter, criteria)
}
//