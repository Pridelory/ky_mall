/**
 *
 *
 *
 *
 *  (c)  reserved.
 *
 */
package com.wangmeng.mall.api.service;

import com.wangmeng.mall.util.PageQueryUtil;
import com.wangmeng.mall.common.api.original.PageResult;

public interface NewBeeMallUserService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Integer[] ids, int lockStatus);
}
