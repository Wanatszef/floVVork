package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long>
{
    Role findByRoleID(long id);
}
