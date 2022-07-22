package com.moon.examples.tools.config;

import com.moon.examples.tools.service.LogService;
import com.moon.pinda.log.event.SysLogListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志功能配置类
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 10:37
 * @description
 */
@Configuration
public class LogAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean // 当容器中不存在 SysLogListener 时才生成实例
    public SysLogListener sysLogListener(LogService logService) {
        // 创建 SysLogListener 对象，传入具体日志实现的 Consumer 接口
        return new SysLogListener((optLogDTO) -> logService.saveLog(optLogDTO));
    }

}
