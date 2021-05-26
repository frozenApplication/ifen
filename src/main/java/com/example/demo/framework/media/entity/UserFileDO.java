package com.example.demo.framework.media.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.jeffreyning.mybatisplus.anno.MppMultiId;
import lombok.Data;
@Data
@TableName("user_file")
public class UserFileDO {
    @MppMultiId
    @JsonIgnore
    private Integer userId; //用户id
    @MppMultiId
    @JsonIgnore
    private String filePath; //md5
    @MppMultiId
    private String fileName;    //用户提供的文件名

    public UserFileDO(Integer userId, String filePath, String fileName) {
        this.userId = userId;
        this.filePath = filePath;
        this.fileName = fileName;
    }
}
