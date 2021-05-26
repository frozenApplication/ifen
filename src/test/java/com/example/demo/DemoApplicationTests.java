package com.example.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.demo.framework.jwt.contract.JWTContract;
import com.example.demo.framework.jwt.entity.JsonWebToken;
import com.example.demo.modules.entity.User;
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
    @Autowired
    JsonWebToken jsonWebToken;
    @Autowired
    JWTContract jwtContract;
    @Resource
    private UserMapper userMapper;

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
    void jwtCreatorTest() {
        System.out.println(jwtContract.encode(new HashMap<>() {{
            put("hello", "world");
        }}));
    }

    @Test
    void decode() {
        Map s = jwtContract.decode("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJBdXRoMCIsImhlbGxvIjoid29ybGQiLCJleHAiOjE2MjE3Njk0NjAsImlhdCI6MTYyMTc2OTQzMH0.2bp5fmD-m7xDwKmMxP1QYLLUNxO8J-9IeL38fYwVBNo");
        System.out.println(s);
    }


}
