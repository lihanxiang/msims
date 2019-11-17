package com.lee.msims.service.impl.moodle;

import com.lee.msims.mapper.moodle.ComponentMapper;
import com.lee.msims.pojo.moodle.Component;
import com.lee.msims.service.moodle.ComponentService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ComponentServiceImpl implements ComponentService {

    private final ComponentMapper componentMapper;

    public ComponentServiceImpl(ComponentMapper componentMapper) {
        this.componentMapper = componentMapper;
    }

    @Override
    public void addComponentToCourse(Component component) {
        componentMapper.addComponentToCourse(component);
    }

    @Override
    public void addFileToComponent(int componentId, String fileId) {
        componentMapper.addFileToComponent(componentId, fileId);
    }

    @Override
    public List<Component> getAllComponentsOfCourse(String courseCode) {
        return componentMapper.getAllComponentsOfCourse(courseCode);
    }

    @Override
    public List<String> getAllFilesOfComponent(int componentId) {
        return componentMapper.getAllFilesOfComponent(componentId);
    }

    @Override
    public void deleteComponentOfCourse(int id) {
        componentMapper.deleteComponentOfCourse(id);
    }

    @Override
    public void deleteFileOfComponent(String fileId) {
        componentMapper.deleteFileOfComponent(fileId);
    }
}
