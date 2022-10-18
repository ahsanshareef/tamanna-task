package com.tamanna.demo.service.impl;

import com.tamanna.demo.model.User;
import com.tamanna.demo.repository.UserRepository;
import com.tamanna.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User get(Long userId) {
        return userRepository.getReferenceById(userId);
    }
}
