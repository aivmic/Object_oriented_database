package students;

import javax.persistence.*;
import java.util.List;

public class DataTest {

    public static void main(String[] args) {
        // Open a connection to the first database (db1.odb for general info):
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("Database1PU");
        EntityManager em1 = emf1.createEntityManager();

        // Open a connection to the second database (db2.odb for learning info):
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("Database2PU");
        EntityManager em2 = emf2.createEntityManager();

        try {
            // Querying and retrieving data from db1 (general info)
            System.out.println("\n--- General Info Database (db1.odb) ---");

            // Fetch students partitioned by AM and NZ
            List<StudentGeneralInfo> studentsListAM = em1.createQuery("SELECT s FROM StudentGeneralInfo s WHERE s.partitionField = 'AM'", StudentGeneralInfo.class).getResultList();
            List<StudentGeneralInfo> studentsListNZ = em1.createQuery("SELECT s FROM StudentGeneralInfo s WHERE s.partitionField = 'NZ'", StudentGeneralInfo.class).getResultList();
            System.out.println("\n--- Students in Database 1 (AM Fragment) ---");
            for (StudentGeneralInfo student : studentsListAM) {
                System.out.println(student);
            }
            System.out.println("\n--- Students in Database 1 (NZ Fragment) ---");
            for (StudentGeneralInfo student : studentsListNZ) {
                System.out.println(student);
            }

            // Fetch courses partitioned by ALL
            List<Course> coursesListALL = em1.createQuery("SELECT c FROM Course c WHERE c.partitionField = 'ALL'", Course.class).getResultList();
            System.out.println("\n--- Courses in Database 1 (ALL Fragment) ---");
            for (Course course : coursesListALL) {
                System.out.println(course);
            }

            // Fetch holidays partitioned by AM and NZ
            List<Holiday> holidaysListAM = em1.createQuery("SELECT h FROM Holiday h WHERE h.partitionField = 'AM'", Holiday.class).getResultList();
            List<Holiday> holidaysListNZ = em1.createQuery("SELECT h FROM Holiday h WHERE h.partitionField = 'NZ'", Holiday.class).getResultList();
            System.out.println("\n--- Holidays in Database 1 (AM Fragment) ---");
            for (Holiday holiday : holidaysListAM) {
                System.out.println(holiday);
            }
            System.out.println("\n--- Holidays in Database 1 (NZ Fragment) ---");
            for (Holiday holiday : holidaysListNZ) {
                System.out.println(holiday);
            }

            // Fetch faculties partitioned by ALL
            List<Faculty> facultiesListALL = em1.createQuery("SELECT f FROM Faculty f WHERE f.partitionField = 'ALL'", Faculty.class).getResultList();
            System.out.println("\n--- Faculties in Database 1 (ALL Fragment) ---");
            for (Faculty faculty : facultiesListALL) {
                System.out.println(faculty);
            }

            // Fetch applications partitioned by AM and NZ
            List<Application> applicationListAM = em1.createQuery("SELECT a FROM Application a WHERE a.partitionField = 'AM'", Application.class).getResultList();
            List<Application> applicationListNZ = em1.createQuery("SELECT a FROM Application a WHERE a.partitionField = 'NZ'", Application.class).getResultList();
            System.out.println("\n--- Applications in Database 1 (AM Fragment) ---");
            for (Application application : applicationListAM) {
                System.out.println(application);
            }
            System.out.println("\n--- Applications in Database 1 (NZ Fragment) ---");
            for (Application application : applicationListNZ) {
                System.out.println(application);
            }

            System.out.println("\n--- Learning Info Database (db2.odb) ---");

            // Fetch students' learning info partitioned by AM and NZ
            List<StudentLearningInfo> learningInfoListAM = em2.createQuery("SELECT s FROM StudentLearningInfo s WHERE s.partitionField = 'AM'", StudentLearningInfo.class).getResultList();
            List<StudentLearningInfo> learningInfoListNZ = em2.createQuery("SELECT s FROM StudentLearningInfo s WHERE s.partitionField = 'NZ'", StudentLearningInfo.class).getResultList();
            System.out.println("\n--- Learning Info in Database 2 (AM Fragment) ---");
            for (StudentLearningInfo studentLearning : learningInfoListAM) {
                System.out.println(studentLearning);
            }
            System.out.println("\n--- Learning Info in Database 2 (NZ Fragment) ---");
            for (StudentLearningInfo studentLearning : learningInfoListNZ) {
                System.out.println(studentLearning);
            }

            // Fetch module results partitioned by AM and NZ
            List<ModuleResult> moduleResultsListAM = em2.createQuery("SELECT m FROM ModuleResult m WHERE m.partitionField = 'AM'", ModuleResult.class).getResultList();
            List<ModuleResult> moduleResultsListNZ = em2.createQuery("SELECT m FROM ModuleResult m WHERE m.partitionField = 'NZ'", ModuleResult.class).getResultList();
            System.out.println("\n--- Module Results in Database 2 (AM Fragment) ---");
            for (ModuleResult moduleResult : moduleResultsListAM) {
                System.out.println(moduleResult);
            }
            System.out.println("\n--- Module Results in Database 2 (NZ Fragment) ---");
            for (ModuleResult moduleResult : moduleResultsListNZ) {
                System.out.println(moduleResult);
            }

            // Fetch projects partitioned by ALL
            List<Project> projectsListALL = em2.createQuery("SELECT p FROM Project p WHERE p.partitionField = 'ALL'", Project.class).getResultList();
            System.out.println("\n--- Projects in Database 2 (ALL Fragment) ---");
            for (Project project : projectsListALL) {
                System.out.println(project);
            }

            // Fetch supervisors partitioned by ALL
            List<Supervisor> supervisorsListAM = em2.createQuery("SELECT s FROM Supervisor s WHERE s.partitionField = 'ALL'", Supervisor.class).getResultList();
            System.out.println("\n--- Supervisors in Database 2 (ALL Fragment) ---");
            for (Supervisor supervisor : supervisorsListAM) {
                System.out.println(supervisor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close both connections
            em1.close();
            emf1.close();
            em2.close();
            emf2.close();
        }
    }
}
