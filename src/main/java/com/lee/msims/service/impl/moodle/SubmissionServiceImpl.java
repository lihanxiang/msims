package com.lee.msims.service.impl.moodle;

import com.lee.msims.mapper.moodle.SubmissionMapper;
import com.lee.msims.pojo.moodle.Submission;
import com.lee.msims.service.moodle.SubmissionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionMapper submissionMapper;

    public SubmissionServiceImpl(SubmissionMapper submissionMapper) {
        this.submissionMapper = submissionMapper;
    }

    @Override
    public void createSubmission(Submission submission) {
        submissionMapper.createSubmission(submission);
    }

    @Override
    public void editSubmission(Submission submission) {
        submissionMapper.editSubmission(submission);
    }

    @Override
    public Submission getSubmissionById(int id) {
        return submissionMapper.getSubmissionById(id);
    }

    @Override
    public List<Submission> getSubmissionInAssignment(int assignmentId) {
        return submissionMapper.getSubmissionInAssignment(assignmentId);
    }

    @Override
    public List<Submission> getSubmissionByStudentId(int studentId) {
        return submissionMapper.getSubmissionByStudentId(studentId);
    }

    @Override
    public void deleteSubmission(int id) {
        submissionMapper.deleteSubmission(id);
    }
}
