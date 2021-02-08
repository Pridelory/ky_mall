package com.wangmeng.mall.api.service.impl;

import com.wangmeng.mall.api.dao.NewBeeMallBulletinMapper;
import com.wangmeng.mall.api.service.NewBeeMallBulletinService;
import com.wangmeng.mall.common.ServiceResultEnum;
import com.wangmeng.mall.common.api.original.PageResult;
import com.wangmeng.mall.entity.NewBeeMallBulletin;
import com.wangmeng.mall.entity.NewBeeMallGoods;
import com.wangmeng.mall.util.PageQueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @ClassName NewBeeMallBulletinServiceImpl
 * @Description TODO
 * @Author wangmeng
 * @Date 2021/2/8
 */
@Service
public class NewBeeMallBulletinServiceImpl implements NewBeeMallBulletinService{

    @Autowired
    private NewBeeMallBulletinMapper newBeeMallBulletinMapper;

    @Override
    public PageResult getNewBeeMallBulletinPage(PageQueryUtil pageUtil) {
        List<NewBeeMallBulletin> bulletinList = newBeeMallBulletinMapper.findNewBeeMallBulletinList(pageUtil);
        int total = newBeeMallBulletinMapper.getTotalNewBeeMallBulletins(pageUtil);
        PageResult pageResult = new PageResult(bulletinList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String saveNewBeeMallBulletin(NewBeeMallBulletin newBeeMallBulletin) {
        if (newBeeMallBulletinMapper.insertSelective(newBeeMallBulletin) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateNewBeeMallBulletin(NewBeeMallBulletin newBeeMallBulletin) {
        NewBeeMallBulletin temp = newBeeMallBulletinMapper.selectByPrimaryKey(newBeeMallBulletin.getId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        newBeeMallBulletin.setUpdateTime(new Date());
        if (newBeeMallBulletinMapper.updateByPrimaryKeySelective(newBeeMallBulletin) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public NewBeeMallBulletin getNewBeeMallBulletinById(Long id) {
        return newBeeMallBulletinMapper.selectByPrimaryKey(id);
    }
}
