package com.lee.msims.service.impl.moodle;

import com.lee.msims.mapper.moodle.AssessmentMapper;
import com.lee.msims.pojo.moodle.Assessment;
import com.lee.msims.service.moodle.AssessmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    private final AssessmentMapper assessmentMapper;

    public AssessmentServiceImpl(AssessmentMapper assessmentMapper) {
        this.assessmentMapper = assessmentMapper;
    }

    @Override
    public void createAssessment(Assessment assessment) {
        assessmentMapper.createAssessment(assessment);
    }

    @Override
    public void editAssessment(Assessment assessment) {
        assessmentMapper.editAssessment(assessment);
    }

    @Override
    public Assessment getAssessmentById(int id) {
        return assessmentMapper.getAssessmentById(id);
    }

    @Override
    public List<Assessment> getAssessmentsInAssignment(int assignmentId) {
        return assessmentMapper.getAssessmentsInAssignment(assignmentId);
    }

    @Override
    public List<Assessment> getAssessmentByScore(double min, double max) {
        return assessmentMapper.getAssessmentByScore(min, max);
    }

    @Override
    public void deleteAssessment(int id) {
        assessmentMapper.deleteAssessment(id);
    }
}
