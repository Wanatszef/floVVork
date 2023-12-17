package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.TaskAccess;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCreatorRepository extends JpaRepository<TaskAccess, Integer>
{
    List<TaskAccess> findDistinctByUserId(User user);
}
