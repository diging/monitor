package edu.asu.diging.monitor.core.service.impl;

import org.jasypt.util.text.AES256TextEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import edu.asu.diging.monitor.core.service.IPasswordEncryptor;

@Service
public class PasswordEncryptor implements IPasswordEncryptor {
    
    @Autowired
    private AES256TextEncryptor textEncryptor;
    
    @Override
    public String encrypt(String userPassword) {
        return textEncryptor.encrypt(userPassword);
    }

    @Override
    public String decrypt(String userPassword) {
        return textEncryptor.decrypt(userPassword);
    }
}
