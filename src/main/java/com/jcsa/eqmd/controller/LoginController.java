package com.jcsa.eqmd.controller;

import com.jcsa.eqmd.bean.User;
import com.jcsa.eqmd.config.UserConfig;
import com.jcsa.eqmd.constant.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private UserConfig config;

    @RequestMapping({"/index", ""})
    public String index(Model model){
        return "index";
    }
    @RequestMapping("/login")
    public String login(User user, HttpServletRequest request, Model model){
        if(user == null || user.getUserName() == null || user.getPassword() == null){
            model.addAttribute("info", "fail");
            return "index";
        }
        if(user.getPassword().equals(config.getMap().get(user.getUserName()))){
            HttpSession session = request.getSession();
            session.setAttribute(Constants.SESSION_KEY_USERNAME, user);
            session.setMaxInactiveInterval(30 * 60);
            return "main";

        }
        model.addAttribute("info", "fail");
        return "index";
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        if(session != null){
            session.invalidate();
        }
        return "redirect:/index";
    }
}
