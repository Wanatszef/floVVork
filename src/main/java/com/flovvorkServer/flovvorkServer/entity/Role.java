package com.flovvorkServer.flovvorkServer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
    private int roleID;

    @Column(name = "name")
    private String name;
    @Column(name = "hierarchy")
    private int hierarchy;

    public Role(String name, int hierarchy) {
        this.name = name;
        this.hierarchy = hierarchy;
    }

    public Role() {

    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHierarchy() {
        return hierarchy;
    }

    public void setHierarchy(int hierarchy) {
        this.hierarchy = hierarchy;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleID=" + roleID +
                ", name='" + name + '\'' +
                ", hierarchy=" + hierarchy +
                '}';
    }
}
