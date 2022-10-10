package bbs.controller;

import bbs.dto.PaginationDTO;
import bbs.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private PostService postService;

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size) {

        // get posts and show them in index page
        PaginationDTO pagination = postService.list(page,size);
        model.addAttribute("pagination",pagination);
        return "index";
    }
}
