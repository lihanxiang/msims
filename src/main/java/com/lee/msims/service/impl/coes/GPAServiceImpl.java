package com.lee.msims.service.impl.coes;

import com.lee.msims.mapper.GPA.GPAMapper;
import com.lee.msims.pojo.coes.GPA;
import com.lee.msims.service.coes.GPAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GPAServiceImpl implements GPAService {

    @Autowired
    private GPAMapper gpaMapper;

    @Override
    public void evaluateGPA(GPA gpa) {
        gpaMapper.evaluateGPA(gpa);
    }

    @Override
    public void editGPA(GPA gpa) {
        gpaMapper.editGPA(gpa);
    }

    @Override
    public GPA getGradeByCodeAndUserId(String courseCode, String userId) {
        return gpaMapper.getGradeByCodeAndUserId(courseCode, userId);
    }

    @Override
    public List<GPA> getGradeByUserId(String userId) {
        return gpaMapper.getGradeByUserId(userId);
    }

    @Override
    public List<GPA> getGradeByCode(String courseCode) {
        return gpaMapper.getGradeByCode(courseCode);
    }
}
