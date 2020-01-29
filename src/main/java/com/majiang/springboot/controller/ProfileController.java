package com.majiang.springboot.controller;

import com.majiang.springboot.dto.PaginationDTO;
import com.majiang.springboot.mapper.UserMapper;
import com.majiang.springboot.model.User;
import com.majiang.springboot.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name="action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(name="page",defaultValue = "1") Integer page,
                          @RequestParam(name="size",defaultValue = "5") Integer size){
        User user = null;
        Cookie[] cookies = request.getCookies();
        if(null!=cookies&&cookies.length!=0){
            for(Cookie cookie:cookies){
                //判断cookie中是否有key为token的字段
                if("token".equals(cookie.getName())){
                    //如果有获取value值
                    String token = cookie.getValue();
                    //根据token查找User类
                     user = userMapper.findByToken(token);
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
        if(null==user){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }
        PaginationDTO paginationDTO = questionService.list(user.getId(), page, size);
        model.addAttribute("pagination",paginationDTO);
        return "profile";
    }
}
