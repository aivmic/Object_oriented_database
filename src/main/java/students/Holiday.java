package students;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "Holiday")
public class Holiday implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Temporal(TemporalType.DATE)
    private Date start_date;

    @Temporal(TemporalType.DATE)
    private Date end_date;

    private String reason;

    private String partitionField;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentGeneralInfo student;

    public Holiday() {
    }

    public Holiday(Date start_date, Date end_date, String reason, StudentGeneralInfo student) {
        this.start_date = start_date;
        this.end_date = end_date;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPartitionField() {
        return partitionField;
    }

    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }

    public StudentGeneralInfo getStudent() {
        return student;
    }

    public void setStudent(StudentGeneralInfo student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return String.format("Holiday [start_date=%s, end_date=%s, reason=%s, partition=%s]", start_date, end_date, reason, partitionField);
    }
}
