package students;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
public class Supervisor implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue
    private long id;

    private String name;
    private String surname;
    private String position;

    private String partitionField;

    @OneToMany(mappedBy = "supervisor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;

    public Supervisor() {
    }

    public Supervisor(String name, String surname, String position) {
        this.name = name;
        this.surname = surname;
        this.position = position;
        this.partitionField = "ALL";
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPartitionField() {
        return partitionField;
    }
    
    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public void addProject(Project project) {
        if (!projects.contains(project)) {
            projects.add(project);
            project.setSupervisor(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Supervisor [name=%s, surname=%s, position=%s, partition=%s]", name, surname, position, partitionField);
    }
}
