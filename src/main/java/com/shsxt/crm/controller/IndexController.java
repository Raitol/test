package com.shsxt.crm.controller;

import com.shsxt.crm.base.BaseController;
import com.shsxt.crm.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController extends BaseController {

    @RequestMapping("index")
    public String index() {
        return "index";

    }
    @RequestMapping("main")
    public String main(HttpServletRequest request, Model model) {
        String userName = CookieUtil.getCookieValue(request, "userName");
        model.addAttribute("userName", userName);
        String realName = CookieUtil.getCookieValue(request, "realName");
        model.addAttribute("realName", realName);
        return "main";
    }
}
