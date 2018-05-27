package me.zhaolei.demo.user_center.repositories;

import me.zhaolei.demo.user_center.entity.UserRole;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "default")
public interface UserRoleRepository extends org.springframework.data.jpa.repository.JpaRepository<UserRole, Long>, org.springframework.data.jpa.repository.JpaSpecificationExecutor<UserRole>, java.io.Serializable {

    @Cacheable(key = "#p0")
    UserRole findByPkUser(String userid);


    @CachePut(key = "#p0.pkUser")
    @Override
    UserRole save(UserRole userrole);
}
