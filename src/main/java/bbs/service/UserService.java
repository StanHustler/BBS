package bbs.service;

import bbs.mapper.UserMapper;
import bbs.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    public void createOrUpdate(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if (dbUser == null) {
            user.setGmtCreate(String.valueOf(System.currentTimeMillis()));
            user.setGmtModified(user.getGmtCreate());
            System.out.println("new " + user);
            userMapper.insert(user);
        } else {
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setGmtModified(String.valueOf(System.currentTimeMillis()));
            dbUser.setToken(user.getToken());
            System.out.println("[update] 'user' "+user.getName());
            System.out.println("    - gmt_modified: " + dbUser.getGmtModified());
            userMapper.update(dbUser);
        }
    }
}
