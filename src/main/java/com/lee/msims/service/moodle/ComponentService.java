package com.lee.msims.service.moodle;

import com.lee.msims.pojo.moodle.Component;
import java.util.List;

public interface ComponentService {

    // Insert
    void addComponentToCourse(Component component);

    void addFileToComponent(int componentId, String fileId);

    // Select
    List<Component> getAllComponentsOfCourse(String courseCode);

    List<String> getAllFilesOfComponent(int componentId);

    // Delete
    void deleteComponentOfCourse(int id);

    void deleteFileOfComponent(String fileId);
}
