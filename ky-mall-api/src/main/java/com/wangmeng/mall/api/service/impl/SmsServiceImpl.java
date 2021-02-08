package com.wangmeng.mall.api.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.wangmeng.mall.api.model.entity.Sms;
import com.wangmeng.mall.api.service.SmsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SmsServiceImpl
 * @Description 短信验证码
 * @Author wangmeng
 * @Date 2021/1/29
 */
@Service
public class SmsServiceImpl implements SmsService {

    @Value("${sms.phone.customerService1}")
    private String phoneOfCustomerService1;

    @Value("${sms.phone.customerService2}")
    private String phoneOfCustomerService2;

    @Value("${sms.template.customer}")
    private String smsCustomerTemplate;

    @Value("${sms.template.customerService}")
    private String smsCustomerServiceTemplate;

    @Value("${sms.api.key}")
    private String apiKey;

    @Value("${sms.api.url}")
    private String apiUrl;

    @Value("${sms.api.url.batch}")
    private String apiUrlBatch;



    @Override
    public String seneSms(Object formData) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", apiKey));
        WebResource webResource = client.resource(apiUrl);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        return textEntity;
    }

    @Override
    public String sendSmsBatch(Object formData) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter(
                "api", apiKey));
        WebResource webResource = client.resource(apiUrlBatch);
        ClientResponse response = webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
        String textEntity = response.getEntity(String.class);
        int status = response.getStatus();
        return textEntity;
    }

    @Override
    public String sendSmsToCustomerService(Sms sms) {
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        String text = smsCustomerServiceTemplate.replaceAll("###", sms.getOrderNumber());
        List<String> phoneList = new ArrayList<>();
        phoneList.add(phoneOfCustomerService1);
        phoneList.add(phoneOfCustomerService2);
        String mobileBatch = StringUtils.join(phoneList.toArray(), ",");
        formData.add("mobile_list", mobileBatch);
        formData.add("message", text);
        String textEntity = sendSmsBatch(formData);
        System.out.println(sms.getOrderNumber());
        System.out.println(text);
        return textEntity;
    }
}
