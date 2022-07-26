package com.moon.pinda.authority.config;

import com.moon.pinda.database.datasource.BaseMybatisConfiguration;
import com.moon.pinda.database.properties.DatabaseProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 和 mybatis 框架相关的配置
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-25 13:55
 * @description
 */
@Configuration
public class AuthorityMybatisAutoConfiguration extends BaseMybatisConfiguration {

    public AuthorityMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }

}