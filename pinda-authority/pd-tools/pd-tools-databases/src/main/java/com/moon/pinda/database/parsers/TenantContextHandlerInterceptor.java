package com.moon.pinda.database.parsers;

import com.moon.pinda.context.BaseContextHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对来自浏览器的请求的拦截，一般是利用Filter实现的。
 * 1、在Spring中，基于Filter这种方式可以实现Bean预处理、后处理。
 * 比如注入FilterRegistrationBean，然后在这个Bean上传递自己继承Filter实现的自定义Filter进入即可。
 * <p>
 * 2、Spring MVC也有拦截器，不仅可实现Filter的所有功能，还可以更精确的控制拦截精度。
 * Spring MVC提供的org.springframework.web.servlet.handler.HandlerInterceptorAdapter这个适配器，
 * 继承此类，可以非常方便的实现自己的拦截器。
 */
@Slf4j
@AllArgsConstructor
public class TenantContextHandlerInterceptor extends HandlerInterceptorAdapter {

    private String databaseName;

    /**
     * 在业务处理器处理请求之前被调用。预处理，可以进行编码、安全控制等处理；
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            log.info("not exec!!! url={}", request.getRequestURL());
            return super.preHandle(request, response, handler);
        }
        BaseContextHandler.setDatabase(this.databaseName);
        return super.preHandle(request, response, handler);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用，可用于清理资源等。
     * 返回处理（已经渲染了页面），可以根据ex是否为null判断是否发生了异常，进行日志记录；
     *
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        BaseContextHandler.remove();
        super.afterCompletion(request, response, handler, ex);
    }

}
