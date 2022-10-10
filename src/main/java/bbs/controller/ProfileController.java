package bbs.controller;

import bbs.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model,
                          HttpServletRequest request){

        User user =(User) request.getSession().getAttribute("user");
        if (user == null) return "redirect:/";

        model.addAttribute("section",action);
        if (action.equals("posts")){
            model.addAttribute("sectionName","我的发表");
        } else if (action.equals("replies")) {
            model.addAttribute("sectionName","最新回复");
        }

        return "profile";
    }
}
