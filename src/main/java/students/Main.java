package students;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Open a connection to the first database (db1.odb for general info):
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("Database1PU");
        EntityManager em1 = emf1.createEntityManager();

        // Open a connection to the second database (db2.odb for learning info):
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("Database2PU");
        EntityManager em2 = emf2.createEntityManager();

        // Begin transaction for general info database (db1)
        em1.getTransaction().begin();

        // Dynamically persist faculties
        persistFaculty(em1, "Informatikos fakultetas", "Studentu g. 50, Kaunas");
        persistFaculty(em1, "Mechanikos fakultetas", "Maironio g. 10, Vilnius");
        persistFaculty(em1, "Matematikos ir Informatikos fakultetas", "Sauletekio al. 15, Vilnius");
        persistFaculty(em1, "Fizikos fakultetas", "Naugarduko g. 24, Vilnius");
        persistFaculty(em1, "Medicinos fakultetas", "Santariskiu g. 5, Vilnius");
        persistFaculty(em1, "Teises fakultetas", "J. Basanaviciaus g. 7, Kaunas");
        persistFaculty(em1, "Ekonomikos fakultetas", "Gedimino pr. 9, Vilnius");
        persistFaculty(em1, "Verslo administravimo fakultetas", "Laisves al. 77, Vilnius");
        persistFaculty(em1, "Statybos fakultetas", "Savanoriu pr. 31, Kaunas");
        persistFaculty(em1, "Menu fakultetas", "Ausros g. 32, Siauliai");
        
        em1.getTransaction().commit();
        
        em1.getTransaction().begin();
        
        List<Faculty> faculties = em1.createQuery("SELECT f FROM Faculty f", Faculty.class).getResultList();
        
        // Dynamically persist students
        persistStudent(em1, "Petras", "Petraitis", "petras.petraitis@example.com", "37061111111", "1999-07-10", "Misko g. 5, Kaunas", faculties.get(0));
        persistStudent(em1, "Jonas", "Jonaitis", "jonas.jonaitis@example.com", "37062222222", "1999-03-22", "Lauko g. 10, Kaunas", faculties.get(1));
        persistStudent(em1, "Viktorija", "Vitkauskaite", "viktorija.vitkauskaite@example.com", "37063333333", "2000-05-12", "Zalioji g. 8, Kaunas", faculties.get(2));
        persistStudent(em1, "Milda", "Mildute", "milda.milkute@example.com", "37064444444", "1998-11-30", "Pusu g. 4, Kaunas", faculties.get(3));
        persistStudent(em1, "Ruta", "Rutaviciute", "ruta.rutaviciute@example.com", "37065555555", "2001-01-01", "Pievu g. 3, Kaunas", faculties.get(4));
        persistStudent(em1, "Antanas", "Antanavicius", "antanas.antanavicius@example.com", "37066666666", "2000-08-20", "Ažuolu g. 9, Kaunas", faculties.get(5));
        persistStudent(em1, "Dalia", "Daliauskaite", "dalia.daliauskaite@example.com", "37067777777", "1997-09-15", "Vandens g. 6, Kaunas", faculties.get(6));
        persistStudent(em1, "Mindaugas", "Mindaugaitis", "mindaugas.mindaugaitis@example.com", "37068888888", "1996-12-23", "Veju g. 7, Kaunas", faculties.get(7));
        persistStudent(em1, "Rimantas", "Rimkevicius", "rimantas.rimkevicius@example.com", "37069999999", "1998-02-10", "Kalnu g. 2, Kaunas", faculties.get(8));
        persistStudent(em1, "Simona", "Simonaite", "simona.simonaite@example.com", "37060000000", "1999-04-05", "Geliu g. 12, Kaunas", faculties.get(9));

        em1.getTransaction().commit();
        
        em1.getTransaction().begin();
        
        // Assign holidays to students
        createAndAssignHolidays(em1);

        em1.getTransaction().commit();
        
        em1.getTransaction().begin();
        
        // Create and persist test data for courses
        Course[] courses = createCourses(em1);

        em1.getTransaction().commit();
        
        em1.getTransaction().begin();
        
        List<StudentGeneralInfo> students = em1.createQuery("SELECT s FROM StudentGeneralInfo s", StudentGeneralInfo.class).getResultList();
        // Select course from database when courses are already created
