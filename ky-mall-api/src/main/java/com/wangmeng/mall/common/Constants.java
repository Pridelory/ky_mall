/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2020 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.wangmeng.mall.common;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/wangmeng-com
 * @apiNote 常量配置
 */
public class Constants {
    //public final static String FILE_UPLOAD_DIC = "/opt/wangmeng/upload/";//上传文件的默认url前缀，根据部署设置自行修改
    public final static String FILE_UPLOAD_DIC = "D:\\upload\\";//上传文件的默认url前缀，根据部署设置自行修改

    public final static int INDEX_CAROUSEL_NUMBER = 5;//首页轮播图数量(可根据自身需求修改)

    public final static int INDEX_CATEGORY_NUMBER = 10;//首页一级分类的最大数量

    public final static int SEARCH_CATEGORY_NUMBER = 8;//搜索页一级分类的最大数量

    public final static int INDEX_GOODS_HOT_NUMBER = 20;//首页热卖商品数量
    public final static int INDEX_GOODS_NEW_NUMBER = 5;//首页新品数量
    public final static int INDEX_GOODS_RECOMMOND_NUMBER = 10;//首页推荐商品数量

    public final static int SHOPPING_CART_ITEM_TOTAL_NUMBER = 20;//购物车中商品的最大数量(可根据自身需求修改)

    public final static int SHOPPING_CART_ITEM_LIMIT_NUMBER = 5;//购物车中单个商品的最大购买数量(可根据自身需求修改)

    public final static String MALL_USER_SESSION_KEY = "newBeeMallUser";//session中user的key

    public final static int GOODS_SEARCH_PAGE_LIMIT = 10;//搜索分页的默认条数(每页10条)

    public final static int SHOPPING_CART_PAGE_LIMIT = 5;//购物车分页的默认条数(每页5条)

    public final static int USER_ADDRESS_PAGE_LIMIT = 10;//购物车分页的默认条数(每页10条)

    public final static int ORDER_SEARCH_PAGE_LIMIT = 5;//我的订单列表分页的默认条数(每页5条)

    public final static int SELL_STATUS_UP = 0;//商品上架状态
    public final static int SELL_STATUS_DOWN = 1;//商品下架状态

    public final static int TOKEN_LENGTH = 32;//token字段长度

    public final static String USER_INTRO = "越努力越幸运";//默认简介
}
