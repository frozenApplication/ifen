package com.example.demo.framework.jwt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.framework.jwt.entity.JsonWebToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface JsonWebTokenMapper extends BaseMapper<JsonWebToken> {
}
