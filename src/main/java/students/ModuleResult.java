package students;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class ModuleResult implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue
    private long id;

    private String name;
    private String semester;
    private String grade;

    private String partitionField;

    @ManyToOne
    private StudentLearningInfo student;

    public ModuleResult() {
    }

    public ModuleResult(String name, String semester, String grade, StudentLearningInfo student) {
        this.name = name;
        this.semester = semester;
        this.grade = grade;
        this.student = student;
        this.partitionField = determinePartition(student.getCode());
    }

    private String determinePartition(String code) {
        char firstLetter = code.charAt(0);
        if (firstLetter >= 'A' && firstLetter <= 'M') {
            return "AM";
        } else {
            return "NZ";
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.partitionField = determinePartition(name);
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public StudentLearningInfo getStudent() {
        return student;
    }

    public void setStudent(StudentLearningInfo student) {
        this.student = student;
    }

    public String getPartitionField() {
        return partitionField;
    }

    @Override
    public String toString() {
        return String.format("ModuleResult [name=%s, semester=%s, grade=%s, partition=%s]", name, semester, grade, partitionField);
    }
}
