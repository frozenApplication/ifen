package com.example.demo.modules.data;

import com.example.demo.modules.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class NotPasswordUser {
    private Integer id;
    private String mobile;
    private String username;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime created_at;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updated_at;

    public NotPasswordUser(User user) {
        this.id = user.getId();
        this.mobile = user.getMobile();
        this.username = user.getUsername();
        this.created_at = user.getCreatedAt();
        this.updated_at = user.getUpdatedAt();
    }

    @Override
    public String toString() {
        return "NotPasswordUser{" +
                "id=" + id +
                ", mobile='" + mobile + '\'' +
                ", username='" + username + '\'' +
                ", created_at=" + created_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                ", updated_at=" + updated_at.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) +
                '}';
    }
}
