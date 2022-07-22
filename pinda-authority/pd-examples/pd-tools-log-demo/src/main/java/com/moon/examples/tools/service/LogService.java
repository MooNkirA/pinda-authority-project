package com.moon.examples.tools.service;

import com.moon.pinda.log.entity.OptLogDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 日志功能具体实现
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 10:35
 * @description
 */
@Service
@Slf4j
public class LogService {

    // 将日志信息保存到数据库
    public void saveLog(OptLogDTO optLogDTO) {
        // 此处只是将日志信息进行输出，实际项目中可以将日志信息保存到数据库
        log.debug("保存日志信息：" + optLogDTO);
    }

}
