package com.wangmeng.mall.api.dao;

import com.wangmeng.mall.entity.NewBeeMallBulletin;
import com.wangmeng.mall.entity.NewBeeMallGoods;
import com.wangmeng.mall.util.PageQueryUtil;

import java.util.List;

/**
 * @ClassName NewBeeMallBulletinMapper
 * @Description 公告栏Mapper
 * @Author wangmeng
 * @Date 2021/2/8
 */
public interface NewBeeMallBulletinMapper {

    int getTotalNewBeeMallBulletins(PageQueryUtil pageUtil);

    List<NewBeeMallBulletin> findNewBeeMallBulletinList(PageQueryUtil pageUtil);

    int insertSelective(NewBeeMallBulletin record);

    NewBeeMallBulletin selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(NewBeeMallBulletin record);
}
