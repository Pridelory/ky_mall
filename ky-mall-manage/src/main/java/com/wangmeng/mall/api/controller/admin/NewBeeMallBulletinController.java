package com.wangmeng.mall.api.controller.admin;

import com.wangmeng.mall.api.service.NewBeeMallBulletinService;
import com.wangmeng.mall.common.NewBeeMallCategoryLevelEnum;
import com.wangmeng.mall.common.ServiceResultEnum;
import com.wangmeng.mall.common.api.original.Result;
import com.wangmeng.mall.common.api.original.ResultGenerator;
import com.wangmeng.mall.entity.GoodsCategory;
import com.wangmeng.mall.entity.NewBeeMallBulletin;
import com.wangmeng.mall.entity.NewBeeMallGoods;
import com.wangmeng.mall.util.PageQueryUtil;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @ClassName NewBeeMallBulletinController
 * @Description 公告栏
 * @Author wangmeng
 * @Date 2021/2/8
 */
@Controller
@RequestMapping("/admin")
public class NewBeeMallBulletinController {

    @Resource
    private NewBeeMallBulletinService newBeeMallBulletinService;

    @GetMapping("/bulletin")
    public String bulletinPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_bulletin");
        return "admin/newbee_mall_bulletin";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/bulletin/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallBulletinService.getNewBeeMallBulletinPage(pageUtil));
    }

    @GetMapping("/bulletin/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");

        request.setAttribute("path", "bulletin-edit");
        return "admin/newbee_mall_bulletin_edit";
//        return "error/error_5xx";
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/bulletin/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody NewBeeMallBulletin newBeeMallBulletin) {
        if (StringUtils.isEmpty(StringUtils.isEmpty(newBeeMallBulletin.getAnnounce()))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallBulletinService.saveNewBeeMallBulletin(newBeeMallBulletin);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/bulletin/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody NewBeeMallBulletin newBeeMallBulletin) {
        if (Objects.isNull(StringUtils.isEmpty(newBeeMallBulletin.getAnnounce()))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallBulletinService.updateNewBeeMallBulletin(newBeeMallBulletin);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    @GetMapping("/bulletin/edit/{id}")
    public String edit(HttpServletRequest request, @PathVariable("id") Long id) {
        request.setAttribute("path", "edit");
        NewBeeMallBulletin newBeeMallBulletin = newBeeMallBulletinService.getNewBeeMallBulletinById(id);
        if (newBeeMallBulletin == null) {
            return "error/error_400";
        }
        request.setAttribute("newBeeMallBulletin", newBeeMallBulletin);
        request.setAttribute("path", "goods-edit");
        return "admin/newbee_mall_bulletin_edit";
    }
}
