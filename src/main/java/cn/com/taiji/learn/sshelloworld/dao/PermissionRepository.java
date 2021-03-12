package cn.com.taiji.learn.sshelloworld.dao;

import cn.com.taiji.learn.sshelloworld.domain.Permission;
import cn.com.taiji.learn.sshelloworld.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@EnableJpaRepositories
@Repository
public interface PermissionRepository extends JpaRepository<Permission,Long> {

//    List<Permission> findAllByRoles(Set<Role> roles);

    List<Permission> findAllByUrl(String url);
}
