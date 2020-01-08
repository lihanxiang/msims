package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.Submission;

import java.util.List;

public interface SubmissionService {

    // Insert
    void createSubmission(Submission submission);

    // Update
    void editSubmission(Submission submission);

    // Select
    Submission getSubmissionById(int id);

    List<Submission> getSubmissionInAssignment(int assignmentId);

    List<Submission> getSubmissionByStudentId(int studentId);

    // Delete
    void deleteSubmission(int id);
}
