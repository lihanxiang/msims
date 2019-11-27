package com.lee.msims.mapper.common;

import com.lee.msims.pojo.common.File;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
@Repository
public interface FileMapper {

    // Insert
    @Insert("INSERT INTO file (faculty, fileId, name, path, time) " +
            "VALUES (#{faculty}, #{fileId}, #{name}, #{path}, #{time})")
    void createFile(File file);

    // Update
    @Update("UPDATE file SET name = #{name} AND path = #{path}")
    void editFileInfo(File file);

    // Select
    @Select("SELECT * FROM file WHERE fileId = #{fileId} ORDER BY time DESC")
    File getFileByFileId(String fileId);

    @Select("SELECT * FROM file WHERE faculty = #{faculty}")
    List<File> getFilesInFaculty(String faculty);

    // Delete
    @Delete("DELETE FROM file WHERE id = #{id}")
    void deleteFile(File file);
}
