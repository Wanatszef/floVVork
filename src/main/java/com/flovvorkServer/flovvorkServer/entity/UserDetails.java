package com.flovvorkServer.flovvorkServer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "user_details")
public class UserDetails
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_details_id")
    private int userDetailsID;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne (cascade = CascadeType.DETACH)
    @JoinColumn(name = "role_id")
    private Role roleID;

    public UserDetails() {
    }

    public UserDetails(String firstName, String secondName, String email, String phoneNumber, Role roleID) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleID = roleID;
    }

    public int getUserDetailsID() {
        return userDetailsID;
    }

    public void setUserDetailsID(int userDetailsID) {
        this.userDetailsID = userDetailsID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRoleID() {
        return roleID;
    }

    public void setRoleID(Role roleID) {
        this.roleID = roleID;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "userDetailsID=" + userDetailsID +
                ", firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", roleID=" + roleID +
                '}';
    }
}
