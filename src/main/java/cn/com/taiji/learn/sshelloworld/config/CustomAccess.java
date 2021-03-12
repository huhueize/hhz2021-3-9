package cn.com.taiji.learn.sshelloworld.config;

import cn.com.taiji.learn.sshelloworld.dao.PermissionRepository;
import cn.com.taiji.learn.sshelloworld.dao.RoleRepository;
import cn.com.taiji.learn.sshelloworld.dao.UserRepository;
import cn.com.taiji.learn.sshelloworld.domain.Permission;
import cn.com.taiji.learn.sshelloworld.domain.Role;
import cn.com.taiji.learn.sshelloworld.domain.User;
import cn.com.taiji.learn.sshelloworld.service.IUserService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.SetUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Component
public class CustomAccess {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Boolean hhz(HttpServletRequest request, Authentication authentication){
        // 1. 先判断当前的用户有没有认证过
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)) {
            //说明么有登录，退出
            return false;
        }

        //TODO role user permission 实现动态鉴权
        //1. 获取到当前用户
        String email = authentication.getName();
        User user = userRepository.findByEmail(email);

        //2. 根据Url查询Permission
        String url = request.getRequestURI();
        List<Permission> permissionList = permissionRepository.findAllByUrl(url);

        if(permissionList.size()<1){
            //白名单机制   没有针对此url配置权限，直接通过
            return true;
        }
        //判断当前用户的角色Role，是否有访问当前URL的资格
        // user所属的角色列表，是否存储在一个角色属于Permission中的角色
        for(Role role:user.getRoles()){
            for(Permission permission:permissionList){
                for(Role roleInP : permission.getRoles()){
                    if(roleInP.equals(role)){
                        //证明匹配成功，证明用户拥有访问次url的权限
                        return true;
                    }
                }
            }
        }

        return false;

        //判断当前用户的角色Role，是否有访问当前URL的资格
//        List->Set
//        Set<Permission> permissions = permissionRepository.findAllByRoles(new HashSet(user.getRoles()));

        //判断当前用户的角色Role，是否有访问当前URL的资格
//        return false;
    }
}
