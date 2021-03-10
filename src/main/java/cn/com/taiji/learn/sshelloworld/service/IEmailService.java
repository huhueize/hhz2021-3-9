package cn.com.taiji.learn.sshelloworld.service;

import cn.com.taiji.learn.sshelloworld.domain.User;

public interface IEmailService {
    boolean sendEmail(User user);
}
