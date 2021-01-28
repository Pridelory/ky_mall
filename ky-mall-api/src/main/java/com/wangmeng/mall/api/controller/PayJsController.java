package com.wangmeng.mall.api.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.wangmeng.mall.api.config.PayjsConfig;
import com.wangmeng.mall.api.model.entity.*;
import com.wangmeng.mall.common.api.original.Result;
import com.wangmeng.mall.common.api.original.ResultGenerator;
import com.wangmeng.mall.common.exception.PayJsException;
import com.wangmeng.mall.util.HttpsUtils;
import com.wangmeng.mall.util.SignUtil;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xuzhiguang
 * @date 2018/12/19
 */
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class PayJsController {

    private final MediaType MediaTypeJson = MediaType.parse("application/json; charset=utf-8");

    /**
     * 域名
     */
    private final String host = "https://payjs.cn";

    /**
     * 商户号
     */
    @Value("${jsapi.mchid}")
    private String mchid;

    /**
     * 秘钥
     */
    @Value("${jsapi.key}")
    private String key;

    /**
     * 连接超时时间
     */
    private long connectTimeout = 3000L;

    /**
     * 读取超时时间
     */
    private long readTimeout = 5000L;


//    /**
//     * 获取回调数据
//     * 官方文档<a>https://help.payjs.cn/api-lie-biao/jiao-yi-xin-xi-tui-song.html</a>
//     * @param response 回调数据
//     * @return 回调数据
//     * @throws PayJsException 异常
//     */
//    public NotifyResponse notify(NotifyResponse response) throws PayJsException {
//        String sign = response.getSign();
//        response.setSign(null);
//        String sign1 = SignUtil.getSign(response, this.key);
//        if (!sign.equals(sign1)) {
//            throw new PayJsException("签名不匹配");
//        }
//        return response;
//    }


    /**
     * 获取jsapi所需参数
     * 官方文档<a>https://help.payjs.cn/api-lie-biao/jsapiyuan-sheng-zhi-fu.html</a>
     * @param request 请求数据
     * @return 返回数据
     * @throws PayJsException 异常
     */
    @RequestMapping("/pay")
    public Result<String> jsapi(JsapiRequest request) throws PayJsException, IOException {
        final String url = this.host + "/api/jsapi";

        Map<String,String> payData = new HashMap<>();
        payData.put("mchid", mchid);
        payData.put("total_fee", request.getTotalFee().toString());
        payData.put("out_trade_no", request.getOutTradeNo()); // 订单号 随便输的，自己生成一下就好了
        payData.put("openid", request.getOpenid()); // 根据openid接口获取到的openid
        payData.put("body", request.getBody());
        payData.put("attach","测试自定义数据");
        payData.put("notify_url", request.getNotifyUrl());
        payData.put("callback_url", request.getCallbackUrl());
        payData.put("sign", SignUtil.sign(payData, key));
        // 请求payjs
        String response = HttpsUtils.sendPost(PayjsConfig.jsapiUrl, JSON.toJSONString(payData),null);
        Result result = ResultGenerator.genSuccessResult();
        result.setData(response);
        return result;
    }

    /**
     * 发送请求
     * @param url 请求地址
     * @param requestObj 请求数据对象
     * @param tClass 返回对象类
     * @param <T> payjs返回数据
     * @return payjs返回数据
     * @throws PayJsException 异常
     */
    private <T extends PayJsResponse> T doRequest(String url, Object requestObj, Class<T> tClass) throws PayJsException {

        SerializeConfig config = new SerializeConfig();
        config.propertyNamingStrategy = PropertyNamingStrategy.SnakeCase;
        String jsonString = JSONObject.toJSONString(requestObj, config);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(this.connectTimeout, TimeUnit.MILLISECONDS)
                .readTimeout(this.readTimeout, TimeUnit.MILLISECONDS)
                .build();

        RequestBody body = RequestBody.create(MediaTypeJson, jsonString);
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            log.error("发送请求出错：{}", e);
            throw new PayJsException("发送请求错误:" + e.getMessage());
        }

        String responseBody;
        try {
            responseBody = response.body().string();
        } catch (IOException e) {
            log.error("解析返回数据出错：{}", e);
            throw new PayJsException("解析返回数据出错:" + e.getMessage());
        }

        return JSON.parseObject(responseBody, tClass);
    }
}
