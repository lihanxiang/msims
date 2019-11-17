package com.lee.msims.service.impl.common;

import com.lee.msims.mapper.common.FileMapper;
import com.lee.msims.pojo.common.File;
import com.lee.msims.service.common.FileService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    private final FileMapper fileMapper;

    public FileServiceImpl(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    @Override
    public void createFile(File file) {
        fileMapper.createFile(file);
    }

    @Override
    public void editFileInfo(File file) {
        fileMapper.editFileInfo(file);
    }

    @Override
    public File getFileByFileId(String fileId) {
        return fileMapper.getFileByFileId(fileId);
    }

    @Override
    public List<File> getFilesInFaculty(String faculty) {
        return fileMapper.getFilesInFaculty(faculty);
    }

    @Override
    public void DeleteFile(File file) {
        fileMapper.deleteFile(file);
    }
}
