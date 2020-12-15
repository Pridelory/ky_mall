package com.wangmeng.mall.api.service;

import com.wangmeng.mall.entity.Carousel;
import com.wangmeng.mall.util.PageQueryUtil;
import com.wangmeng.mall.common.api.original.PageResult;

public interface NewBeeMallCarouselService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCarouselPage(PageQueryUtil pageUtil);

    String saveCarousel(Carousel carousel);

    String updateCarousel(Carousel carousel);

    Carousel getCarouselById(Integer id);

    Boolean deleteBatch(Integer[] ids);
}
