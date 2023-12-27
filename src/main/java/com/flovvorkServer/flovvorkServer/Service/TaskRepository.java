package com.flovvorkServer.flovvorkServer.Service;

import com.flovvorkServer.flovvorkServer.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task,Integer>
{
    Task findByIdTask(int taskID);

}
