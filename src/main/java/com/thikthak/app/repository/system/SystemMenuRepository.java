package com.thikthak.app.repository.system;

import com.thikthak.app.domain.system.SystemMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemMenuRepository extends JpaRepository<SystemMenu, Long> {

    List<SystemMenu> findAllByParentMenu(SystemMenu systemMenu); // pass systemMenu null
    List<SystemMenu> getAllByParentMenuIsNull();

}