//        Course[] courses = em1.createQuery("SELECT c FROM Course c", Course.class)
//                      .getResultList()
//                      .toArray(new Course[0]);

        // Assign each student to their respective courses
        assignCoursesToStudents(em1, students, courses);
        
        em1.getTransaction().commit();
        
        em1.getTransaction().begin();

        // Create and persist applications based on test data
        Application[] applications = new Application[10];
        applications[0] = createApplication(em1, LocalDate.of(2024, 9, 14), "Stipendijos konkursas", students.get(0));
        applications[1] = createApplication(em1, LocalDate.of(2024, 11, 18), "Mainu programa", students.get(1));
        applications[2] = createApplication(em1, LocalDate.of(2024, 10, 2), "Praktikos konkursas", students.get(2));
        applications[3] = createApplication(em1, LocalDate.of(2024, 8, 30), "Tyrimu projektas", students.get(3));
        applications[4] = createApplication(em1, LocalDate.of(2024, 7, 18), "Sporto programa", students.get(4));
        applications[5] = createApplication(em1, LocalDate.of(2024, 6, 5), "Kurybinis projektas", students.get(5));
        applications[6] = createApplication(em1, LocalDate.of(2024, 3, 14), "Tarptautinis konkursas", students.get(6));
        applications[7] = createApplication(em1, LocalDate.of(2024, 1, 10), "Kursu mainai", students.get(7));
        applications[8] = createApplication(em1, LocalDate.of(2024, 5, 9), "Socialine veikla", students.get(8));
        applications[9] = createApplication(em1, LocalDate.of(2024, 4, 25), "Ekonomikos projektas", students.get(9));

        em1.getTransaction().commit();
        
        em2.getTransaction().begin();

        // Create and persist learning info for each student
        for (StudentGeneralInfo student : students) {
            String code = student.getCode();
            String startingDate = LocalDate.of(2020, 9, 1).toString();

            StudentLearningInfo learningInfo = new StudentLearningInfo(code, startingDate);

            em2.persist(learningInfo);
        }
        
        em2.getTransaction().commit();
        
        em2.getTransaction().begin();

        List<StudentLearningInfo> learningInfos = em2.createQuery("SELECT s FROM StudentLearningInfo s", StudentLearningInfo.class).getResultList();

        // Create and persist ModuleResult records based on learningInfos
        createAndPersistModuleResults(em2, learningInfos);

        // Persist supervisors
        persistSupervisors(em2);
        
        em2.getTransaction().commit();
        
        em2.getTransaction().begin();

        Supervisor[] supervisors = em2.createQuery("SELECT s FROM Supervisor s", Supervisor.class)
                             .getResultList()
                             .toArray(new Supervisor[0]);

        // Create projects
        createProjects(em2, supervisors, learningInfos);

        em2.getTransaction().commit();

        // Close both connections
        em1.close();
        emf1.close();
        em2.close();
        emf2.close();
    }

    // Helper method to dynamically persist faculties
    private static void persistFaculty(EntityManager em1, String name, String address) {
        Faculty faculty = new Faculty(name, address);
        em1.persist(faculty);
    }

    // Helper method to dynamically persist students
    private static void persistStudent(EntityManager em1, String firstName, String lastName, String email, String phoneNumber, String birthDate, String address, Faculty faculty) {
        StudentGeneralInfo student = new StudentGeneralInfo(firstName, lastName, email, phoneNumber, birthDate, address, faculty);
        em1.persist(student);
    }
    
    // Method to create holidays and assign them to students using test data
    private static void createAndAssignHolidays(EntityManager em1) {
        List<StudentGeneralInfo> students = em1.createQuery("SELECT s FROM StudentGeneralInfo s", StudentGeneralInfo.class).getResultList();

        if (!students.isEmpty()) {
            createHoliday(em1, "Ziemos atostogos", LocalDate.of(2024, 12, 15), LocalDate.of(2024, 12, 20), students.get(0));
            createHoliday(em1, "Liga", LocalDate.of(2024, 10, 20), LocalDate.of(2024, 10, 25), students.get(0));
            createHoliday(em1, "Mainu programa", LocalDate.of(2024, 3, 10), LocalDate.of(2024, 3, 15), students.get(1));
            createHoliday(em1, "Vasaros stovykla", LocalDate.of(2024, 7, 14), LocalDate.of(2024, 7, 20), students.get(1));
            createHoliday(em1, "Mainai uzsienyje", LocalDate.of(2024, 5, 10), LocalDate.of(2024, 5, 15), students.get(2));
            createHoliday(em1, "Kurybines atostogos", LocalDate.of(2024, 6, 14), LocalDate.of(2024, 6, 20), students.get(2));
            createHoliday(em1, "Vasaros poilsis", LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 5), students.get(3));
            createHoliday(em1, "Kaledines atostogos", LocalDate.of(2024, 12, 25), LocalDate.of(2024, 12, 31), students.get(3));
            createHoliday(em1, "Seimos poilsis", LocalDate.of(2024, 10, 12), LocalDate.of(2024, 10, 18), students.get(4));
            createHoliday(em1, "Tyrimu stovykla", LocalDate.of(2024, 8, 20), LocalDate.of(2024, 8, 25), students.get(4));
        }
    }

    // Helper method to create a holiday and assign it to a student
    private static void createHoliday(EntityManager em1, String reason, LocalDate startDate, LocalDate endDate, StudentGeneralInfo student) {
        Date start = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date end = Date.from(endDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Holiday holiday = new Holiday(start, end, reason, student);
        student.addHoliday(holiday);

        em1.persist(holiday);
        em1.merge(student);
    }
    
    // Method to create courses
    private static Course[] createCourses(EntityManager em1) {
        Course[] courses = new Course[4];
        for (int i = 0; i < 4; i++) {
            String courseName = (i + 1) + " kursas";
            Date startDate = Date.from(LocalDate.of(2024, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(LocalDate.of(2025, 6, 30).atStartOfDay(ZoneId.systemDefault()).toInstant());
            courses[i] = new Course(courseName, startDate, endDate, new ArrayList<>());
            em1.persist(courses[i]);
        }
        return courses;
    }

    // Method to assign courses to students
    private static void assignCoursesToStudents(EntityManager em1, List<StudentGeneralInfo> students, Course[] courses) {
        for (StudentGeneralInfo student : students) {
            int currentCourseLevel = determineStudentCourseLevel(student);

            for (int i = 0; i < currentCourseLevel; i++) {
                Course course = courses[i];

                if (!student.getCourses().contains(course)) {
                    student.addCourse(course);
                    course.addStudent(student);

                    em1.merge(course);
                    em1.merge(student);
                }
            }
        }
    }

    // Helper method to determine the student's current course level
    private static int determineStudentCourseLevel(StudentGeneralInfo student) {
        String code = student.getCode();
        if (code.contains("1")) {
            return 1;
        } else if (code.contains("2")) {
            return 2;
        } else if (code.contains("3")) {
            return 3;
        } else if (code.contains("4")) {
            return 4;
        }
        return 1;
    }
    
    // Helper method to create and persist an application
    private static Application createApplication(EntityManager em, LocalDate localDate, String reason, StudentGeneralInfo student) {
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Application application = new Application(date, reason, student);
        em.persist(application);
        student.getApplications().add(application);
        em.merge(student);
        return application;
    }
    
    // Method to create and persist ModuleResult records
    private static void createAndPersistModuleResults(EntityManager em2, List<StudentLearningInfo> learningInfos) {
        ModuleResult[] moduleResults = new ModuleResult[10];
        moduleResults[0] = new ModuleResult("Matematika 1", "Ruduo", "9", learningInfos.get(0));
        moduleResults[1] = new ModuleResult("Fizika 1", "Ruduo", "8", learningInfos.get(1));
        moduleResults[2] = new ModuleResult("Objektinis programavimas", "Ruduo", "10", learningInfos.get(2));
        moduleResults[3] = new ModuleResult("Inzinerines matematikos pagrindai", "Ruduo", "7", learningInfos.get(3));
        moduleResults[4] = new ModuleResult("Duomenu strukturos", "Ruduo", "8", learningInfos.get(4));
        moduleResults[5] = new ModuleResult("Skaitmenine logika", "Ruduo", "10", learningInfos.get(5));
        moduleResults[6] = new ModuleResult("Diskrecioji matematika", "Ruduo", "9", learningInfos.get(6));
        moduleResults[7] = new ModuleResult("Kompiuterines sistemos", "Ruduo", "9", learningInfos.get(7));
        moduleResults[8] = new ModuleResult("Programavimo metodai", "Ruduo", "8", learningInfos.get(8));
        moduleResults[9] = new ModuleResult("Inzinerine fizika", "Ruduo", "7", learningInfos.get(9));

        for (ModuleResult result : moduleResults) {
            result.getStudent().addModuleResult(result);
            em2.persist(result);
            em2.merge(result.getStudent());
        }
    }
    
    // Method to dynamically persist supervisors
    private static void persistSupervisors(EntityManager em1) {
        Supervisor[] supervisors = new Supervisor[10];
        supervisors[0] = new Supervisor("Antanas", "Antanaitis", "Docentas");
        supervisors[1] = new Supervisor("Asta", "Astaite", "Lektore");
        supervisors[2] = new Supervisor("Tadas", "Tadavicius", "Profesorius");
        supervisors[3] = new Supervisor("Nida", "Nidaite", "Mokslininke");
        supervisors[4] = new Supervisor("Saulius", "Saulaitis", "Mokslininkas");
        supervisors[5] = new Supervisor("Inga", "Ingeviciute", "Lektore");
        supervisors[6] = new Supervisor("Vytautas", "Vyte", "Docentas");
        supervisors[7] = new Supervisor("Dalia", "Daliauskaite", "Docente");
        supervisors[8] = new Supervisor("Simonas", "Simonyte", "Lektore");
        supervisors[9] = new Supervisor("Monika", "Monkaite", "Profesore");

        for (Supervisor supervisor : supervisors) {
            em1.persist(supervisor);
        }
    }

    // Helper method to create and persist projects
    private static void createProjects(EntityManager em, Supervisor[] supervisors, List<StudentLearningInfo> learningInfos) {
        Project[] projects = new Project[10];
        projects[0] = new Project("Savarankiskas projektas", 
                Date.from(LocalDate.of(2024, 10, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                6, Arrays.asList(learningInfos.get(0)), supervisors[0]);
        projects[1] = new Project("Mokslo projektas", 
                Date.from(LocalDate.of(2024, 12, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                3, Arrays.asList(learningInfos.get(1), learningInfos.get(2)), supervisors[1]);
        projects[2] = new Project("Tyrimu projektas", 
                Date.from(LocalDate.of(2024, 11, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                5, Arrays.asList(learningInfos.get(3)), supervisors[2]);
        projects[3] = new Project("Technologiju projektas", 
                Date.from(LocalDate.of(2024, 9, 1).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                8, Arrays.asList(learningInfos.get(4)), supervisors[3]);
        projects[4] = new Project("Programavimo projektas", 
                Date.from(LocalDate.of(2024, 11, 5).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 25).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                4, Arrays.asList(learningInfos.get(5)), supervisors[4]);
        projects[5] = new Project("Skaitmeniniu technologiju projektas", 
                Date.from(LocalDate.of(2024, 8, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 11, 10).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                7, Arrays.asList(learningInfos.get(6)), supervisors[5]);
        projects[6] = new Project("Inzinerinis projektas", 
                Date.from(LocalDate.of(2024, 10, 20).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                9, Arrays.asList(learningInfos.get(7)), supervisors[6]);
        projects[7] = new Project("Diskreciuju sistemu projektas", 
                Date.from(LocalDate.of(2024, 11, 2).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 31).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                10, Arrays.asList(learningInfos.get(8)), supervisors[7]);
        projects[8] = new Project("Matematikos taikymo projektas", 
                Date.from(LocalDate.of(2024, 9, 15).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 11, 30).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                8, Arrays.asList(learningInfos.get(9)), supervisors[8]);
        projects[9] = new Project("Moksliniu tyrimu projektas", 
                Date.from(LocalDate.of(2024, 10, 12).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                Date.from(LocalDate.of(2024, 12, 25).atStartOfDay(ZoneId.systemDefault()).toInstant()), 
                7, Arrays.asList(learningInfos.get(0), learningInfos.get(1)), supervisors[9]);

        for (Project project : projects) {
            em.persist(project);
        }
    }
}
