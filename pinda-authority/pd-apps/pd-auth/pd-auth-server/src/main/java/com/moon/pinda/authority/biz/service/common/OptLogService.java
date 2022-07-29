package com.moon.pinda.authority.biz.service.common;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moon.pinda.authority.entity.common.OptLog;
import com.moon.pinda.log.entity.OptLogDTO;

/**
 * 操作日志业务接口
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 16:17
 * @description
 */
public interface OptLogService extends IService<OptLog> {

    /**
     * 保存操作日志。
     * 注：与 Mybatis-plus 提供的 save 方法的形参不一样
     *
     * @param entity
     * @return
     */
    boolean save(OptLogDTO entity);

}
