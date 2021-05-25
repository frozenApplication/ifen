package com.example.demo.modules.controller;

import com.example.demo.exception.UserException;
import com.example.demo.framework.media.service.impl.WebFileServiceImpl;
import com.example.demo.modules.data.JsonResult;
import com.example.demo.modules.data.NotPasswordUser;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.params.UserSampleStructure;
import com.example.demo.modules.processors.UserOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


/**
 * 对于RESTFul的route配置，其下route映射的方法将不会显示调用具体的实现细节
 */
@RestController // 标注为控制器，旗下方法返回值序列化为json
public class UserController {

    @Autowired
    UserOperation userOperation;
    @Autowired
    HttpServletResponse response;
    @Autowired
    WebFileServiceImpl webFileService;

    /**
     * 用户注册
     *
     * @param register_params 包含mobile:String 和 mobile:String 两个依赖参数
     * @return 返回json格式, code:int 0成功,1失败 /data:Map /message:String 成功或者失败信息
     */
    @PostMapping("/api/v1/register")
    public JsonResult userRegister(@Valid UserSampleStructure register_params) {

//        插入用户信息到user表,手机号存在会抛出异常，处理在异常句柄中;
        User user = userOperation.createUserAccount(register_params);
//        make a function about results need to be given to the client.
        return new JsonResult(0, new NotPasswordUser(user), "success");
    }


    /**
     * 用户登录
     *
     * @param loginMessage 包含mobile:String 和 mobile:String 两个依赖参数
     * @return 返回json格式, code:int 0成功,1失败 /data:Map /message:String 成功或者失败信息
     */
    @PostMapping("/api/v1/login")
    public JsonResult userLogin(@Valid UserSampleStructure loginMessage) {
        Map<String, Object> data = new HashMap<>();
//        从数据库取出对应user实体
        User user = userOperation.getUserByMobileAndPwd(loginMessage.getMobile(), loginMessage.getPassword());
        if (user == null) return new JsonResult(1, null, "手机号或者密码错误");//数据库中没有对应用户时
        String jwt = userOperation.generateJwtByUser(user);
//        装载user、jwt信息
        data.put("user", new NotPasswordUser(user));
        data.put("token", jwt);

        return new JsonResult(0, data, "success");
    }


    /**
     * 获取用户信息
     *
     * @param authorization:String
     * @return 返回json格式, code:int 0成功,1失败 /data:Map /message:String 成功或者失败信息
     */
    @GetMapping("/api/v1/me")
    public JsonResult getCurrentUserMessage(@RequestHeader("Authorization") String authorization) {
        User user = userOperation.getUserByJwt(authorization);
        if (user == null) return new JsonResult(1, null, "the jwt is invalid.");

        return new JsonResult(0, new NotPasswordUser(user), "success");
    }


    /**
     * 更新当前用户信息
     *
     * @param name:String
     * @param authorization:String
     * @return 返回json格式, code:int 0成功,1失败 /data:Map /message:String 成功或者失败信息
     */
    @PutMapping("/api/v1/me")
    public JsonResult updateCurrentUserMessage(String name, @RequestHeader(value = "Authorization")
            String authorization) {
//          验证jwt，正确后 获取token数据，根据其中的id获取数据库中的信息修改姓名
//        根据token、姓名，更新用户姓名信息
        if (authorization == null) throw new UserException("please provide json wet token."); //没有jwt信息进行异常拦截处理
        User user = userOperation.getUserByJwt(authorization); //通过token中payload下的id值查询User表中的记录，获得User实例
        user.setUsername(name);
        user.setUpdatedAt(LocalDateTime.now());
        userOperation.updateUser(user);
        return new JsonResult(1, new NotPasswordUser(user), "success");
    }

    /**
     * 更新用户密码
     *
     * @param oldPassword :原始密码
     * @param password    :新密码
     * @param password2   :验证时的密码
     * @return 返回json格式, code:int 0成功,1失败 /data:Map /message:String 成功或者失败信息
     */
    @PostMapping("/api/v1/change_pwd")
    public JsonResult updateUserPassword(String oldPassword, String password, String password2, @RequestHeader("Authorization") String token) {
        if (!password.equals(password2)) return new JsonResult(1, null, "password is inconsistent.");//验证新密码相同
        User user = userOperation.UpdateUserPassword(oldPassword, password, token);
        return new JsonResult(1, new NotPasswordUser(user), "success");
    }


    /**
     * 上传文件
     *
     * @param file :用户上传的文件流
     * @return
     */
    @PostMapping("/api/v1/uploads")
    public JsonResult uploadFile(@RequestParam("file_of_uploader") MultipartFile file, @RequestHeader("Authorization") String token) throws IOException {
        webFileService.upload(file, userOperation.getUserIdByJwt(token));

        return new JsonResult(0, file.getOriginalFilename(), "success");
    }


    @GetMapping("/api/v1/file")
    public void downloadFile(@RequestParam("file") String filename, @RequestHeader("Authorization") String token) throws IOException {
        Integer userId = userOperation.getUserIdByJwt(token);
        webFileService.download(response, filename, userId);

//        根据文件名查找文件，然后返回文件流
//        设置response表头
//        return fileString;

    }
}



