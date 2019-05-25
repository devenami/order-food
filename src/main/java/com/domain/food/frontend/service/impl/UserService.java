package com.domain.food.frontend.service.impl;

import com.domain.food.core.AbstractService;
import com.domain.food.dao.IUserDao;
import com.domain.food.domain.User;
import com.domain.food.frontend.service.IUserService;
import com.domain.food.utils.*;
import com.domain.food.vo.UserVO;
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
    public UserVO addUser(String userCode, String password, String username, int sex, String department) {
        User user = new User();
        user.setUserCode(userCode);
        user.setPassword(DigestUtil.md5(password));
        user.setUsername(username);
        user.setSex(sex);
        user.setDepartment(department);
        user.setSave(System.currentTimeMillis());
        userDao.saveUser(user);
        UserVO userVO = new UserVO();
        BeanUtil.copy(user, userVO);
        return userVO;
    }

    @Override
    public UserVO updateUser(String userCode, String password, String username, Integer sex, String department) {
        User user = userDao.getByUserCode(userCode);
        Condition.notNull(user, "用户不存在");
        if (!StringUtil.isBlank(password)) {
            user.setPassword(DigestUtil.md5(password));
        }
        if (!StringUtil.isBlank(username)) {
            user.setUsername(username);
        }
        if (!ObjectUtil.isNull(sex)) {
            user.setSex(sex);
        }
        if (!StringUtil.isBlank(department)) {
            user.setDepartment(department);
        }
        userDao.updateUser(user);
        UserVO userVO = new UserVO();
        BeanUtil.copy(user, userVO);
        return userVO;
    }

    @Override
    public UserVO deleteUser(String userCode) {
        User user = userDao.getByUserCode(userCode);
        Condition.notNull(user, "用户不存在");
        userDao.deleteUser(userCode);
        UserVO userVO = new UserVO();
        BeanUtil.copy(user, userVO);
        return userVO;
    }


}
