package com.example.demo.framework.media.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class FileService {
    public static void uploadFile(MultipartFile file) throws IOException {
        InputStream ins = file.getInputStream();
        OutputStream os = new FileOutputStream("C:/file_store/" + file.getOriginalFilename());
//        设定为大小不超过5M的文件
//        保存到本地
//        需要写入数据库吗？
        os.write(ins.readAllBytes());
    }


    public static void downloadFile(HttpServletResponse response, String filename) throws IOException {
        filename = new String(filename.trim().getBytes("iso8859-1"), "UTF-8"); //url上的信息默认以ascII码编译,解码成utf-8
        File file = new File("C:/file_store/" + filename);
        if (!file.exists()) throw new FileNotFoundException();
        InputStream io = new FileInputStream("C:/file_store/" + filename);
//        获取字节流
        ServletOutputStream servletOutputStream = response.getOutputStream();// 获取response
        response.setContentLength(io.available());  //获取文件长度
        response.setHeader("Content-Type", "application/octet-stream");    //服务器接受文件需要设定文件类型 octet-stream仅仅指8进制流
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s", filename));//
        servletOutputStream.write(io.readAllBytes()); //注入文件字节流在Stream中


        response.flushBuffer(); //刷新内存中的文件流
    }
}
