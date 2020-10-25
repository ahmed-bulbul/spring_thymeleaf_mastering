package com.thikthak.app.repository.test;

import com.thikthak.app.domain.auth.UserRole;
import com.thikthak.app.domain.test.ServiceItems;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


@Repository
public interface ServiceItemRepository extends JpaRepository<ServiceItems, Long> {

}
