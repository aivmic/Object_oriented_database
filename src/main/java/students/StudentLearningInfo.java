package students;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class StudentLearningInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String code;
    private String starting_date;

    private String partitionField;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ModuleResult> moduleResults = new ArrayList<>();
    
    @ManyToMany(mappedBy = "students", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Project> projects = new ArrayList<>();

    public StudentLearningInfo() {
    }

    public StudentLearningInfo(String code, String starting_date) {
        this.code = code;
        this.starting_date = starting_date;
        this.partitionField = determinePartition(code);
    }

    private String determinePartition(String code) {
        String firstLetter = code.substring(0, 1).toUpperCase();
        return (firstLetter.compareTo("A") >= 0 && firstLetter.compareTo("M") <= 0) ? "AM" : "NZ";
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
        this.partitionField = determinePartition(code);  // Update partition when code changes
    }

    public String getStarting_date() {
        return starting_date;
    }

    public void setStarting_date(String starting_date) {
        this.starting_date = starting_date;
    }

    public String getPartitionField() {
        return partitionField;
    }

    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }
    
    public List<ModuleResult> getModuleResults() {
        return moduleResults;
    }

    public void setModuleResults(List<ModuleResult> moduleResults) {
        this.moduleResults = moduleResults;
    }

    public void addModuleResult(ModuleResult moduleResult) {
        moduleResults.add(moduleResult);
        moduleResult.setStudent(this);
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
            project.addStudent(this);
        }
    }

    @Override
    public String toString() {
        return String.format("StudentLearningInfo [code=%s, starting_date=%s, partition=%s]", code, starting_date, partitionField);
    }
}
