package com.wangmeng.mall.api.controller;

import com.wangmeng.mall.api.model.vo.IndexInfoVO;
import com.wangmeng.mall.api.model.vo.NewBeeMallIndexCarouselVO;
import com.wangmeng.mall.api.service.NewBeeMallCarouselService;
import com.wangmeng.mall.common.Constants;
import com.wangmeng.mall.common.api.original.Result;
import com.wangmeng.mall.common.api.original.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @ClassName NewBeeMallCarouselAPI
 * @Description TODO
 * @Author wangmeng
 * @Date 2021/1/22
 */
@RestController
@Api(value = "v1", tags = "1.新蜂商城首页接口")
@RequestMapping("/api/v1")
public class NewBeeMallCarouselAPI {

    @Resource
    private NewBeeMallCarouselService newBeeMallCarouselService;

    /**
     * 获取轮播图
     * @return
     */
    @GetMapping("/carouse")
    @ApiOperation(value = "获取轮播图数据", notes = "轮播图")
    public Result<List<NewBeeMallIndexCarouselVO>> indexInfo() {
        List<NewBeeMallIndexCarouselVO> carousels = newBeeMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        return ResultGenerator.genSuccessResult(carousels);
    }
}

