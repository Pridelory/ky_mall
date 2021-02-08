package com.wangmeng.mall.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName NewBeeMallBulletin
 * @Description 公告栏
 * @Author wangmeng
 * @Date 2021/2/8
 */
@Data
public class NewBeeMallBulletin {

    private Long id;

    private String announce;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;
}
