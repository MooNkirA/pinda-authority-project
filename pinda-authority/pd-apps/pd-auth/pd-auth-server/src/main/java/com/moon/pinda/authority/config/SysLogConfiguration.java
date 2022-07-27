package com.moon.pinda.authority.config;

import com.moon.pinda.authority.biz.service.common.OptLogService;
import com.moon.pinda.log.event.SysLogListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 系统操作日志配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-26 16:24
 * @description
 */
@EnableAsync // 开启异步支持
@Configuration
public class SysLogConfiguration {

    // 创建日志记录监听器对象
    @Bean
    public SysLogListener sysLogListener(OptLogService optLogService) {
        // Consumer<OptLogDTO> consumer = (optLog) -> optLogService.save(optLog);
        return new SysLogListener(optLogService::save);
    }

}
