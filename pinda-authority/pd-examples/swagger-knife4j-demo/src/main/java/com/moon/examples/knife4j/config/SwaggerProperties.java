package com.moon.examples.knife4j.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置属性类，用于封装yml配置文件中关于 Swagger 接口文档相关的配置信息
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-19 15:58
 * @description
 */
@Data
@ConfigurationProperties(prefix = "example.swagger")
public class SwaggerProperties {
    // 以下属性用于不配置分组的情况
    private String title = "在线文档"; // 标题
    private String group = ""; // 自定义组名
    private String description = "在线文档"; // 描述
    private String version = "1.0"; // 版本
    private Contact contact = new Contact(); // 联系人
    private String basePackage = "com.moon.examples.knife4j"; // swagger会解析的包路径
    private List<String> basePath = new ArrayList<>(); // swagger会解析的url规则
    private List<String> excludePath = new ArrayList<>();// 在basePath基础上需要排除的url规则
    // 以下用于保存配置分组文档
    private Map<String, DocketInfo> docket = new LinkedHashMap<>();

    public String getGroup() {
        if (group == null || "".equals(group)) {
            return title;
        }
        return group;
    }

    @Data
    public static class DocketInfo {
        private String title = "在线文档"; // 标题
        private String group = ""; // 自定义组名
        private String description = "在线文档"; // 描述
        private String version = "1.0"; // 版本
        private Contact contact = new Contact(); // 联系人
        private String basePackage = ""; // swagger会解析的包路径
        private List<String> basePath = new ArrayList<>(); // swagger会解析的url规则
        private List<String> excludePath = new ArrayList<>();// 在basePath基础上需要排除的url

        public String getGroup() {
            if (group == null || "".equals(group)) {
                return title;
            }
            return group;
        }
    }

    @Data
    public static class Contact {
        private String name = "MooN"; // 联系人
        private String url = ""; // 联系人url
        private String email = ""; // 联系人email
    }

}
