package com.shsxt.crm.service;

import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private UserDao userDao;

    public User findById(Integer id) {
        return userDao.findById(id);
    }
    public List<User> find() {
        return userDao.find();
    }

}
