package com.skarp.prio.user;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class User {
    private String name;
    private String initials;
    private LocalDate dateRegistered;
    private LocalDate dateResigned;
    private long employmentTime; //in days
    private UserState state;
    private UserPrivilege userPrivilege;

    public User(String name, String initials){
        this.state = UserState.Idle;
        this.dateRegistered = LocalDate.now();
        this.userPrivilege = UserPrivilege.VIEW_ONLY;
        this.name = name;
        this.initials = initials;
    }

    public String getName() {
        return name;
    }

    public String getInitials() {
        return initials;
    }

    public LocalDate getDateRegistered() {
        return dateRegistered;
    }

    public LocalDate getDateResigned() {
        return dateResigned;
    }

    public long getEmploymentTime() {
        return employmentTime;
    }

    public UserState getState() {
        return state;
    }

    public UserPrivilege getUserPrivilege() {
        return userPrivilege;
    }

    public void setUserPrivilege(UserPrivilege userPrivilege){
        this.userPrivilege = userPrivilege;
    }

    public void demoteUser(User user){
        if (hasFullPrivilege()){
            user.setUserPrivilege(UserPrivilege.VIEW_ONLY);
            user.dateResigned = LocalDate.now();
            user.employmentTime = ChronoUnit.DAYS.between(user.dateRegistered, user.dateResigned);
        }else{throw new MissingPrivilegeException();}
    }

    private Boolean hasFullPrivilege(){
        if(this.userPrivilege == UserPrivilege.FULL_ACCESS){
            return true;
        }
        else{throw new MissingPrivilegeException();}
    }
    private Boolean hasSemiPrivilege(){
        if(this.userPrivilege == UserPrivilege.FULL_ACCESS || this.userPrivilege == UserPrivilege.SEMI_ACCESS) {
            return true;
        }else {throw new MissingPrivilegeException();}
    }
}
