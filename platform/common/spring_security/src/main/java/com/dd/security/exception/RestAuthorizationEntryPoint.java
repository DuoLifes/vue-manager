package com.dd.security.exception;

import com.dd.common_utils.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 当未登录或token失效时访问接口，自定义返回结果
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");//设置响应编码
        response.setContentType("application/json");//设置响应格式，前后端分离最好是json
        PrintWriter out = response.getWriter();//获取response输出流
        //创建返回结果对象
        Result result = Result.error().code(401).message("请登录！！！");
        //以json字符串形式放到response输出流中
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();//刷新
        out.close();//关闭流
    }
}
