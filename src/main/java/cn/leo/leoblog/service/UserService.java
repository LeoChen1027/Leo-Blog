package cn.leo.leoblog.service;

import cn.leo.leoblog.pojo.User;

public interface UserService {

    User checkUser(String username, String password);
}
