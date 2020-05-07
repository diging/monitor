package edu.asu.diging.monitor.core.db;

import edu.asu.diging.monitor.core.auth.IUser;
import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;

public interface IUserDbConnection {
    
    IUser getById(String username);

    IUser store(IUser app) throws UnstorableObjectException;

}
