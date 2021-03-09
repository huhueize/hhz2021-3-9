package cn.com.taiji.learn.sshelloworld.controller;

import cn.com.taiji.learn.sshelloworld.dao.UserRepository;
import cn.com.taiji.learn.sshelloworld.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ModelAndView list() {
        Iterable<User> users = this.userRepository.findAll();
        return new ModelAndView("users/list", "users", users);
    }

    @GetMapping("{id}")
    public ModelAndView view(@PathVariable("id") Long id) {
        User user = this.userRepository.findUser(id);
        return new ModelAndView("users/view", "user", user);
    }

    @GetMapping(params = "form")
    public String createForm(@ModelAttribute User user) {
        return "users/form";
    }

    @PostMapping
    public ModelAndView create(@Valid User user, BindingResult result, RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return new ModelAndView("users/form", "formErrors", result.getAllErrors());
        }
        user = this.userRepository.save(user);
        redirect.addFlashAttribute("globalMessage", "Successfully created a new user");
        return new ModelAndView("redirect:/user/{user.id}", "user.id", user.getId());
    }

    @GetMapping(value = "delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.userRepository.deleteUser(id);
        return new ModelAndView("redirect:/");
    }

    @GetMapping("modify/{id}")
    public ModelAndView modifyForm(@PathVariable("id") Long id) {
        User user = this.userRepository.findUser(id);
        return new ModelAndView("users/form", "user", user);
    }

    @GetMapping("test/only-user")
    @ResponseBody
    public String onlyuser(){
        return "this is user";
    }

//  测试使用
    @GetMapping("test/foo")
    @ResponseBody
    public String foo() {

//        throw new RuntimeException("Expected exception in controller");
        return "404 not found";
    }

    @GetMapping("test/bar")
    @ResponseBody
    public String bar() {
        return "bar";
    }
}
