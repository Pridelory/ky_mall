/**
 *
 *
 *
 *
 *  (c)  reserved.
 *
 */
package com.wangmeng.mall.api.service;

import com.wangmeng.mall.entity.IndexConfig;
import com.wangmeng.mall.util.PageQueryUtil;
import com.wangmeng.mall.common.api.original.PageResult;

public interface NewBeeMallIndexConfigService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getConfigsPage(PageQueryUtil pageUtil);

    String saveIndexConfig(IndexConfig indexConfig);

    String updateIndexConfig(IndexConfig indexConfig);

    IndexConfig getIndexConfigById(Long id);

    Boolean deleteBatch(Long[] ids);
}
