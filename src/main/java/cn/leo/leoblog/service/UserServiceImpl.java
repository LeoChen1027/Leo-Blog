package cn.leo.leoblog.service;

import cn.leo.leoblog.dao.UserRepository;
import cn.leo.leoblog.pojo.User;
import cn.leo.leoblog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        User user=userRepository.findByUsernameAndPassword(username, MD5Utils.code(password));
        return user;
    }

}
