package com.lee.msims.controller;

import com.lee.msims.pojo.common.User;
import com.lee.msims.service.common.FileService;
import com.lee.msims.service.common.UserService;
import com.lee.msims.util.DateFormatter;
import com.lee.msims.util.FileIDBuilder;
import com.sun.deploy.net.HttpResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

@Controller
@RequestMapping("file")
public class FileController {

    private final DateFormatter dateFormatter;
    private final FileIDBuilder fileIDBuilder;
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileIDBuilder fileIDBuilder, FileService fileService, UserService userService, DateFormatter dateFormatter) {
        this.fileIDBuilder = fileIDBuilder;
        this.fileService = fileService;
        this.userService = userService;
        this.dateFormatter = dateFormatter;
    }

    @RequestMapping(value = "upload-page", method = RequestMethod.GET)
    public String uploadPage(Model model){
        return "student/file_submission";
    }

    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file, Model model){
        User user = userService.getUserByUserId(
                (String)SecurityUtils.getSubject().getSession().getAttribute("userId"));
        String faculty = user.getFaculty();
        if (!file.isEmpty()){
            StringBuffer sb = new StringBuffer("C:\\Users\\94545\\Desktop\\file");
            sb.append("\\").append(faculty);
            String fileId = fileIDBuilder.generateFileId();
            String fileName = file.getOriginalFilename();
            sb.append("\\").append(fileName);
            String storePath = sb.toString();
            File filePath = new File(storePath);
            if (!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            try {
                file.transferTo(new File(storePath));
                com.lee.msims.pojo.common.File databaseFile =
                        new com.lee.msims.pojo.common.File(faculty, fileId, fileName, storePath,
                                dateFormatter.formatDateToString(new Date()));
                fileService.createFile(databaseFile);
            } catch (IOException e){
                e.printStackTrace();
                return "file/500";
            }
            return "file/uploadSuccess";
        } else {
            return "file/uploadFail";
        }
    }

    @RequestMapping(value = "download/{fileId}")
    public String download(@PathVariable("fileId") String fileId, HttpServletResponse response){
        //File file = new File("C:\\Users\\94545\\Desktop\\file set\\1.txt");
        File file = new File(fileService.getFileByFileId(fileId).getPath());
        response.setHeader("content-type", "application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "file/500";
        }
        return "";
    }
}
