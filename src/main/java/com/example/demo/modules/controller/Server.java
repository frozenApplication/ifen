package com.example.demo.modules.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.exception.UserException;
import com.example.demo.modules.entity.JsonWebToken;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.mapper.JsonWebTokenMapper;
import com.example.demo.modules.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller // 标注为控制器，旗下方法返回值序列化为json
public class Server {


    @Resource
    UserMapper userMapper;
    @Resource
    JsonWebTokenMapper jsonWebTokenMapper;


    /**
     * 用户注册
     *
     * @param mobile   :手机号 11位
     * @param password 密码
     * @return json信息
     */
    @PostMapping("/api/v1/register")
    public Map userRegister(String mobile, String password) {
//        判断输入值是否正确
        Map<String, Object> map = new HashMap<>();

        User u = new User();
//        插入用户信息到user表
        u.setMobile(mobile);
        u.setUsername(mobile);
        u.setPassword(password);
        u.setCreated_at(LocalDateTime.now());
        u.setUpdated_at(LocalDateTime.now());
        userMapper.insert(u);
//        整理json信息
        map.put("code", 0);
        map.put("data", u);
        map.put("message", "access");
        return map;
    }

    /**
     * 用户登录
     *
     * @param mobile   ：手机号11位
     * @param password ：密码
     * @return
     */
    @PostMapping("/api/v1/login")
    public Map userLogin(String mobile, String password) {
        User u;
        Map<String, String> m = new HashMap<>();
        m.put("mobile", mobile);
        m.put("password", password);
        u = userMapper.selectOne(new QueryWrapper<User>().allEq(m));
        Map<String, Object> map = new HashMap<>();
        String token;
//        创建jwk，保存到数据库
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            token = JWT.create()
                    .withIssuer("auth0")
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            //Invalid Signing configuration / Couldn't convert Claims.
            token = null;
        }
        u.setMobile(mobile);
        u.setPassword(password);
        map.put("code", 0);
        map.put("data", token);
        map.put("user", u);
        return map;
    }

    /**
     * 获取用户信息
     *
     * @param authorization:String
     * @return Map
     */
    @GetMapping("/api/v1/me")
    public Map getCurrentUserMessage(@RequestHeader("Authorization") String authorization) {
        Map<String, Object> m = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        User u;
        m.put("token", authorization);
        List<JsonWebToken> jwts = jsonWebTokenMapper.selectByMap(m);
        Integer id = jwts.get(0).getId();
        u = userMapper.selectById(id);

//        u = getUser(id);
        map.put("code", 0);
        map.put("data", u);
        map.put("message", "success");
        return map;
    }


    /**
     * 更新当前用户信息
     *
     * @param name:String
     * @param authorization:String
     * @return Map
     */
    @PutMapping("/api/v1/me")
    public Map updateCurrentUserMessage(String name, @RequestHeader(value = "Authorization")
            String authorization) {

        Map<String, Object> map = new HashMap<>();
        User u = new User();

//        根据token、姓名，更新用户姓名信息
        if (authorization == null) {
            throw new UserException();
        }
        map.put("code", 1);
        map.put("data", u);
        map.put("message", "success");

        return map;
    }

    /**
     * 更新用户密码
     *
     * @param oldPassword :原始密码
     * @param password    :新密码
     * @param password2   :验证时的密码
     * @return
     */
    @PostMapping("/api/v1/change_pwd")
    public Map updateUserPassword(String oldPassword, String password, String password2, @RequestHeader("Authorization") String token) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> m = new HashMap<>();
        User u;
        String msg = "success";
        m.put("token", token);
        List<JsonWebToken> jwts = jsonWebTokenMapper.selectByMap(m);
        Integer id = jwts.get(0).getId();
        u = userMapper.selectById(id);
        if (!password.equals(password2)) throw new UserException();

        if (u.getPassword().equals(oldPassword)) {
            u.setPassword(password);
            userMapper.updateById(u);
        } else {
            msg = "original password is inconsistent. ";
        }

        map.put("code", 0);
        map.put("data", u);
        map.put("message", msg);
        return map;
    }


    /**
     * 上传文件
     *
     * @param file :用户上传的文件流
     * @return
     */
    @PostMapping("/api/v1/uploads")
    public Map uploadFile(@RequestParam("file_of_uploader") MultipartFile file) throws IOException {
//        获取文件流，进行保存
        InputStream ins = file.getInputStream();
        OutputStream os = new FileOutputStream("C:/file_store/" + file.getOriginalFilename());
//        设定为大小不超过5M的文件
//        保存到本地
//        需要写入数据库吗？
        os.write(ins.readAllBytes());
        Map<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", "ok");
        map.put("message", "success");
        return map;
    }

    @Autowired
    HttpServletResponse response;

    @GetMapping("/api/v1/file")
    public void downloadFile(@RequestParam("file") String filename) throws IOException {
        filename = new String(filename.trim().getBytes("iso8859-1"), "UTF-8"); //url上的信息默认以ascII码编译,解码成utf-8
        File file = new File("C:/file_store/" + filename);
        if (!file.exists()) throw new FileNotFoundException();
        InputStream io = new FileInputStream("C:/file_store/" + filename);
//        获取字节流
        ServletOutputStream servletOutputStream = response.getOutputStream();// 获取response
        response.setContentLength(io.available());  //获取文件长度
        response.setHeader("Content-Type","application/octet-stream");    //服务器接受文件需要设定文件类型 octet-stream仅仅指8进制流
        response.setHeader("Content-Disposition", String.format("attachment;filename=%s", filename));//
        servletOutputStream.write(io.readAllBytes()); //注入文件字节流在Stream中


        response.flushBuffer(); //刷新内存中的文件流


//        根据文件名查找文件，然后返回文件流
//        设置response表头
//        return fileString;

    }
}



