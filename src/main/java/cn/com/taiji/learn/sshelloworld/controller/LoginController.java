package cn.com.taiji.learn.sshelloworld.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/login11")
    public String login(){
        return "loginPage";
    }

    @GetMapping("/logout")
    public String logout(){
        return "loginPage";
    }

}
