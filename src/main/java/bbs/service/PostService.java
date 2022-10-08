package bbs.service;

import bbs.dto.PaginationDTO;
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

    public PaginationDTO list(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = postMapper.count();
        paginationDTO.setPagination(totalCount,page,size);
        // error response
        if (page < 1) page = 1;
        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();

        Integer offset = size * (page -1);
        List<Post> posts = postMapper.list(offset,size);
        List<PostDTO> postDTOList = new ArrayList<>();

        for (Post post:posts){
            // put a user as property and whole post into postDTO
            User user =  userMapper.findById(post.getCreator());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post,postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        paginationDTO.setPosts(postDTOList);
        return paginationDTO;
    }
}
