package com.wangmeng.mall.api.model.dto;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;

/**
 * @ClassName OrderDTO
 * @Description TODO
 * @Author wangmeng
 * @Date 2021/1/4
 */
@Data
public class OrderDTO implements Serializable{

    /**
     * 商户号，自动填充，可不填
     */
    private Long mchid;

    /**
     * 订单总额
     */
    private Integer totalPrice;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 订单标题
     */
    private String body;

    /**
     * 自定义数据 在notify的时候回原样返回
     */
    private String attach;
}
