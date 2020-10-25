package com.thikthak.app.repository.auth;

import com.thikthak.app.domain.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //public interface UserRepository extends CrudRepository<User, Long> {

    User getUserByUsername(String username);
}

