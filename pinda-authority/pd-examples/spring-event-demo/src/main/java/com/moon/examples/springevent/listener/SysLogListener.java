package com.moon.examples.springevent.listener;

import com.moon.examples.springevent.dto.LogDTO;
import com.moon.examples.springevent.event.SysLogEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 事件监听器，监听日志事件
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 16:32
 * @description
 */
@Component
public class SysLogListener {

    @Async // 异步处理
    // @EventListener 标识当前方法为事件监听处理方法，并指定监听的事件类型
    @EventListener(SysLogEvent.class)
    public void saveLog(SysLogEvent event) {
        LogDTO logDTO = (LogDTO) event.getSource();
        long id = Thread.currentThread().getId();
        System.out.println("监听到日志操作事件：" + logDTO + " 线程id：" + id);
        // 其它处理逻辑，如将日志信息保存到数据库...
    }

}
