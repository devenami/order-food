package com.domain.food.service.impl;

import com.domain.food.core.AbstractService;
import com.domain.food.dao.IUserDao;
import com.domain.food.domain.User;
import com.domain.food.service.IUserService;
import com.domain.food.utils.Condition;
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
    public User addUser(String userCode, String username, int sex, String department) {
        User user = new User();
        user.setUserCode(userCode);
        user.setUsername(username);
        user.setSex(sex);
        user.setDepartment(department);
        userDao.saveUser(user);
        return user;
    }

    @Override
    public User updateUser(String userCode, String username, Integer sex, String department) {
        User user = userDao.getByUserCode(userCode);
        Condition.notNull(user, "用户不存在");
        user.setUsername(username);
        user.setSex(sex);
        user.setDepartment(department);
        userDao.updateUser(user);
        return user;
    }

    @Override
    public User deleteUser(String userCode) {
        User user = userDao.getByUserCode(userCode);
        Condition.notNull(user, "用户不存在");
        userDao.deleteUser(userCode);
        return user;
    }


}
