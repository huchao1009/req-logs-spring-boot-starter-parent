package com.opensource.component.logs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.NamedThreadLocal;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

@Slf4j
public class SysLogInterceptor extends HandlerInterceptorAdapter implements RequestBodyAdvice {

    private NamedThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<>("StopWatch-StartTime");

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ReqLogProperties properties;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        long beginTime = System.currentTimeMillis();
        startTimeThreadLocal.set(beginTime);
        String path = getPath(request);
        log.info("Start: {}", path);

        if (null != handler && handler instanceof HandlerMethod) {
            Map<String, String[]> params = request.getParameterMap();
            log.info("invoke {}, Params: {}", path, objectMapper.writeValueAsString(params));
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            long endTime = System.currentTimeMillis();
            long beginTime = startTimeThreadLocal.get();
            long consumeTime = endTime - beginTime;

            String path = getPath(request);

            log.info("End {}, executeTime: {} ms", path, consumeTime);

            if (consumeTime > properties.getSlowReqElapsed()) {
                log.warn("slow api ,{}, executeTime {} ms", path, consumeTime);
            }
        } finally {
            startTimeThreadLocal.remove();
        }
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object o, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return o;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        return httpInputMessage;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        try {
            log.info("body Params: {}", objectMapper.writeValueAsString(body));
        } catch (JsonProcessingException e) {
            log.error("body to json error", e);
        }
        return body;
    }

    private String getPath(HttpServletRequest request) {
        String path = request.getServletPath();
        if (StringUtils.isBlank(path)) {
            path = request.getRequestURI();
        }
        return path;
    }
}
