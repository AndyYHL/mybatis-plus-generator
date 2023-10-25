package com.example.generator.web.interceptor;

import com.example.generator.context.RequestHeaderContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * RequestHeaderContextInterceptor描述
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Slf4j
public class RequestHeaderContextInterceptor implements HandlerInterceptor {
    /**
     * 请求头参数
     */
    private static final String HEAD_AUTHORIZATION = "Authorization";

    @Override
    @SuppressWarnings("all")
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        initHeaderContext(request);
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private void initHeaderContext(HttpServletRequest request) {

        String authorization = request.getHeader(HEAD_AUTHORIZATION);
        log.info(">>>>>>>拦截到api相关请求头<<<<<<<<".concat(HEAD_AUTHORIZATION).concat(":[{}]"), authorization);

        /*JSONObject jsonObject = JSONObject.parseObject(bearerAuth);
        String user_name = (String) jsonObject.get("user_name");*/

        new RequestHeaderContext.RequestHeaderContextBuild()
                .authorization(authorization)
                .bulid();
    }

    @Override
    @SuppressWarnings("all")
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        RequestHeaderContext.clean();
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
