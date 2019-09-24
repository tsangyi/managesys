package com.xu.bms.servlet;

import com.xu.bms.service.LoginService;
import com.xu.bms.service.impl.LoginServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * ${Description}
 *
 * @author xu
 * @date 20/9/2019 下午4:46
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private LoginService loginService = new LoginServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置请求编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应正文类型
        resp.setContentType("text/html;charset=utf8");
        //获取请求参数
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");
        System.out.println(remember);
        //调用service方法
        boolean loginRes = loginService.login(username, password);

        if (loginRes) {
            resp.sendRedirect(req.getContextPath() + "frame.jsp");

        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
