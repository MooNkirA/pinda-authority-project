package com.moon.pinda.goods.config;

import com.moon.pinda.database.datasource.BaseMybatisConfiguration;
import com.moon.pinda.database.properties.DatabaseProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 配置一些拦截器
 */
@Configuration
public class GoodsServerMybatisAutoConfiguration extends BaseMybatisConfiguration {
    public GoodsServerMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }
}