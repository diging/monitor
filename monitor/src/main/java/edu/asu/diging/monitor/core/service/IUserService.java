package edu.asu.diging.monitor.core.service;

import edu.asu.diging.monitor.core.auth.User;
import edu.asu.diging.monitor.core.exceptions.UserAlreadyExistsException;
import edu.asu.diging.monitor.web.admin.forms.UserForm;

public interface IUserService {
    User registerNewUserAccount(UserForm user) throws UserAlreadyExistsException;
}