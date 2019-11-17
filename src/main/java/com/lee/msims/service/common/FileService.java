package com.lee.msims.service.common;

import com.lee.msims.pojo.common.File;
import java.util.List;

public interface FileService {

    // Insert
    void createFile(File file);

    // Update
    void editFileInfo(File file);

    // Select
    File getFileByFileId(String fileId);

    List<File> getFilesInFaculty(String faculty);

    // Delete
    void DeleteFile(File file);
}
