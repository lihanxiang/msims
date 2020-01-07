package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.Assignment;
import java.util.List;

public interface AssignmentService {

    // Insert
    void createAssignment(Assignment assignment);

    // Update
    void editAssignmentInfo(Assignment assignment);

    // Select
    Assignment getAssignmentById(int id);

    List<Assignment> getAssignmentsOfCourse(String courseCode);

    List<String> getAssignmentsByStudentId(int studentId);

    List<String> getStudentsWhoAreSubmitted(int assignmentId);

    int checkIfSubmitted(int studentId, int assignmentId);

    // Delete
    void deleteAssignment(int id);
}
