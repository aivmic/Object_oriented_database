package students;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

@Entity
public class Faculty implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String address;

    private String partitionField;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentGeneralInfo> students = new ArrayList<>();

    public Faculty() {
    }

    public Faculty(String name, String address) {
        this.name = name;
        this.address = address;
        this.partitionField = "ALL";
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPartitionField() {
        return partitionField;
    }

    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }

    public List<StudentGeneralInfo> getStudents() {
        return students;
    }

    public void setStudents(List<StudentGeneralInfo> students) {
        this.students = students;
    }

    public void addStudent(StudentGeneralInfo student) {
        students.add(student);
        student.setFaculty(this);
    }

    public void removeStudent(StudentGeneralInfo student) {
        students.remove(student);
        student.setFaculty(null);
    }

    @Override
    public String toString() {
        return String.format("Faculty [name=%s, address=%s, partition=%s]", name, address, partitionField);
    }
}
