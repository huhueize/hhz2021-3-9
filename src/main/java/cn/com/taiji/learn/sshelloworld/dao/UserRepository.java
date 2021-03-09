package cn.com.taiji.learn.sshelloworld.dao;


import cn.com.taiji.learn.sshelloworld.domain.User;

public interface UserRepository {

    Iterable<User> findAll();

//  增加，修改
    User save(User user);
//  查找
    User findUser(Long id);
//  删除
    void deleteUser(Long id);

}
