package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.Assessment;

import java.util.List;

public interface AssessmentService {

    // Insert
    void createAssessment(Assessment assessment);

    // Update
    void editAssessment(Assessment assessment);

    // Select
    Assessment getAssessmentById(int id);

    Assessment getAssessmentBySubmissionId(int submissionId);

    List<Assessment> getAssessmentsInAssignment(int assignmentId);

    List<Assessment> getAssessmentByScore(double min, double max);

    // Delete
    void deleteAssessment(int id);
}
