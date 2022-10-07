package bbs.service;

import bbs.dto.PostDTO;
import bbs.mapper.PostMapper;
import bbs.mapper.UserMapper;
import bbs.model.Post;
import bbs.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PostMapper postMapper;

    public List<PostDTO> list() {
        List<Post> posts = postMapper.list();
        List<PostDTO> postDTOList = new ArrayList<>();
        for (Post post:posts){
            // put a user as property and whole post into postDTO
            User user =  userMapper.findById(post.getCreator());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }
}
