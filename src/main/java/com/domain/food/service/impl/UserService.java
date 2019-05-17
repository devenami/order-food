package com.domain.food.service.impl;

import com.domain.food.core.AbstractService;
import com.domain.food.dao.IUserDao;
import com.domain.food.domain.User;
import com.domain.food.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户管理服务
 *
 * @author feb13th
 * @since 2019/5/16 22:00
 */
@Service
public class UserService extends AbstractService implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public User addUser(String username, int sex, String department) {
        User user = new User();
        // TODO 添加编码生成器
        user.setUserCode("code");
        user.setUsername(username);
        user.setSex(sex);
        user.setDepartment(department);
        userDao.save(user);
        return user;
    }
}
