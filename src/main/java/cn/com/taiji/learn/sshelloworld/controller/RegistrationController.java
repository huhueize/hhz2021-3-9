package cn.com.taiji.learn.sshelloworld.controller;

import cn.com.taiji.learn.sshelloworld.domain.User;
import cn.com.taiji.learn.sshelloworld.expetion.EmailExistsException;
import cn.com.taiji.learn.sshelloworld.service.IEmailService;
import cn.com.taiji.learn.sshelloworld.service.IUserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@Api(tags = {"注册接口"})
public class RegistrationController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IEmailService iEmailService;

    @GetMapping(value = "/signup")
    public ModelAndView registrationForm() {
        return new ModelAndView("registrationPage", "user", new User());
    }

    @GetMapping(value = "/user/verify/{id}")
    public ModelAndView verify(@PathVariable String id) {
        User user = userService.findById(Long.valueOf(id));
        userService.enable(user);
        return new ModelAndView("redirect:/login");
    }

    @PostMapping(value = "/user/register")
    public ModelAndView registerUser(@Valid final User user, final BindingResult result) {
//        if(!user.getPassword().equals(user.getPasswordConfirmation())){
//            result.addError(new FieldError("user", "email", "密码不一致"));
//            return new ModelAndView("registrationPage", "user", user);
//        }

        if (result.hasErrors()) {
            return new ModelAndView("registrationPage", "user", user);
        }
        try {
            user.setEnabled(false);
            User u = userService.registerNewUser(user);
            iEmailService.sendEmail(user);
        } catch (EmailExistsException e) {
            result.addError(new FieldError("user", "email", e.getMessage()));
            return new ModelAndView("registrationPage", "user", user);
        }
        return new ModelAndView("redirect:/login");
    }

//    @GetMapping(value = "/sendEmail")
//    @ResponseBody
//    public Boolean sendEmail() {
//        return iEmailService.sendEmail();
//    }
}
