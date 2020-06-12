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
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "App name cannot be blank.");
        if (appForm.getGroupType() == GroupType.NEW)
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "groupName", "Group name cannot be blank.");

        if (appForm.getGroupType() == GroupType.EXISTING)
            ValidationUtils.rejectIfEmpty(errors, "existingGroupId", "Please select a valid group from dropdown.");
    }
}
