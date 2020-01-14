package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.Submission;

import java.util.List;

public interface SubmissionService {

    // Insert
    void createSubmission(Submission submission);

    // Update
    void editSubmission(Submission submission);

    void gradeSubmission(Submission submission);

    // Select
    Submission getSubmissionById(int id);

    Submission getSubmissionByAssignmentIdAndStudentId(int assignmentId, String studentId);

    List<Submission> getSubmissionInAssignment(int assignmentId);

    List<Submission> getSubmissionInAssignmentNotGraded(int assignmentId);

    List<Submission> getSubmissionInAssignmentGraded(int assignmentId);

    List<Submission> getSubmissionByStudentId(String studentId);

    // Delete
    void deleteSubmission(int id);
}
