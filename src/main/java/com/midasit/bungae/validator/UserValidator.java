package com.midasit.bungae.validator;

import com.midasit.bungae.user.dto.User;
import com.midasit.bungae.user.repository.UserDao;
import com.midasit.bungae.user.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User)target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "authority", "field.required");
    }


    public static class MyApplication {
        public static void main(String[] args) {
            User user = new User();
            user.setNo(123);

            // validation
            // 1. UserValidator.validate(user, Error)
            // 2.

            new UserDao().create(user);
        }
    }
}
