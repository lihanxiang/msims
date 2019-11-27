package com.lee.msims.mapper.moodle;

import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.moodle.Component;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComponentMapper {

    // Insert
    @Insert("INSERT INTO component (courseCode, type, time) VALUES (#{courseCode}, #{type}, #{time})")
    void addComponentToCourse(Component component);

    @Insert("INSERT INTO component_file (componentId, fileId) VALUES (#{componentId}, #{fileId})")
    void addFileToComponent(int componentId, String fileId);

    // Select
    @Select("SELECT * FROM component WHERE courseCode = #{courseCode} ORDER BY time DESC")
    List<Component> getAllComponentsOfCourse(String courseCode);

    @Select("SELECT fileId FROM component_file WHERE componentId = #{componentId}")
    List<String> getAllFilesOfComponent(int componentId);

    // Delete
    @Delete("DELETE FROM component WHERE id = #{id}")
    void deleteComponentOfCourse(int id);

    @Delete("DELETE FROM component_file WHERE fileId = #{fileId}")
    void deleteFileOfComponent(String fileId);
}
