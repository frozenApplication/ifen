package com.example.demo.framework.media.storage;

import java.io.File;
import java.io.IOException;

public class FileStorage {


    public static String getPath() throws IOException {
        File directory = new File("");//参数为空
        String courseFile = directory.getCanonicalPath();
        File file1 = new File(courseFile + "/file_path/");
        String filePath = file1.getPath();
        file1.mkdir();
        return filePath;
    }
}

