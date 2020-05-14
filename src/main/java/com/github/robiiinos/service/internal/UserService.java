package com.github.robiiinos.service.internal;

import com.github.robiiinos.dao.UserDao;
import com.github.robiiinos.model.User;
import com.github.robiiinos.request.internal.UserLoginRequest;
import com.github.robiiinos.request.internal.UserRegisterRequest;
import com.github.robiiinos.service.AuthenticationService;

public class UserService {
    private static final UserDao userDao = new UserDao();

    private static final AuthenticationService authService = new AuthenticationService();

    public UserService() {
    }

    public int createUser(UserRegisterRequest userRequest) {
        return userDao.create(userRequest);
    }

    public String loginUser(UserLoginRequest userRequest) {
        User user = (User) userDao.findByUsernameAndPassword(userRequest);

        return authService.createToken(user);
    }
}
