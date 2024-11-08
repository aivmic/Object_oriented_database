package students;

import javax.persistence.*;
import java.util.List;

public class StudentDataCollector {

    public static void main(String[] args) {
        // Open a connection to the first database (db1.odb for general info):
        EntityManagerFactory emf1 = Persistence.createEntityManagerFactory("Database1PU");
        EntityManager em1 = emf1.createEntityManager();

        // Open a connection to the second database (db2.odb for learning info):
        EntityManagerFactory emf2 = Persistence.createEntityManagerFactory("Database2PU");
        EntityManager em2 = emf2.createEntityManager();

        try {
            String studentCode = "JJ744";

            // Fetch the student using the provided code
            StudentGeneralInfo student = em1.createQuery("SELECT s FROM StudentGeneralInfo s WHERE s.code = :code", StudentGeneralInfo.class)
                                            .setParameter("code", studentCode)
                                            .getSingleResult();

            System.out.println("\n--- Data for Student: " + student.getName() + " " + student.getSurname() + " (Code: " + student.getCode() + ") ---");

            // Display faculty information
            System.out.println("\nFaculty: " + student.getFaculty());

            // Display holidays information
            List<Holiday> holidays = student.getHolidays();
            System.out.println("\n--- Holidays ---");
            for (Holiday holiday : holidays) {
                System.out.println(holiday);
            }

            // Display courses information
            List<Course> courses = student.getCourses();
            System.out.println("\n--- Courses ---");
            for (Course course : courses) {
                System.out.println(course);
            }

            // Display applications information
            List<Application> applications = student.getApplications();
            System.out.println("\n--- Applications ---");
            for (Application application : applications) {
                System.out.println(application);
            }

            // Retrieve learning information from the second database (db2)
            StudentLearningInfo learningInfo = em2.createQuery("SELECT s FROM StudentLearningInfo s WHERE s.code = :code", StudentLearningInfo.class)
                                                  .setParameter("code", studentCode)
                                                  .getSingleResult();
            System.out.println("\nLearning Info: " + learningInfo);

            // Display module results related to this student
            List<ModuleResult> moduleResults = em2.createQuery("SELECT m FROM ModuleResult m WHERE m.student.id = :id", ModuleResult.class)
                                                  .setParameter("id", learningInfo.getId())
                                                  .getResultList();
            System.out.println("\n--- Module Results ---");
            for (ModuleResult moduleResult : moduleResults) {
                System.out.println(moduleResult);
            }

            // Display projects related to this student
            List<Project> projects = em2.createQuery("SELECT p FROM Project p JOIN p.students s WHERE s.id = :id", Project.class)
                                         .setParameter("id", learningInfo.getId())
                                         .getResultList();
            System.out.println("\n--- Projects ---");
            for (Project project : projects) {
                System.out.println(project);

                // Display supervisor information for each project
                System.out.println("  Supervisor: " + project.getSupervisor());
            }

        } catch (NoResultException e) {
            System.out.println("No student found with the provided code.");
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
