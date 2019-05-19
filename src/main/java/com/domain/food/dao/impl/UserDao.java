package com.domain.food.dao.impl;

import com.domain.food.core.AbstractDao;
import com.domain.food.dao.IUserDao;
import com.domain.food.domain.User;
import org.springframework.stereotype.Repository;

/**
 * 用户持久化实现类
 *
 * @author zhoutaotao
 * @date 2019/5/16
 */
@Repository
public class UserDao extends AbstractDao<String, User> implements IUserDao {

    @Override
    public void saveUser(User user) {
        super.save(user);
    }

    @Override
    public void updateUser(User user) {
        super.update(user);
    }

    @Override
    public User getByUserCode(String userCode) {
        return super.getById(userCode);
    }

    @Override
    public void deleteUser(String userCode) {
        super.deleteById(userCode);
    }
}
