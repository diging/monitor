package edu.asu.diging.monitor.core.db;

import edu.asu.diging.monitor.core.exceptions.UnstorableObjectException;
import edu.asu.diging.monitor.core.model.IAppTest;

public interface IAppTestDbConnection {

	IAppTest getById(String id);

	IAppTest store(IAppTest appTest) throws UnstorableObjectException;

	String generateId();

}