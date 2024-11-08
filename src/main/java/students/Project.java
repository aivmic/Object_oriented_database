package students;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id 
    @GeneratedValue
    private long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Temporal(TemporalType.DATE)
    private Date end_date;

    private int credits;

    private String partitionField;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<StudentLearningInfo> students;

    @ManyToOne
    private Supervisor supervisor;

    public Project() {
    }

    public Project(String name, Date start_date, Date end_date, int credits, List<StudentLearningInfo> students, Supervisor supervisor) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.credits = credits;
        this.students = students;
        this.supervisor = supervisor;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getPartitionField() {
        return partitionField;
    }
    
    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }

    public List<StudentLearningInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentLearningInfo> students) {
        this.students = students;
    }

    public void addStudent(StudentLearningInfo student) {
        if (!students.contains(student)) {
            students.add(student);
            student.addProject(this);
        }
    }

    public Supervisor getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    @Override
    public String toString() {
        return String.format("Project [name=%s, start_date=%s, end_date=%s, credits=%d, partition=%s]", name, start_date, end_date, credits, partitionField);
    }
}
