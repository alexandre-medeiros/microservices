package com.himax.ead.course.core.validators;

import com.himax.ead.course.api.v1.model.CourseDto;
import com.himax.ead.course.api.v1.model.UserDto;
import com.himax.ead.course.client.AuthUserClient;
import com.himax.ead.course.domain.enums.UserStatus;
import com.himax.ead.course.domain.enums.UserType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseValidator implements Validator {

    private Validator validator;
    private AuthUserClient client;

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseDto course = (CourseDto) o;
        validator.validate(course, errors);

        if (!errors.hasErrors()) {
            validInstructor(course.getUserInstructor(), errors);
        }
    }

    private void validInstructor(UUID id, Errors errors) {
        UserDto user = client.findUserById(id);
        validIfIsActive(user, errors);
        validIfIsInstructor(user, errors);
    }

    private void validIfIsInstructor(UserDto user, Errors errors) {
        if (!errors.hasErrors() && !user.getUserType().equals(UserType.INSTRUCTOR)) {
            errors.rejectValue("userInstructor", "InstructorError", "User with id " + user.getId() + " is not a INSTRUCTOR");
        }
    }

    private void validIfIsActive(UserDto user, Errors errors) {
        if (!errors.hasErrors() && !user.getUserStatus().equals(UserStatus.ACTIVE)) {
            errors.rejectValue("userInstructor", "InstructorError", "User with id " + user.getId() + " is not ACTIVE");
        }
    }
}
