package com.jcsa.eqmd.filter;

import com.jcsa.eqmd.constant.Constants;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        // 请求的url
        String url = request.getRequestURI();
       if("/".equals(url) || url.contains("/index") || url.contains("/css") || url.contains("/js") || url.contains("/images") || url.contains("/login")) {
           chain.doFilter(req, resp);
       }else {
           // 获得session
           HttpSession session = request.getSession();
           // 从session中获取SessionKey对应值,若值不存在,则重定向到redirectUrl
           Object user = session.getAttribute(Constants.SESSION_KEY_USERNAME);
           if (user != null) {
               chain.doFilter(request, response);
           } else {
               response.sendRedirect("/");
           }
       }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
