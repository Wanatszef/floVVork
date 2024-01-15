package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Role;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.boot.logging.logback.RootLogLevelConfigurator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>
{
    Role findByRoleID(long id);

    @Query("SELECT m FROM Role m")
    List<Role> findAll();
}
