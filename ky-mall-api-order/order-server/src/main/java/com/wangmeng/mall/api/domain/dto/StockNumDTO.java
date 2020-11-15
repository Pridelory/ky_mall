package com.wangmeng.mall.api.domain.dto;

import lombok.Data;

/**
 * 库存修改所需实体
 */

@Data
public class StockNumDTO {
    private Long goodsId;

    private Integer goodsCount;
}
