package com.example.demo.framework.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.exception.UserException;
import com.example.demo.framework.media.entity.UserFileDO;
import com.example.demo.framework.media.mapper.UserFileMapper;
import com.example.demo.framework.media.service.WebFileService;
import com.example.demo.modules.data.PureUserFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Service
public class WebFileServiceImpl implements WebFileService {

    @Autowired
    UserFileMapper userFileMapper;

    /**
     * 文件上传
     * 1、本地添加
     * 2、数据库添加
     *
     * @param file
     * @throws IOException
     */
    public void upload(MultipartFile file, Integer userId) throws IOException {
        InputStream inputStream = file.getInputStream();
        String fileMd5 = this.getFileMd5(inputStream);//getFileMd5操作会消耗生成器中的内容
//        获得文件MD5值
//        QueryWrapper<UserFileDO> wrapper = new QueryWrapper<>();
        Integer count = userFileMapper.selectCount(new QueryWrapper<UserFileDO>().eq("file_path", fileMd5));//查询数据库中是否存在
        String filepath = "C:/file_store/" + fileMd5;
        File tempFile = new File(filepath);


        if (count == 0 || !tempFile.exists()) {//数据库中无数据，本地文件不存在 做本地保存

            this.saveFile(file.getInputStream(), filepath);

        }
//        插入数据到数据库
//        userId , filename 不能重复

        UserFileDO userFileDO = new UserFileDO(userId, fileMd5, file.getOriginalFilename());
        userFileMapper.insert(userFileDO);

    }


    public void download(HttpServletResponse response, String filename, Integer userId) throws IOException {
//        filename = new String(filename.trim().getBytes("iso8859-1"), "UTF-8"); //url上的信息默认以ascII码编译,解码成utf-8
//        数据库中查询
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.put("user_id", userId);
        tempMap.put("file_name", filename);
        UserFileDO userFileDO = userFileMapper.selectOne(new QueryWrapper<UserFileDO>().allEq(tempMap));//根据用户id和提供的文件名查询用户文件表
        if (userFileDO == null) throw new UserException("没有此文件");
//
        String filePath = "C:/file_store/" + userFileDO.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) throw new FileNotFoundException();
        InputStream io = new FileInputStream(filePath);
//        获取字节流
        ServletOutputStream servletOutputStream = response.getOutputStream();// 获取response
        response.setContentLength(io.available());  //获取文件长度
        response.setHeader("Content-Type", "application/octet-stream");    //服务器接受文件需要设定文件类型 octet-stream仅仅指8进制流

        response.setHeader("Content-Disposition", String.format("attachment;filename=%s", new String(filename.getBytes(), StandardCharsets.ISO_8859_1)));//
        servletOutputStream.write(io.readAllBytes()); //注入文件字节流在Stream中


        response.flushBuffer(); //刷新内存中的文件流
    }

    @Override
    public List<PureUserFile> display(Integer userId) {

        List<UserFileDO> userFileDOs = userFileMapper.selectList(new QueryWrapper<UserFileDO>().eq("user_id", userId));
        List<PureUserFile> pureUserFiles = new LinkedList<PureUserFile>();
        for(UserFileDO userFileDO : userFileDOs){
            pureUserFiles.add(new PureUserFile(userFileDO));
        }

        return pureUserFiles;
    }


    public void saveFile(InputStream inputStream, String filepath) throws IOException {
        OutputStream outputStream = new FileOutputStream(filepath);
        outputStream.write(inputStream.readAllBytes());//进行本地写入
    }


    public String getFileMd5(InputStream inputStream) throws IOException {

        return DigestUtils.md5DigestAsHex(inputStream);//如果数据库中已经存在相同文件则返回null

    }
}
