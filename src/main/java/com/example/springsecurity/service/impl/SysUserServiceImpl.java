package com.example.springsecurity.service.impl;

import com.example.springsecurity.dao.UserRepository;
import com.example.springsecurity.entity.SysUser;
import com.example.springsecurity.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public SysUser findByUsername(String username){
        return userRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public SysUser save(SysUser user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public List<SysUser> getAllUserInfo(){
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void changeUserInfo(String userName, String userRank){
        SysUser user = userRepository.findByUsername(userName);
        user.setRank(userRank);
        userRepository.save(user);
    }
}
