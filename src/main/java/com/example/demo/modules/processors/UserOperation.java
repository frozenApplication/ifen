package com.example.demo.modules.processors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.exception.UserException;
import com.example.demo.framework.jwt.contract.JWTContract;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.mapper.UserMapper;
import com.example.demo.modules.params.UserSampleStructure;
import com.example.demo.modules.service.EncodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
public class UserOperation {

    @Resource
    UserMapper userMapper;
    @Autowired
    JWTContract jwtContract;
    @Autowired
    EncodeService encodeService;
    public String generateJwtByUser(User user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("mobile", user.getMobile());
//        根据用户id和手机号生成jwt

        return jwtContract.encode(map);
    }

    public User getUserByMobileAndPwd(String mobile, String password) {
        //        根据mobile与password寻找数据库信息
        Map<String, String> tempMap = new HashMap<>();
        tempMap.put("mobile", mobile);
        tempMap.put("password", encodeService.encode(password));
        User u = userMapper.selectOne(new QueryWrapper<User>().allEq(tempMap)); // user可能为null
        return u;
    }
    public Integer getUserIdByJwt(String token) {
        Map<String, ?> payloadMap = jwtContract.decode(token);//获取payload中的字段

        return Integer.valueOf(payloadMap.get("id").toString());
    }
    public User getUserByJwt(String token) {

        return this.getUserById(this.getUserIdByJwt(token));

    }

    public User getUserById(Integer id) {
        return userMapper.selectById(id);
    }


    public User createUserAccount(UserSampleStructure params) {
//        不存在就然后插入，存在就抛出异常
        User u = new User();
        u.setMobile(params.getMobile());
        u.setUsername(params.getMobile());
        u.setPassword(encodeService.encode(params.getPassword()));//密码md5加密
        u.setCreatedAt(LocalDateTime.now());
        u.setUpdatedAt(LocalDateTime.now());
        userMapper.insert(u);
        return u;
    }

    public void updateUser(User user) {
        userMapper.updateById(user);
    }


    public User UpdateUserPassword(String oldPassword, String password, String token) {
        User user = this.getUserByJwt(token);
        if (user == null) throw new UserException("the jwt is invalid.");//判断是否获取到user实例
        if (!user.getPassword().equals(encodeService.encode(oldPassword)))
            throw new UserException("original password is inconsistent. ");//验证原始密码
        user.setUpdatedAt(LocalDateTime.now());
        user.setPassword(encodeService.encode(password));//修改用户密码
        userMapper.updateById(user);//更新数据库
        return user;
    }



}
