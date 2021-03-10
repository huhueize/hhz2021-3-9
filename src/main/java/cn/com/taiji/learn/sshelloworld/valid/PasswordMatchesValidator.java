package cn.com.taiji.learn.sshelloworld.valid;

import cn.com.taiji.learn.sshelloworld.domain.User;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {


    @Override
    public void initialize(PasswordMatches constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        final User user = (User) o;
        return user.getPassword().equals(user.getPasswordConfirmation());
    }
}
