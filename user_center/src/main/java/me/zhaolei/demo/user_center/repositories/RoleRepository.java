package me.zhaolei.demo.user_center.repositories;

import me.zhaolei.demo.user_center.entity.Role;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import java.io.Serializable;
import java.util.List;

@Service
@CacheConfig(cacheNames = "roles")
public interface RoleRepository extends JpaRepository<Role, Long>,
        JpaSpecificationExecutor<Role>,
        Serializable {


    /**
     * 查询系统角色，使用角色编码作为KEY
     *
     * @param roleCode
     * @return
     */
    @Cacheable(key = "#p0")
    Role findByRoleCode(String roleCode);

    @CachePut(key = "#p0.roleCode")
    @Override
    Role save(Role role);


    /**
     * 根据用户PK查询用户角色信息,为了防止角色发生变动造成新角色无法读取，可以不存储缓存
     *
     * @param Pk_user
     * @return
     */

    @Query(value = "select * from sys_role r where r.dr=0 and r.pk_role in (select o.pk_role from sys_user_role o where o.dr=0 and o.pk_user = ?1 )", nativeQuery = true)
    List<Role> findSysRoleByPkUser(Long Pk_user);
}
