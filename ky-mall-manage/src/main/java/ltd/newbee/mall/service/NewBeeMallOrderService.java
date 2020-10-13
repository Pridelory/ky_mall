package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.NewBeeMallOrder;
import ltd.newbee.mall.entity.NewBeeMallOrderItem;
import ltd.newbee.mall.util.PageQueryUtil;
import ltd.newbee.mall.util.PageResult;

import java.util.List;

public interface NewBeeMallOrderService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallOrdersPage(PageQueryUtil pageUtil);

    /**
     * 订单信息修改
     *
     * @param newBeeMallOrder
     * @return
     */
    String updateOrderInfo(NewBeeMallOrder newBeeMallOrder);

    /**
     * 配货
     *
     * @param ids
     * @return
     */
    String checkDone(Long[] ids);

    /**
     * 出库
     *
     * @param ids
     * @return
     */
    String checkOut(Long[] ids);

    /**
     * 关闭订单
     *
     * @param ids
     * @return
     */
    String closeOrder(Long[] ids);

    List<NewBeeMallOrderItem> getOrderItems(Long id);
}
