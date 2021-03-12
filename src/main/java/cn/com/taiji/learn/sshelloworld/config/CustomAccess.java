package cn.com.taiji.learn.sshelloworld.config;

import cn.com.taiji.learn.sshelloworld.dao.PermissionRepository;
import cn.com.taiji.learn.sshelloworld.dao.RoleRepository;
import cn.com.taiji.learn.sshelloworld.dao.UserRepository;
import cn.com.taiji.learn.sshelloworld.domain.Permission;
import cn.com.taiji.learn.sshelloworld.domain.Role;
import cn.com.taiji.learn.sshelloworld.domain.User;
import cn.hutool.core.collection.CollectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomAccess {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public Boolean a(HttpServletRequest request, Authentication authentication) {
        // 1. 先判断当前的用户有没有认证过
        // 获取到当前用户
        Object principal = authentication.getPrincipal();
        if (principal == null || "anonymousUser".equals(principal)) {
            //说明么有登录，退出
            return false;
        }

        //2.根据Url查询Permission
        String url = request.getRequestURI();
        //获取当前URL的角色集合
        List<Permission> permissionList = permissionRepository.findAllByUrl(url);

        if (permissionList.size() < 1) {
            //白名单机制   没有针对此url配置权限，直接通过
            return true;
        }

        //3.获取url对应的角色Role
        List<Role> urlRoleList = new ArrayList<>();
        permissionList.forEach(permission -> {
            urlRoleList.addAll(permission.getRoles());
        });

        //4. 获取到当前用户对应的角色Role
        Collection<? extends GrantedAuthority> userRoleList = authentication.getAuthorities();


        for(Role r: urlRoleList){
            for(GrantedAuthority g: userRoleList){
                if(r.getName().equals(g.getAuthority())){
                    return true;
                }
            }
        }
        return false;

//        //5. urlRoleList 和 userRoleList存在交集的话，就说明用户拥有访问此url的权限
//        if (CollectionUtil.isEmpty(CollectionUtil.intersection(urlRoleList, userRoleList))) {
//            return false;
//        }
//
//        return true;

        //1. session的问题
        //2. permission的jpa怎么根据roles查permission
        //3. CustomUserDetailsService没有结合起来

    }
}
