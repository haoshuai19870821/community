package com.majiang.springboot.controller;

import com.majiang.springboot.dto.PaginationDTO;
import com.majiang.springboot.dto.QuestionDTO;
import com.majiang.springboot.mapper.UserMapper;
import com.majiang.springboot.model.User;
import com.majiang.springboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @RequestMapping({"/","index.html"})
    //request里可以获取cookie信息,用来拿取存入cookie的token
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name="page",defaultValue = "1") Integer page,
                        @RequestParam(name="size",defaultValue = "2") Integer size){
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
        PaginationDTO pagination = questionService.list(page, size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
