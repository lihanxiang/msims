package com.lee.msims.service.coes;

import com.lee.msims.pojo.coes.GPA;

import java.util.List;

public interface GPAService {

    // Insert
    void evaluateGPA(GPA gpa);

    // Update
    void editGPA(GPA gpa);

    // Select
    GPA getGradeByCodeAndUserId(String courseCode, String userId);

    List<GPA> getGradeByUserId(String userId);

    List<GPA> getGradeByCode(String courseCode);
}
