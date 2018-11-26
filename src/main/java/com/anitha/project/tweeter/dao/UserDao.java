package com.anitha.project.tweeter.dao;

import com.anitha.project.tweeter.dao.AbstractDao;
import com.anitha.project.tweeter.model.User;

public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super(User.class);
    }

}
