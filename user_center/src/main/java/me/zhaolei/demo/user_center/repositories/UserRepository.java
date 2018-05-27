package me.zhaolei.demo.user_center.repositories;

import me.zhaolei.demo.user_center.entity.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "users")
public interface UserRepository extends org.springframework.data.jpa.repository.JpaRepository<User, Long>, org.springframework.data.jpa.repository.JpaSpecificationExecutor<User>, java.io.Serializable {

    /**
     * 根据用户账号查询用户信息 缓存使用用户的账号作为KEY
     *
     * @param userCode 用户账号
     * @return User
     */
    @Cacheable(key = "#p0")
    User findByUserCode(String userCode);


    @CachePut(key = "#p0.userCode")
    @Override
    User save(User user);
}
