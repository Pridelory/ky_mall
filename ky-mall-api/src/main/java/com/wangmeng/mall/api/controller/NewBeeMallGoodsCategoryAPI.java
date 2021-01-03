package com.wangmeng.mall.api.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.wangmeng.mall.common.NewBeeMallException;
import com.wangmeng.mall.common.ServiceResultEnum;
import com.wangmeng.mall.api.model.vo.NewBeeMallIndexCategoryVO;
import com.wangmeng.mall.api.service.NewBeeMallCategoryService;
import com.wangmeng.mall.common.api.original.Result;
import com.wangmeng.mall.common.api.original.ResultGenerator;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "3.新蜂商城分类页面接口")
@RequestMapping("/api/v1")
public class NewBeeMallGoodsCategoryAPI {

    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "获取分类数据", notes = "分类页面使用")
    public Result<List<NewBeeMallIndexCategoryVO>> getCategories() {
        List<NewBeeMallIndexCategoryVO> categories = newBeeMallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            NewBeeMallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(categories);
    }
}
