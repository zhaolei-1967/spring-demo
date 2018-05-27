package me.zhaolei.demo.user_center.startup;

import lombok.extern.slf4j.Slf4j;
import me.zhaolei.demo.user_center.entity.Role;
import me.zhaolei.demo.user_center.repositories.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    RoleRepository rp;

    public DataLoader(RoleRepository rp) {
        this.rp = rp;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role r = new Role();
        r.setRoleCode("ROLE_ADMIN");
        r.setPkRole(1L);
        rp.save(r);

        r = new Role();
        r.setRoleCode("ROLE_USER");
        r.setPkRole(2L);
        rp.save(r);
    }
}
