package cn.com.taiji.learn.sshelloworld.dao;

import cn.com.taiji.learn.sshelloworld.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

}
