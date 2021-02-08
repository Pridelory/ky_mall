package com.wangmeng.mall.api.controller;

import com.wangmeng.mall.api.config.PayjsConfig;
import com.wangmeng.mall.api.model.dto.NotifyDTO;
import com.wangmeng.mall.api.model.dto.OrderDTO;
import com.wangmeng.mall.api.model.entity.Sms;
import com.wangmeng.mall.api.service.SmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import com.wangmeng.mall.api.model.param.SaveOrderParam;
import com.wangmeng.mall.api.model.vo.NewBeeMallOrderDetailVO;
import com.wangmeng.mall.api.model.vo.NewBeeMallOrderListVO;
import com.wangmeng.mall.common.Constants;
import com.wangmeng.mall.common.NewBeeMallException;
import com.wangmeng.mall.common.ServiceResultEnum;
import com.wangmeng.mall.config.annotation.TokenToMallUser;
import com.wangmeng.mall.api.model.vo.NewBeeMallShoppingCartItemVO;
import com.wangmeng.mall.entity.MallUser;
import com.wangmeng.mall.entity.MallUserAddress;
import com.wangmeng.mall.api.service.NewBeeMallOrderService;
import com.wangmeng.mall.api.service.NewBeeMallShoppingCartService;
import com.wangmeng.mall.api.service.NewBeeMallUserAddressService;
import com.wangmeng.mall.util.PageQueryUtil;
import com.wangmeng.mall.common.api.original.PageResult;
import com.wangmeng.mall.common.api.original.Result;
import com.wangmeng.mall.common.api.original.ResultGenerator;
import jdk.nashorn.internal.scripts.JS;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wangmeng.mall.util.SignUtil.sign;

@RestController
@Api(value = "v1", tags = "7.新蜂商城订单操作相关接口")
@RequestMapping("/api/v1")
public class NewBeeMallOrderAPI {

    @Autowired
    private NewBeeMallShoppingCartService newBeeMallShoppingCartService;
    @Autowired
    private NewBeeMallOrderService newBeeMallOrderService;
    @Autowired
    private NewBeeMallUserAddressService newBeeMallUserAddressService;
    @Autowired
    private SmsService smsService;

    @PostMapping("/saveOrder")
    @ApiOperation(value = "生成订单接口", notes = "传参为地址id和待结算的购物项id数组")
    public Result<String> saveOrder(@ApiParam(value = "订单参数") @RequestBody SaveOrderParam saveOrderParam, @TokenToMallUser MallUser loginMallUser) {
        int priceTotal = 0;
        if (saveOrderParam == null || saveOrderParam.getCartItemIds() == null) {
            NewBeeMallException.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        if (saveOrderParam.getCartItemIds().length < 1) {
            NewBeeMallException.fail(ServiceResultEnum.PARAM_ERROR.getResult());
        }
        List<NewBeeMallShoppingCartItemVO> itemsForSave = newBeeMallShoppingCartService.getCartItemsForSettle(Arrays.asList(saveOrderParam.getCartItemIds()), loginMallUser.getUserId());
        if (CollectionUtils.isEmpty(itemsForSave)) {
            //无数据
            NewBeeMallException.fail("参数异常");
        } else {
            //总价
            for (NewBeeMallShoppingCartItemVO newBeeMallShoppingCartItemVO : itemsForSave) {
                priceTotal += newBeeMallShoppingCartItemVO.getGoodsCount() * newBeeMallShoppingCartItemVO.getSellingPrice();
            }
            if (priceTotal < 1) {
                NewBeeMallException.fail("价格异常");
            }
//            MallUserAddress address = newBeeMallUserAddressService.getMallUserAddressById(saveOrderParam.getAddressId());
//            if (!loginMallUser.getUserId().equals(address.getUserId())) {
//                return ResultGenerator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
//            }
            //保存订单并返回订单号
            String saveOrderResult = newBeeMallOrderService.saveOrder(loginMallUser, itemsForSave);
            Result result = ResultGenerator.genSuccessResult();
            result.setData(saveOrderResult);
            return result;
        }
        return ResultGenerator.genFailResult("生成订单失败");
    }

    @GetMapping("/order/{orderNo}")
    @ApiOperation(value = "订单详情接口", notes = "传参为订单号")
    public Result<NewBeeMallOrderDetailVO> orderDetailPage(@ApiParam(value = "订单号") @PathVariable("orderNo") String orderNo, @TokenToMallUser MallUser loginMallUser) {
        return ResultGenerator.genSuccessResult(newBeeMallOrderService.getOrderDetailByOrderNo(orderNo, loginMallUser.getUserId()));
    }

    @GetMapping("/order")
    @ApiOperation(value = "订单列表接口", notes = "传参为页码")
    public Result<PageResult<List<NewBeeMallOrderListVO>>> orderList(@ApiParam(value = "页码") @RequestParam(required = false) Integer pageNumber,
                            @ApiParam(value = "订单状态:0.待支付 1.待确认 2.待发货 3:已发货 4.交易成功") @RequestParam(required = false) Integer status,
                            @TokenToMallUser MallUser loginMallUser) {
        Map params = new HashMap(4);
        if (pageNumber == null || pageNumber < 1) {
            pageNumber = 1;
        }
        params.put("userId", loginMallUser.getUserId());
        params.put("orderStatus", status);
        params.put("page", pageNumber);
        params.put("limit", Constants.ORDER_SEARCH_PAGE_LIMIT);
        //封装分页请求参数
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallOrderService.getMyOrders(pageUtil));
    }

