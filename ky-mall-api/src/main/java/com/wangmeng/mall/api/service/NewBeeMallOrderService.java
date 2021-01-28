package com.wangmeng.mall.api.service;

import com.wangmeng.mall.api.model.vo.NewBeeMallOrderDetailVO;
import com.wangmeng.mall.api.model.vo.NewBeeMallShoppingCartItemVO;
import com.wangmeng.mall.entity.MallUser;
import com.wangmeng.mall.entity.MallUserAddress;
import com.wangmeng.mall.util.PageQueryUtil;
import com.wangmeng.mall.common.api.original.PageResult;

import java.util.List;

public interface NewBeeMallOrderService {
    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
    NewBeeMallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);

    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);

    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);

    String paySuccess(String orderNo, int payType);

    String saveOrder(MallUser loginMallUser, List<NewBeeMallShoppingCartItemVO> itemsForSave);
}
