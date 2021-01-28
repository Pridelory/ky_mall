package com.wangmeng.mall.api.controller;

import com.wangmeng.mall.api.model.vo.NewBeeMallGoodsDetailVO;
import com.wangmeng.mall.api.model.vo.NewBeeMallSearchGoodsVO;
import com.wangmeng.mall.common.api.original.PageResult;
import com.wangmeng.mall.common.api.original.Result;
import com.wangmeng.mall.common.api.original.ResultGenerator;
import com.wangmeng.mall.util.*;
import io.swagger.annotations.*;
import com.wangmeng.mall.common.Constants;
import com.wangmeng.mall.common.NewBeeMallException;
import com.wangmeng.mall.common.ServiceResultEnum;
import com.wangmeng.mall.config.annotation.TokenToMallUser;
import com.wangmeng.mall.entity.MallUser;
import com.wangmeng.mall.entity.NewBeeMallGoods;
import com.wangmeng.mall.api.service.NewBeeMallGoodsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api(value = "v1", tags = "4.新蜂商城商品相关接口")
@RequestMapping("/api/v1")
public class NewBeeMallGoodsAPI {

    private static final Logger logger = LoggerFactory.getLogger(NewBeeMallGoodsAPI.class);

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索接口", notes = "根据关键字和分类id进行搜索")
    public Result<PageResult<List<NewBeeMallSearchGoodsVO>>> search(@RequestParam(required = false) @ApiParam(value = "搜索关键字") String keyword,
                                                                    @RequestParam(required = false) @ApiParam(value = "分类id") List<Long> goodsCategoryParentIds,
                                                                    @RequestParam(required = false) @ApiParam(value = "orderBy") String orderBy,
                                                                    @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber) {
        
        logger.info("goods search api,keyword={},goodsCategoryId={},orderBy={},pageNumber={},userId={}", keyword, goodsCategoryParentIds, orderBy, pageNumber);

        Map params = new HashMap(4);
        //两个搜索参数都为空，直接返回异常
        if (goodsCategoryParentIds == null && StringUtils.isEmpty(keyword)) {
            NewBeeMallException.fail("非法的搜索参数");
        }
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("goodsCategoryParentIds", goodsCategoryParentIds);
        params.put("page", pageNumber);
        params.put("limit", Constants.GOODS_SEARCH_PAGE_LIMIT);
        //对keyword做过滤 去掉空格
        if (!StringUtils.isEmpty(keyword)) {
            params.put("keyword", keyword);
        }
        if (!StringUtils.isEmpty(orderBy)) {
            params.put("orderBy", orderBy);
        }
        //搜索上架状态下的商品
        params.put("goodsSellStatus", Constants.SELL_STATUS_UP);
        //封装商品数据
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.searchNewBeeMallGoods(pageUtil));
    }

    @GetMapping("/goods/detail/{goodsId}")
    @ApiOperation(value = "商品详情接口", notes = "传参为商品id")
    public Result<NewBeeMallGoodsDetailVO> goodsDetail(@ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId, @TokenToMallUser MallUser loginMallUser) {
        logger.info("goods detail api,goodsId={},userId={}", goodsId, loginMallUser.getUserId());
        if (goodsId < 1) {
            return ResultGenerator.genFailResult("参数异常");
        }
        NewBeeMallGoods goods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (goods == null) {
            return ResultGenerator.genFailResult("参数异常");
        }
        if (Constants.SELL_STATUS_UP != goods.getGoodsSellStatus()) {
            NewBeeMallException.fail(ServiceResultEnum.GOODS_PUT_DOWN.getResult());
        }
        NewBeeMallGoodsDetailVO goodsDetailVO = new NewBeeMallGoodsDetailVO();
        BeanUtil.copyProperties(goods, goodsDetailVO);
        goodsDetailVO.setGoodsCarouselList(goods.getGoodsCarousel().split(","));
        return ResultGenerator.genSuccessResult(goodsDetailVO);
    }
}
