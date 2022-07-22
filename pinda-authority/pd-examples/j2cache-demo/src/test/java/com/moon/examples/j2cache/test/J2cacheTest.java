package com.moon.examples.j2cache.test;

import net.oschina.j2cache.CacheChannel;
import net.oschina.j2cache.CacheObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

/**
 * j2cache 基础使用示例
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-22 20:00
 * @description
 */
@SpringBootTest
public class J2cacheTest {

    private final String key = "myKey";
    private final String region = "rx";

    // 注入缓存操作对象
    @Autowired
    private CacheChannel cacheChannel;

    @Test
    public void getInfos() {
        // 从缓存中获取数据，需要指定区域region和key
        CacheObject cacheObject = cacheChannel.get(region, key);
        List<String> data = new ArrayList<>();

        if (cacheObject.getValue() == null) {
            // 缓存中没有找到，查询数据库获得
            data.add("info1");
            data.add("info2");
            // 放入缓存
            cacheChannel.set(region, key, data);
        } else {
            // 获取缓存的数据
            data = (List<String>) cacheObject.getValue();
        }

        System.out.println(data);
        // 用于测试查看是否从一级缓存中获取
        // int level = cacheChannel.check(region, key);
        // System.out.println("level: " + level);
    }

    // 清理指定缓存
    @Test
    public void evict() {
        cacheChannel.evict(region, key);
        System.out.println("evict success");
    }

    // 清理指定区域中的所有缓存
    @Test
    public void clear() {
        cacheChannel.clear(region);
        System.out.println("clear success");
    }

    // 检查指定的缓存数据是否存在
    @Test
    public void exists() {
        boolean exists = cacheChannel.exists(region, key);
        System.out.println("exists: " + exists);
    }

    // 检查指定的缓存数据是从哪一级缓存获取到的
    @Test
    public void check() {
        int level = cacheChannel.check(region, key);
        System.out.println("level: " + level);
    }

}
