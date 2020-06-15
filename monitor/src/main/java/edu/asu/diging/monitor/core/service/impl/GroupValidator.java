package edu.asu.diging.monitor.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import edu.asu.diging.monitor.web.admin.forms.GroupForm;

@Service
public class GroupValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return GroupForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "group.name.empty");

    }

}
