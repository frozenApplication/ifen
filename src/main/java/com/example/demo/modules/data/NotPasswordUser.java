package com.example.demo.modules.data;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.demo.modules.entity.User;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class NotPasswordUser {
    private Integer id;
    private String mobile;
    private String username;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public NotPasswordUser(User user) {
        this.id = user.getId();
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.created_at = user.getCreated_at();
        this.updated_at = user.getUpdated_at();
    }
}
