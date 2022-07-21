package com.moon.examples.antisamy.filter;

import org.owasp.validator.html.AntiSamy;
import org.owasp.validator.html.CleanResults;
import org.owasp.validator.html.Policy;
import org.owasp.validator.html.PolicyException;
import org.owasp.validator.html.ScanException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.Map;

/**
 * HttpServletRequest 包装类，在此类中的进行请求参数的处理
 *
 * @author MooNkirA
 * @version 1.0
 * @date 2022-07-21 10:26
 * @description
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {

    // 定义 AntiSamy 提供的策略对象 Policy
    private static Policy policy;

    static {
        try {
            // 获得antisamy框架所需的决策文件路径
            String antisayPath = XssRequestWrapper.class.getClassLoader()
                    .getResource("antisamy-test.xml")
                    .getFile();
            // 获取过滤策略对象
            policy = Policy.getInstance(antisayPath);
        } catch (PolicyException e) {
            e.printStackTrace();
        }
    }

    // 通过 AntiSamy 框架过滤请求参数的字符
    public String cleanXss(String text) {
        try {
            AntiSamy antiSamy = new AntiSamy();
            // 扫描字符串，根据策略去清除非法的字符
            CleanResults cleanResults = antiSamy.scan(text, policy);
            // 获取清理后的字符串
            text = cleanResults.getCleanHTML();
        } catch (ScanException | PolicyException e) {
            e.printStackTrace();
        }
        return text;
    }

    /**
     * 构造方法
     *
     * @param request
     */
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 此方法的默认行为是在被包装的请求对象上返回 getParameterValues(String name)。
     *
     * @param name
     * @return
     */
    @Override
    public String[] getParameterValues(String name) {
        // 获取原始的请求参数值
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues == null) {
            return null;
        }

        int length = parameterValues.length;
        // 定义待返回的新的数组
        String[] newArray = new String[length];

        for (int i = 0; i < length; i++) {
            // 获取参数值
            String parameterValue = parameterValues[i];
            // 进行参数处理
            parameterValue = cleanXss(parameterValue);
            // 将处理后的值，放到新的数组中
            newArray[i] = parameterValue;
        }
        return newArray;
    }

    /* **********************************
     * 以下对多种情况进行 xss 攻击处理
     ************************************/

    @Override
    public String getParameter(String paramString) {
        String str = super.getParameter(paramString);
        if (str == null) {
            return null;
        }
        return cleanXss(str);
    }


    @Override
    public String getHeader(String paramString) {
        String str = super.getHeader(paramString);
        if (str == null) {
            return null;
        }
        return cleanXss(str);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> requestMap = super.getParameterMap();
        for (Map.Entry<String, String[]> me : requestMap.entrySet()) {
            String[] values = me.getValue();
            for (int i = 0; i < values.length; i++) {
                values[i] = cleanXss(values[i]);
            }
        }
        return requestMap;
    }
}
