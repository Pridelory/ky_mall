package com.wangmeng.mall.api.service;

import com.wangmeng.mall.api.model.entity.Sms;

/**
 * @ClassName SmsService
 * @Description 短信服务
 * @Author wangmeng
 * @Date 2021/1/29
 */
public interface SmsService {

    /**
     * 短信发送公共模板（单条）
     * @param formData
     * @return
     */
    String seneSms(Object formData);

    /**
     * 短信群发公共模板
     * @param formData
     * @return
     */
    String sendSmsBatch(Object formData);

    /**
     * 客服短信服务
     * @return
     */
    String sendSmsToCustomerService(Sms sms);
}
