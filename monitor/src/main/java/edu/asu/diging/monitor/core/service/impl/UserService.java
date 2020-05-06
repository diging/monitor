package edu.asu.diging.monitor.core.service.impl;

import edu.asu.diging.monitor.core.service.IUserService;
import edu.asu.diging.monitor.web.admin.forms.UserForm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.auth.User;
import edu.asu.diging.monitor.core.db.IUserDbConnection;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.exceptions.UserAlreadyExistsException;

@Service
public class UserService implements IUserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private IUserDbConnection dbConnection;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerNewUserAccount(UserForm userForm) throws UserAlreadyExistsException {
        if (userExists(userForm.getUsername())) {
            throw new UserAlreadyExistsException("There is an account with the username:" + userForm.getUsername());
        }
        User user = new User();
        user.setUsername(userForm.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userForm.getPassword()));
        try {
            dbConnection.store(user);
        } catch (UnstorableObjectException e) {
            logger.error("Could not store user", e);
        }
        return user;
    }

    private boolean userExists(String id) {
        return dbConnection.getById(id) != null;
    }

}
