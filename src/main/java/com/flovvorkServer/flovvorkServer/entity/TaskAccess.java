package com.flovvorkServer.flovvorkServer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "task_access")
public class TaskAccess
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_access_id")
    private int taskAccessID;


    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "id_task")
    private Task task;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public int getTaskAccessID() {
        return taskAccessID;
    }

    public void setTaskAccessID(int taskAccessID) {
        this.taskAccessID = taskAccessID;
    }

    public TaskAccess() {
    }

    public TaskAccess(int taskAccessID, User userId, Task task) {
        this.taskAccessID = taskAccessID;
        this.userId = userId;
        this.task = task;
    }

    @Override
    public String toString() {
        return "TaskAccess{" +
                "taskAccessID=" + taskAccessID +
                ", userId=" + userId +
                ", task=" + task +
                '}';
    }
}