    @PutMapping("/order/{orderNo}/cancel")
    @ApiOperation(value = "订单取消接口", notes = "传参为订单号")
    public Result cancelOrder(@ApiParam(value = "订单号") @PathVariable("orderNo") String orderNo, @TokenToMallUser MallUser loginMallUser) {
        String cancelOrderResult = newBeeMallOrderService.cancelOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(cancelOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(cancelOrderResult);
        }
    }

    @PutMapping("/order/{orderNo}/finish")
    @ApiOperation(value = "确认收货接口", notes = "传参为订单号")
    public Result finishOrder(@ApiParam(value = "订单号") @PathVariable("orderNo") String orderNo, @TokenToMallUser MallUser loginMallUser) {
        String finishOrderResult = newBeeMallOrderService.finishOrder(orderNo, loginMallUser.getUserId());
        if (ServiceResultEnum.SUCCESS.getResult().equals(finishOrderResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(finishOrderResult);
        }
    }

    @GetMapping("/paySuccess")
    @ApiOperation(value = "支付成功回调接口", notes = "传参为订单号和支付方式")
    public Result paySuccess(@ApiParam(value = "订单号") @RequestParam("orderNo") String orderNo, @ApiParam(value = "支付方式") @RequestParam("payType") int payType) {
        String payResult = newBeeMallOrderService.paySuccess(orderNo, payType);
        if (ServiceResultEnum.SUCCESS.getResult().equals(payResult)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(payResult);
        }
    }

    /**
     * 用户支付成功回调接口
     * @param notifyDTO
     * @return
     */
    @PostMapping("/paySuccessNotify")
    @ApiOperation(value = "支付成功回调接口", notes = "传参为订单号和支付方式")
    public Object paySuccessNotify(NotifyDTO notifyDTO) {
        // 验签 以防客户端篡改伪造
        Map<String,String> notifyData = new HashMap<>();
        notifyData.put("return_code",notifyDTO.getReturn_code());
        notifyData.put("total_fee",notifyDTO.getTotal_fee());
        notifyData.put("out_trade_no",notifyDTO.getOut_trade_no());
        notifyData.put("payjs_order_id",notifyDTO.getPayjs_order_id());
        notifyData.put("transaction_id",notifyDTO.getTransaction_id());
        notifyData.put("time_end",notifyDTO.getTime_end());
        notifyData.put("openid",notifyDTO.getOpenid());
        notifyData.put("attach",notifyDTO.getAttach());
        notifyData.put("mchid",notifyDTO.getMchid());
        String sign = sign(notifyData, PayjsConfig.key);
        if (sign.equals(notifyDTO.getSign())) {
            String orderNo = notifyDTO.getOut_trade_no();
            // 微信支付
            int payType = 2;
            newBeeMallOrderService.paySuccess(orderNo, payType);
            Sms sms = new Sms();
            sms.setOrderNumber(orderNo);
            smsService.sendSmsToCustomerService(sms);
            return "success";
        }
        return "failure";
    }

}
