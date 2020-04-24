package edu.asu.diging.monitor.core.db;

import edu.asu.diging.monitor.core.auth.User;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;

public interface IUserDbConnection {
    
    User getById(String username);

    User store(User app) throws UnstorableObjectException;

}
