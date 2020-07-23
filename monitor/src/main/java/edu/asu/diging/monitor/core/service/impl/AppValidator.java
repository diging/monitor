package edu.asu.diging.monitor.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.diging.monitor.core.model.GroupType;
import edu.asu.diging.monitor.web.admin.forms.AppForm;

@Service
public class AppValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AppForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppForm appForm = (AppForm) target;
        if (appForm.getName() != null) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "app.name.empty");
        }
        if (appForm.getGroupType() == GroupType.NEW) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupName", "group.name.empty");
        }
        if (appForm.getGroupType() == GroupType.EXISTING) {
            ValidationUtils.rejectIfEmpty(errors, "existingGroupId", "group.dropdown.empty");
        }
        if (appForm.isUpdateUserInfo()) {
            if (!appForm.getUsername().isEmpty() || !appForm.getPassword().isEmpty()) {
                ValidationUtils.rejectIfEmpty(errors, "username", "user.name.empty");
                ValidationUtils.rejectIfEmpty(errors, "password", "user.password.empty");
            }
        }
    }
}