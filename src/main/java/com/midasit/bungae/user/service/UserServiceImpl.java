package com.midasit.bungae.user.service;

import com.midasit.bungae.generator.repository.UserMapper;
import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public User getById(String id) {
        return userRepository.get(id);
    }
}
