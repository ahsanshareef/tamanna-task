package com.tamanna.demo.service;

import com.tamanna.demo.model.User;

public interface IUserService {

    User addUser(User user);

    User get(Long userId);
}
