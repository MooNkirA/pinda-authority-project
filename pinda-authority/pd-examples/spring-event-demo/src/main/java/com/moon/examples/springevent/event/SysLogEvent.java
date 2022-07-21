package com.moon.examples.springevent.event;

import com.moon.examples.springevent.dto.LogDTO;
import org.springframework.context.ApplicationEvent;

/**
 * 自定义系统日志事件，需要继承 org.springframework.context.ApplicationEvent
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 16:32
 * @description
 */
public class SysLogEvent extends ApplicationEvent {

    public SysLogEvent(LogDTO source) {
        super(source);
    }

}
