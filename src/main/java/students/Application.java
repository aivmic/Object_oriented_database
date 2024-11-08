package students;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private String reason;

    private String partitionField;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentGeneralInfo student;

    public Application() {
    }

    public Application(Date date, String reason, StudentGeneralInfo student) {
        this.date = date;
        this.reason = reason;
        this.student = student;
        this.partitionField = determinePartition(student.getCode());
    }

    private String determinePartition(String code) {
        String firstLetter = code.substring(0, 1).toUpperCase();
        return (firstLetter.compareTo("A") >= 0 && firstLetter.compareTo("M") <= 0) ? "AM" : "NZ";
    }

    public long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPartitionField() {
        return partitionField;
    }

    public StudentGeneralInfo getStudent() {
        return student;
    }

    public void setStudent(StudentGeneralInfo student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return String.format("Application [date=%s, reason=%s, student=%s, partition=%s]", 
                             date, reason, student.getName() + " " + student.getSurname(), partitionField);
    }
}
