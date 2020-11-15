/**
 *
 *
 *
 *
 *  (c)  reserved.
 *
 */
package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.IndexConfig;
import com.wangmeng.mall.util.PageQueryUtil;
import com.wangmeng.mall.util.PageResult;

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
