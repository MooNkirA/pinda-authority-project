package com.moon.pinda.goods.dto;

import com.moon.pinda.goods.entity.GoodsInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 商品保存 DTO
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-28 10:18
 * @description
 */
public class GoodsInfoSaveDTO extends GoodsInfo {
    private static final long serialVersionUID = 4134977371867158103L;
}
