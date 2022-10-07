package bbs.controller;

import bbs.dto.PostDTO;
import bbs.mapper.UserMapper;
import bbs.model.User;
import bbs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostService postService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request,
                        Model model) {
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
        List<PostDTO> postList = postService.list();
        model.addAttribute("posts",postList);
        return "index";
    }
}
