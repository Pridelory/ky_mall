package com.wangmeng.mall.api.service;

import com.wangmeng.mall.common.api.original.PageResult;
import com.wangmeng.mall.entity.NewBeeMallBulletin;
import com.wangmeng.mall.entity.NewBeeMallGoods;
import com.wangmeng.mall.util.PageQueryUtil;

/**
 * @ClassName NewBeeMallBulletinService
 * @Description TODO
 * @Author wangmeng
 * @Date 2021/2/8
 */
public interface NewBeeMallBulletinService {

    /**
     * 后台公告栏分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallBulletinPage(PageQueryUtil pageUtil);

    /**
     * 添加公告
     *
     * @param newBeeMallBulletin
     * @return
     */
    String saveNewBeeMallBulletin(NewBeeMallBulletin newBeeMallBulletin);

    /**
     * 修改公告信息
     *
     * @param newBeeMallBulletin
     * @return
     */
    String updateNewBeeMallBulletin(NewBeeMallBulletin newBeeMallBulletin);

    /**
     * 获取公告详情
     *
     * @param id
     * @return
     */
    NewBeeMallBulletin getNewBeeMallBulletinById(Long id);
}
