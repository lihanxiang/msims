package com.lee.msims.service.impl.moodle;

import com.lee.msims.mapper.moodle.AssignmentMapper;
import com.lee.msims.pojo.moodle.Assignment;
import com.lee.msims.service.moodle.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentMapper assignmentMapper;

    public AssignmentServiceImpl(AssignmentMapper assignmentMapper) {
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public void createAssignment(Assignment assignment) {
        assignmentMapper.createAssignment(assignment);
    }

    @Override
    public void editAssignmentInfo(Assignment assignment) {
        assignmentMapper.editAssignmentInfo(assignment);
    }

    @Override
    public Assignment getAssignmentById(int id) {
        return assignmentMapper.getAssignmentById(id);
    }

    @Override
    public List<Assignment> getAssignmentsOfCourse(String courseCode) {
        return assignmentMapper.getAssignmentsOfCourse(courseCode);
    }

    @Override
    public List<String> getAssignmentsByStudentId(int studentId) {
        return assignmentMapper.getAssignmentsByStudentId(studentId);
    }

    @Override
    public List<String> getStudentsWhoAreSubmitted(int assignmentId) {
        return assignmentMapper.getStudentsWhoAreSubmitted(assignmentId);
    }

    @Override
    public int checkIfSubmitted(int studentId, int assignmentId) {
        return assignmentMapper.checkIfSubmitted(studentId, assignmentId);
    }

    @Override
    public void deleteAssignment(int id) {
        assignmentMapper.deleteAssignment(id);
    }
}
