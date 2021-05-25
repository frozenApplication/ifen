package com.example.demo.framework.media.service;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WebFileService {
    void upload(MultipartFile file, Integer id) throws IOException;

    void download(HttpServletResponse response, String filename, Integer userId) throws IOException;
}
