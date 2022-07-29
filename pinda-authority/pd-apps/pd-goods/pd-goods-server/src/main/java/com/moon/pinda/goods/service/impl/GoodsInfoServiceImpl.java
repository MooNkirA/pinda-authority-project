package com.moon.pinda.goods.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.pinda.goods.dao.GoodsInfoMapper;
import com.moon.pinda.goods.entity.GoodsInfo;
import com.moon.pinda.goods.service.GoodsInfoService;
import org.springframework.stereotype.Service;

/**
 * 商品业务接口实现
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-28 10:51
 * @description
 */
@Service
public class GoodsInfoServiceImpl extends ServiceImpl<GoodsInfoMapper, GoodsInfo> implements GoodsInfoService {
}
