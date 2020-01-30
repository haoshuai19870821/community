package com.majiang.springboot.interceptor;

import com.majiang.springboot.mapper.UserMapper;
import com.majiang.springboot.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();
        if(null!=cookies&&cookies.length!=0){
            for(Cookie cookie:cookies){
                //判断cookie中是否有key为token的字段
                if("token".equals(cookie.getName())){
                    //如果有获取value值
                    String token = cookie.getValue();
                    //根据token查找User类
                    User user = userMapper.findByToken(token);
                    //判断user是否为null
                    if(null!=user){
                        //如果不为null说明查到user那么把user对象存储到session中方便前端拿取
                        request.getSession().setAttribute("user",user);
                    }
                    //跳出循环
                    break;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }
}
