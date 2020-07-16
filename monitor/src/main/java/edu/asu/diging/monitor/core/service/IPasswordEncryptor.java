package edu.asu.diging.monitor.core.service;

public interface IPasswordEncryptor {

    String encrypt(String userPassword);

    String decrypt(String userPassword);

}
