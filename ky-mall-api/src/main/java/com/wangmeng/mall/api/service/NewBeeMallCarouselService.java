package com.wangmeng.mall.api.service;

import com.wangmeng.mall.api.model.vo.NewBeeMallIndexCarouselVO;
import java.util.List;

public interface NewBeeMallCarouselService {

    /**
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return
     */
    List<NewBeeMallIndexCarouselVO> getCarouselsForIndex(int number);
}
