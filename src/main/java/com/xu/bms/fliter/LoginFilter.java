package com.xu.bms.fliter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * ${Description}
 *
 * @author xu
 * @date 24/9/2019 下午6:59
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        System.out.println("进入拦截器");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        String uri = request.getRequestURI();

        HttpSession session = request.getSession();
        Object username = session.getAttribute("username");
        if (uri.contains("/css/") || uri.contains("/js/") ||
                uri.contains("/fonts/") || uri.contains("/error/") ||
                uri.contains("/login.jsp") || uri.contains("/login")
                || uri.contains("/frame.jsp")) {
            chain.doFilter(req, resp);
        } else {
            if (username == null) {
                // 2.1如果没有获取到,则重定向到登陆页面
                response.sendRedirect(request.getContextPath() + "/login");
            } else {
                // 2.2如果获取到了,则放行
                System.out.println("放行");
                chain.doFilter(req, resp);
            }
        }


    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
