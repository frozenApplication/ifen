package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.modules.entity.JsonWebToken;
import com.example.demo.modules.entity.User;
import com.example.demo.modules.mapper.JsonWebTokenMapper;
import com.example.demo.modules.mapper.UserMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    User user;
    @Resource
    private UserMapper userMapper;
    @Autowired
    JsonWebToken jsonWebToken;
    @Resource
    private JsonWebTokenMapper jsonWebTokenMapper;

    @Test
    void contextLoads() {
//        System.out.println(u);
        System.out.println(("----- selectAll method test ------"));
        Map<String, Object> map = new HashMap<>();
        map.put("mobile", "15327277878");
        map.put("password", "123456");
        List<User> userList = userMapper.selectList(new QueryWrapper<User>().allEq(map));
        Assert.assertEquals(2, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    void cInsert() {
//        userMapper.insert(user);
        jsonWebToken.setId(1);
        jsonWebToken.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.izVguZPRsBQ5Rqw6dhMvcIwy8_9lQnrO3vpxGwPCuzs");
        jsonWebTokenMapper.insert(jsonWebToken);
        System.out.println(jsonWebToken);
    }

}
