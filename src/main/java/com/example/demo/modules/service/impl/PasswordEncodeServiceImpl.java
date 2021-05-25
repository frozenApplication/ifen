package com.example.demo.modules.service.impl;

import com.example.demo.modules.service.EncodeService;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

@Component
public class PasswordEncodeServiceImpl implements EncodeService {

    @Override
    public String encode(String rawMessage) {
        String slat = "#%^secret@@@";
        String base = rawMessage + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
