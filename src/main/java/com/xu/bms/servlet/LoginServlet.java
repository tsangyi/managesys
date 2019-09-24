package com.xu.bms.servlet;

import com.xu.bms.service.LoginService;
import com.xu.bms.service.impl.LoginServiceImpl;
import com.xu.bms.util.EncryptUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
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
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String REMEMBER = "remember";

    private LoginService loginService = new LoginServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String UN = null;
        String PWD = null;
        //设置请求编码
        resp.setCharacterEncoding("UTF-8");
        //设置响应正文类型
        resp.setContentType("text/html;charset=utf8");
        //获取请求参数
        String username = req.getParameter(USERNAME);
        String password = req.getParameter(PASSWORD);
        String remember = req.getParameter(REMEMBER);
        System.out.println("remember:" + remember);

        boolean flag = false;
        Cookie[] cookies = req.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                String cookieName = cookie.getName();
                //4.判断名称是否是USERNAME,USERNAME
                if ("USERNAME".equals(cookieName)) {
                    //获取Cookie的value，时间
                    UN = cookie.getValue();
                    flag = true;
                } else if ("PASSWORD".equals(cookieName)) {
                    PWD = cookie.getValue();
                }
            }
            System.out.println("UN:" + UN);
            System.out.println("PWD:" + PWD);
            if (UN != null && PWD != null) {
                login(req, resp, UN, PWD);
            }
        }
        //判断是否是第一次登陆
        if (cookies == null || cookies.length == 0 || !flag) {
            boolean loginRes = loginService.login(username, EncryptUtils.getPwd(password));
            if (loginRes) {
                //判断是否勾选了记住我,勾选了就给前端cookie
                System.out.println("在等里拿到remember没?" + remember);
                if (remember != null) {
                    System.out.println("remember不为空");
                    //创建cookie对象
                    Cookie cookie1 = new Cookie("USERNAME", username);
                    Cookie cookie2 = new Cookie("PASSWORD", password);
                    //设置一下存活时间
                    cookie1.setMaxAge(60 * 60 * 24 * 7);
                    cookie2.setMaxAge(60 * 60 * 24 * 7);
                    //发送cookie
                    resp.addCookie(cookie1);
                    resp.addCookie(cookie2);
                }
                req.getSession().setAttribute("username", username);
                System.out.println("loginRes:=====" + loginRes);
                resp.sendRedirect(req.getContextPath() + "frame.jsp");

            } else {
                req.setAttribute("error", "用户名或密码错误!");
                req.getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        }

    }

    private void login(HttpServletRequest req, HttpServletResponse resp, String username, String password) throws IOException, ServletException {
        //调用service方法
        boolean loginRes = loginService.login(username, EncryptUtils.getPwd(password));
        if (loginRes) {
            System.out.println("loginRes1:" + loginRes);
            resp.sendRedirect(req.getContextPath() + "frame.jsp");

        } else {
            req.setAttribute("error", "用户名或密码错误!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
