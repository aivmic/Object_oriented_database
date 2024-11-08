package students;

import java.io.Serializable;
import java.util.Random;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class StudentGeneralInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private String code;

    private String name;
    private String surname;
    private String email;
    private String phone_number;
    private String birth_date;
    private String address;

    private String partitionField;

    @ManyToOne
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Holiday> holidays = new ArrayList<>();
    
    @ManyToMany
    @JoinTable(
        name = "student_course",
        joinColumns = @JoinColumn(name = "student_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new ArrayList<>();
    
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Application> applications = new ArrayList<>();

    public StudentGeneralInfo() {
    }

    public StudentGeneralInfo(String name, String surname, String email, String phone_number, String birth_date, String address, Faculty faculty) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone_number = phone_number;
        this.birth_date = birth_date;
        this.address = address;
        if (this.code == null || this.code.isEmpty()) {
            this.code = generateCode();
        }
        this.partitionField = determinePartition(code);  // Assign partition based on surname
        this.faculty = faculty;
    }

    private String generateCode() {
        Random random = new Random();
        int randomNumber = random.nextInt(10000);
        return name.substring(0, 1) + surname.substring(0, 1) + randomNumber;
    }

    private String determinePartition(String code) {
        String firstLetter = code.substring(0, 1).toUpperCase();
        return (firstLetter.compareTo("A") >= 0 && firstLetter.compareTo("M") <= 0) ? "AM" : "NZ";
    }

    public long getId() {
        return id;
    }

    public String getCode() {
        return code;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(String birth_date) {
        this.birth_date = birth_date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }

    public String getPartitionField() {
        return partitionField;
    }

    public void setPartitionField(String partitionField) {
        this.partitionField = partitionField;
    }
    
    public List<Holiday> getHolidays() {
        return holidays;
    }

    public void setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
    }

    public void addHoliday(Holiday holiday) {
        holidays.add(holiday);
        holiday.setStudent(this);
    }
    
    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
    
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            if (!course.getStudents().contains(this)) {
                course.getStudents().add(this);
            }
        }
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public void addApplication(Application application) {
        if (!applications.contains(application)) {
            applications.add(application);
            application.setStudent(this);
        }
    }
    
    @Override
    public String toString() {
        return String.format("StudentGeneralInfo [name=%s, surname=%s, email=%s, code=%s, partition=%s]", name, surname, email, code, partitionField);
    }
}
