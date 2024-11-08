package students;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
@Table(name = "Course")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Temporal(TemporalType.DATE)
    private Date end_date;

    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "course_id"),
        inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private List<StudentGeneralInfo> students = new ArrayList<>();;

    private String partitionField;

    public Course() {
    }

    public Course(String name, Date start_date, Date end_date, List<StudentGeneralInfo> students) {
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.students = students;
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

    public List<StudentGeneralInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentGeneralInfo> students) {
        this.students = students;
    }

    public void addStudent(StudentGeneralInfo student) {
        if (!students.contains(student)) {
            students.add(student);
            if (!student.getCourses().contains(this)) {
                student.getCourses().add(this);
            }
        }
    }

    public String getPartitionField() {
        return partitionField;
    }

    @Override
    public String toString() {
        return String.format("Course [name=%s, start_date=%s, end_date=%s, partition=%s]", name, start_date, end_date, partitionField);
    }
}
