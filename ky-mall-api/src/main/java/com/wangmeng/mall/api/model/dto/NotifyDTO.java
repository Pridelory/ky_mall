package com.wangmeng.mall.api.model.dto;

import lombok.Data;

/**
 * NotifyDTO
 *
 * @Author wangmeng
 * @create 2021/01/11
 */
@Data
public class NotifyDTO {

    /**
     * 返回码
     *
     * 是否支付成功
     */
    private String return_code;
    /**
     * 金额 单位：分
     */
    private String total_fee;

    /**
     * 用户端自主生成的订单号
     */
    private String out_trade_no;

    /**
     * PAYJS 侧订单号
     */
    private String payjs_order_id;

    /**
     * 微信用户手机显示订单号
     */
    private String transaction_id;

    /**
     * 支付成功时间
     */
    private String time_end;

    /**
     * openid
     */
    private String openid;

    /**
     * 用户自定义数据
     */
    private String attach;

    /**
     * 商户号
     */
    private String mchid;

    /**
     * 支付类型。微信订单不返回该字段；支付宝订单返回：alipay
     */
    private String type;

    /**
     * 数据签名
     */
    private String sign;
}
