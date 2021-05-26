package com.example.demo.framework.media.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.framework.media.entity.UserFileDO;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserFileMapper extends MppBaseMapper<UserFileDO> {

}
