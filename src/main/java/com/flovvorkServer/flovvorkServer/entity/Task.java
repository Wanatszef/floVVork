package com.flovvorkServer.flovvorkServer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task")
public class Task
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idtask")
    private int idTask;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "task_start_file")
    private String taskStartFile;

    public int getIdTask() {
        return idTask;
    }

    public void setIdTask(int idTask) {
        this.idTask = idTask;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStartFile() {
        return taskStartFile;
    }

    public void setTaskStartFile(String taskStartFile) {
        this.taskStartFile = taskStartFile;
    }

    @Override
    public String toString() {
        return "Task{" +
                "idTask=" + idTask +
                ", taskName='" + taskName + '\'' +
                ", taskStartFile='" + taskStartFile + '\'' +
                '}';
    }

    public Task(String taskName, String taskStartFile) {
        this.taskName = taskName;
        this.taskStartFile = taskStartFile;
    }

    public Task()
    {}
}
