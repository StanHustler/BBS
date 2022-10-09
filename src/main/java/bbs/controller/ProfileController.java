package bbs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProfileController {

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action")String action,
                          Model model){

        if (action.equals("posts")){
            model.addAttribute("section","posts");
            model.addAttribute("sectionName","我的发表");
        }
        return "profile";
    }
}
