package com.example.demo.modules.data;

import com.example.demo.framework.media.entity.UserFileDO;
import lombok.Data;

@Data
public class PureUserFile {
    String fileName;

    public PureUserFile(UserFileDO userFileDO) {
        this.fileName = userFileDO.getFileName();
    }

}
