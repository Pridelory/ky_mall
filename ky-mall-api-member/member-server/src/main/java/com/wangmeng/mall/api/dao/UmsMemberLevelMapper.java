package com.wangmeng.mall.api.dao;

import com.wangmeng.mall.example.UmsMemberLevelExample;
import com.wangmeng.mall.model.UmsMemberLevel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UmsMemberLevelMapper {
    long countByExample(UmsMemberLevelExample example);

    int deleteByExample(UmsMemberLevelExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UmsMemberLevel record);

    int insertSelective(UmsMemberLevel record);

    List<UmsMemberLevel> selectByExample(UmsMemberLevelExample example);

    UmsMemberLevel selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UmsMemberLevel record, @Param("example") UmsMemberLevelExample example);

    int updateByExample(@Param("record") UmsMemberLevel record, @Param("example") UmsMemberLevelExample example);

    int updateByPrimaryKeySelective(UmsMemberLevel record);

    int updateByPrimaryKey(UmsMemberLevel record);
}