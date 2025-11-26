package com.example.generator.context;

import lombok.Getter;

/**
 * RequestHeaderContext描述
 * 规定所有的Http头中必须携带Token 来验证用户身份，采用在拦截器用户名信息绑定到ThreadLocal，以供后续方法使用
 * 声明 上下文
 *
 * @author Administrator-YHL
 * @date 2023-10-25
 **/
@Getter
public class RequestHeaderContext {
    private static final ThreadLocal<RequestHeaderContext> REQUEST_HEADER_CONTEXT_THREAD_LOCAL = new ThreadLocal<>();
    private final String authorization;

    public static RequestHeaderContext getInstance() {
        return REQUEST_HEADER_CONTEXT_THREAD_LOCAL.get();
    }

    public void setContext(RequestHeaderContext context) {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.set(context);
    }

    public static void clean() {
        REQUEST_HEADER_CONTEXT_THREAD_LOCAL.remove();
    }

    private RequestHeaderContext(RequestHeaderContextBuild requestHeaderContextBuild) {
        this.authorization = requestHeaderContextBuild.authorization;
        setContext(this);
    }

    public static class RequestHeaderContextBuild {
        private String authorization;

        public RequestHeaderContextBuild authorization(String authorization) {
            this.authorization = authorization;
            return this;
        }

        public RequestHeaderContext bulid() {
            return new RequestHeaderContext(this);
        }
    }
}
