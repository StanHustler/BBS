package bbs.service;

import bbs.mapper.UserMapper;
import bbs.model.User;
import bbs.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
//        User dbUser = userMapper.findByAccountId(user.getAccountId());
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);

        if (users.size() == 0) {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            System.out.println("new " + user);
            userMapper.insert(user);
        } else {
            User dbUser = users.get(0);
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setToken(user.getToken());
            System.out.println("[update] 'user' "+user.getName());
            System.out.println("    - gmt_modified: " + dbUser.getGmtModified());

            UserExample exampleUpdateUserById = new UserExample();
            exampleUpdateUserById.createCriteria().andIdEqualTo(dbUser.getId());
            userMapper.updateByExampleSelective(dbUser, exampleUpdateUserById);
        }
    }
}
