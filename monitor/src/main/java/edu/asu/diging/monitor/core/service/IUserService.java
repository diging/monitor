package edu.asu.diging.monitor.core.service;

import edu.asu.diging.monitor.core.exceptions.UserAlreadyExistsException;
import edu.asu.diging.monitor.web.admin.forms.UserForm;

public interface IUserService {
    void registerNewUserAccount(UserForm user) throws UserAlreadyExistsException;
}