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
 * 商品查询 DTO
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-28 10:18
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class GoodsInfoPageDTO extends GoodsInfo {

    private static final long serialVersionUID = 8331978284010116196L;

    private LocalDateTime startCreateTime;
    private LocalDateTime endCreateTime;

}
