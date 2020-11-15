/**
 *
 *
 *
 *
 *  (c)  reserved.
 *
 */
package ltd.newbee.mall.service;

import ltd.newbee.mall.entity.GoodsCategory;
import com.wangmeng.mall.util.PageQueryUtil;
import com.wangmeng.mall.util.PageResult;

import java.util.List;

public interface NewBeeMallCategoryService {
    /**
     * 后台分页
     *
     * @param pageUtil
     * @return
     */
    PageResult getCategorisPage(PageQueryUtil pageUtil);

    String saveCategory(GoodsCategory goodsCategory);

    String updateGoodsCategory(GoodsCategory goodsCategory);

    GoodsCategory getGoodsCategoryById(Long id);

    Boolean deleteBatch(Integer[] ids);

    /**
     * 根据parentId和level获取分类列表
     *
     * @param parentIds
     * @param categoryLevel
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(List<Long> parentIds, int categoryLevel);
}
