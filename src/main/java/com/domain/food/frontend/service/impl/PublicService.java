package com.domain.food.frontend.service.impl;

import com.domain.food.consts.ErrorCode;
import com.domain.food.dao.IUserDao;
import com.domain.food.domain.User;
import com.domain.food.frontend.service.IPublicService;
import com.domain.food.utils.BeanUtil;
import com.domain.food.utils.DigestUtil;
import com.domain.food.utils.ObjectUtil;
import com.domain.food.utils.SessionUtil;
import com.domain.food.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 公开服务是吸纳类
 *
 * @author zhoutaotao
 * @date 2019/5/16
 */
@Service
public class PublicService implements IPublicService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserVO login(String userCode, String password) {

        // 参数校验
        User user = userDao.getByUserCode(userCode);
        if (ObjectUtil.isNull(user)) {
            ErrorCode.LOGIN_USER_NOT_EXISTS.shutdown();
        }
        String existsPassword = user.getPassword();
        if (!existsPassword.equals(DigestUtil.md5(password))) {
            ErrorCode.LOGIN_PASSWORD_ERROR.shutdown();
        }

        // 执行登陆
        UserVO userVO = new UserVO();
        BeanUtil.copy(user, userVO);
        SessionUtil.addUser(userVO);

        return userVO;
    }
}
