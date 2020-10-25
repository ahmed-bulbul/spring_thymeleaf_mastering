package com.thikthak.app.repository.auth;

import com.thikthak.app.domain.auth.RequestUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestUrlRepository extends JpaRepository<RequestUrl, Long> {
}
