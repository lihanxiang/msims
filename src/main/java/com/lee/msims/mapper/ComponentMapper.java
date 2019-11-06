package com.lee.msims.mapper;

import com.lee.msims.pojo.common.File;
import com.lee.msims.pojo.moodle.Component;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ComponentMapper {

    // Insert
    @Insert("INSERT INTO component (courseCode, type) VALUES (#{courseCode}, #{type})")
    void addComponentToCourse(Component component);

    @Insert("INSERT INTO component_file (componentId, fileId) VALUES (#{componentId}, #{fileId})")
    void addFileToComponent(int componentId, String fileId);

    // Select
    @Select("SELECT * FROM component WHERE courseCode = #{courseCode}")
    void getAllComponentsOfCourse(String courseCode);

    @Select("SELECT * FROM component_file WHERE componentId = #{componentId}")
    void getAllFilesOfComponent(int componentId);

    // Delete
    @Delete("DELETE FROM component WHERE id = #{id}")
    void deleteComponentOfCourse(int id);

    @Delete("DELETE FROM component_file WHERE fileId = #{fileId}")
    void deleteFileOfComponent(String fileId);
}
