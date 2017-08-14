package com.shsxt.crm.service;

import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.exception.ParamException;
import com.shsxt.crm.model.User;
import com.shsxt.crm.util.MD5Util;
import com.shsxt.crm.util.UserIDBase64;
import com.shsxt.crm.vo.UserLoginIdentity;
import com.shsxt.crm.vo.UserVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    @Autowired
    private UserDao userDao;

    public UserLoginIdentity login(String userName, String password) {
        if (StringUtils.isBlank(userName)) {
            throw new ParamException("请输入用户名");
        }
        if (StringUtils.isBlank(password)) {
            throw new ParamException("请输入密码");
        }
        User user = userDao.findByUserName(userName);
        if (user == null) {
            throw new ParamException("用户名或密码错误");
        }
        if (!user.getPassword().equals(MD5Util.md5Method(password))) {
            throw new ParamException("用户名或密码错误");
        }
        UserLoginIdentity userLoginIdentity = new UserLoginIdentity();
        userLoginIdentity.setRealName(user.getTrueName());
        userLoginIdentity.setUserName(user.getUserName());
        userLoginIdentity.setUserIdString(UserIDBase64.encoderUserID(user.getId()));

        return userLoginIdentity;

    }

    public List<UserVO> findCutomerManager() {
        List<UserVO> customerManagers = userDao.findCutomerManager();
        return customerManagers;
    }

}
