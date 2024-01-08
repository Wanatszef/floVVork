package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Document;
import com.flovvorkServer.flovvorkServer.entity.Task;
import com.flovvorkServer.flovvorkServer.entity.TaskAccess;
import com.flovvorkServer.flovvorkServer.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskCreatorRepository extends JpaRepository<TaskAccess, Integer>
{
    List<TaskAccess> findDistinctByUserId(User user);

    TaskAccess findByTaskAndUserId(Task task, User userId);

    TaskAccess findByUserIdAndTask(User user, Task task);
}
