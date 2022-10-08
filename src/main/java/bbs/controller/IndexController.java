package bbs.controller;

import bbs.dto.PaginationDTO;
import bbs.mapper.UserMapper;
import bbs.model.User;
import bbs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostService postService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {
        // find the cookie which named token
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        System.out.println("found cookie, setting session"); //sout
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }

        // get posts and show them in index page
        PaginationDTO pagination = postService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
