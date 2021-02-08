package com.wangmeng.mall.api.controller;
import com.wangmeng.mall.api.model.entity.Sms;
import com.wangmeng.mall.api.service.SmsService;
import com.wangmeng.mall.util.BeanUtil;
import com.wangmeng.mall.util.NumberUtil;
import com.wangmeng.mall.common.api.original.Result;
import com.wangmeng.mall.common.api.original.ResultGenerator;
import io.swagger.annotations.*;
import com.wangmeng.mall.api.model.param.MallUserLoginParam;
import com.wangmeng.mall.api.model.param.MallUserRegisterParam;
import com.wangmeng.mall.api.model.param.MallUserUpdateParam;
import com.wangmeng.mall.common.Constants;
import com.wangmeng.mall.common.ServiceResultEnum;
import com.wangmeng.mall.config.annotation.TokenToMallUser;
import com.wangmeng.mall.api.model.vo.NewBeeMallUserVO;
import com.wangmeng.mall.entity.MallUser;
import com.wangmeng.mall.api.service.NewBeeMallUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
@Api(value = "v1", tags = "2.新蜂商城用户操作相关接口")
@RequestMapping("/api/v1")
public class NewBeeMallPersonalAPI {

    @Resource
    private NewBeeMallUserService newBeeMallUserService;

    @Resource
    private SmsService smsService;


    private static final Logger logger = LoggerFactory.getLogger(NewBeeMallPersonalAPI.class);

    @PostMapping("/user/login")
    @ApiOperation(value = "登录接口", notes = "返回token")
    public Result<String> login(@RequestBody @Valid MallUserLoginParam mallUserLoginParam) {
        if (!NumberUtil.isPhone(mallUserLoginParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String loginResult = newBeeMallUserService.login(mallUserLoginParam.getLoginName(), mallUserLoginParam.getPasswordMd5());

        logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);

        //登录成功
        if (!StringUtils.isEmpty(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }


    @PostMapping("/user/logout")
    @ApiOperation(value = "登出接口", notes = "清除token")
    public Result<String> logout(@TokenToMallUser MallUser loginMallUser) {
        Boolean logoutResult = newBeeMallUserService.logout(loginMallUser.getUserId());
        logger.info("logout api,loginMallUser={}", loginMallUser.getUserId());
        Sms sms = new Sms();
        sms.setOrderNumber("asdfsdf");
        smsService.sendSmsToCustomerService(sms);
        //登出成功
        if (logoutResult) {
            return ResultGenerator.genSuccessResult();
        }
        //登出失败
        return ResultGenerator.genFailResult("logout error");
    }


    @PostMapping("/user/register")
    @ApiOperation(value = "用户注册", notes = "")
    public Result register(@RequestBody @Valid MallUserRegisterParam mallUserRegisterParam) {
        if (!NumberUtil.isPhone(mallUserRegisterParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String registerResult = newBeeMallUserService.register(mallUserRegisterParam.getLoginName(), mallUserRegisterParam.getPassword());

        logger.info("register api,loginName={},loginResult={}", mallUserRegisterParam.getLoginName(), registerResult);

        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    @PutMapping("/user/info")
    @ApiOperation(value = "修改用户信息", notes = "")
    public Result updateInfo(@RequestBody @ApiParam("用户信息") MallUserUpdateParam mallUserUpdateParam, @TokenToMallUser MallUser loginMallUser) {
        Boolean flag = newBeeMallUserService.updateUserInfo(mallUserUpdateParam, loginMallUser.getUserId());
        if (flag) {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        } else {
            //返回失败
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        }
    }

    @GetMapping("/user/info")
    @ApiOperation(value = "获取用户信息", notes = "")
    public Result<NewBeeMallUserVO> getUserDetail(@TokenToMallUser MallUser loginMallUser) {
        //已登录则直接返回
        NewBeeMallUserVO mallUserVO = new NewBeeMallUserVO();
        BeanUtil.copyProperties(loginMallUser, mallUserVO);
        return ResultGenerator.genSuccessResult(mallUserVO);
    }
}
