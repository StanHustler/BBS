package bbs.service;

import bbs.dto.PaginationDTO;
import bbs.dto.PostDTO;
import bbs.mapper.PostMapper;
import bbs.mapper.UserMapper;
import bbs.model.Post;
import bbs.model.PostExample;
import bbs.model.User;
import org.apache.ibatis.session.RowBounds;
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
        PostExample example = new PostExample();
        Integer totalCount = (int) postMapper.countByExample(example);
        paginationDTO.setPagination(totalCount, page, size);
        // error response
        if (page < 1) page = 1;
        if (page > paginationDTO.getTotalPage()) page = paginationDTO.getTotalPage();
        // page split
        int offset = size * (page - 1);
        List<Post> posts = postMapper.selectByExampleWithRowbounds(new PostExample(),new RowBounds(offset,size));
        List<PostDTO> postDTOList = new ArrayList<>();

        for (Post post : posts) {
            // put a user as property and whole post into postDTO

            User user = userMapper.selectByPrimaryKey(post.getCreator());
            PostDTO postDTO = new PostDTO();
            BeanUtils.copyProperties(post, postDTO);
            postDTO.setUser(user);
            postDTOList.add(postDTO);
        }
        paginationDTO.setPosts(postDTOList);
        return paginationDTO;
    }

    public PostDTO getById(int id) {
        Post post = postMapper.selectByPrimaryKey(id);
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setUser(userMapper.selectByPrimaryKey(post.getCreator()));
        return postDTO;
    }

    public void createOrUpdate(Post post) {
        if (post.getId() == null) {
            post.setGmtCreate(System.currentTimeMillis());
            post.setGmtModified(post.getGmtCreate());
            System.out.println("[Create] post " + post.getTitle());
            postMapper.insert(post);
        } else {
            post.setGmtModified(System.currentTimeMillis());
            System.out.println("[Update] post "+ post.getTitle());
            System.out.println("    - gmt_modified: "+ post.getGmtCreate());
            PostExample example = new PostExample();
            example.createCriteria().andIdEqualTo(post.getId());
            postMapper.updateByExampleSelective(post, example);
        }
    }
}
