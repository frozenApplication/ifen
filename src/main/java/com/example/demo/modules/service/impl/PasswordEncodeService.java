package com.example.demo.modules.service.impl;

import com.example.demo.modules.service.BaseEncode;
import org.springframework.util.DigestUtils;

public class PasswordEncodeService implements BaseEncode {

    @Override
    public String encode(String rawMessage) {
        String slat = "#%^secret@@@";
        String base = rawMessage + slat;
        return DigestUtils.md5DigestAsHex(base.getBytes());
    }
}
