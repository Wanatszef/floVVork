package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Role;
import com.flovvorkServer.flovvorkServer.entity.Task;
import com.flovvorkServer.flovvorkServer.entity.User;
import com.flovvorkServer.flovvorkServer.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,Long>
{
    UserDetails findByRoleID(Role roleID);
}
