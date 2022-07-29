package com.moon.pinda.authority.biz.service.common.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.moon.pinda.authority.biz.dao.common.OptLogMapper;
import com.moon.pinda.authority.biz.service.common.OptLogService;
import com.moon.pinda.authority.entity.common.OptLog;
import com.moon.pinda.dozer.DozerUtils;
import com.moon.pinda.log.entity.OptLogDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志业务接口实现
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 16:19
 * @description
 */
@Service
public class OptLogServiceImpl extends ServiceImpl<OptLogMapper, OptLog> implements OptLogService {

    @Autowired
    DozerUtils dozerUtils;

    /**
     * 保存操作日志。
     * 注：与 Mybatis-plus 提供的 save 方法的形参不一样
     *
     * @param entity
     * @return
     */
    @Override
    public boolean save(OptLogDTO entity) {
        return super.save(dozerUtils.map(entity, OptLog.class));
    }

}
