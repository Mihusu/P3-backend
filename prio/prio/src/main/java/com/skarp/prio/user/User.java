package com.skarp.prio.user;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.springframework.data.annotation.Id;


public class User {
    @Id
    private String id;
    private String username;
    private String password; //password is hashed
    private String initials;
    private LocalDate dateRegistered;
    private LocalDate dateResigned;
    private long employmentTime; //in days
    private UserState state;
    private UserPrivilege userPrivilege;
    private int counter;

    public User(String username, String password){
        if(!(SHA3.hashPassword("").split("").length == password.split("").length)){
            this.password = SHA3.hashPassword(password);
            this.state = UserState.IDLE;
            this.dateRegistered = LocalDate.now();
            this.userPrivilege = UserPrivilege.UNASSIGNED;
            this.username = username;
            this.initials = generateInitials(username);
            this.counter = 0;
        }else{
            this.password = password;
            this.username = username;
        }


    }

    public void setPassword(String password) {
        this.password = SHA3.hashPassword(password);
    }

    public void resetDateResigned() {
        this.dateResigned = null;
    }

    public String getId() {
        return id;
    }

    public int getCounter() {
        return counter;
    }
    public void compoundCounter(){
        this.counter++;
    }

    public String generateInitials(String username){
        String initials = "";
        for (String s : username.split(" ")) {
            initials+=s.split("")[0];
        }
        return initials.toUpperCase();
    }
    public boolean checkCredentials(String inputName, String inputPassword){
        if(this.username.equals(inputName) && this.password.equals(SHA3.hashPassword(inputPassword))){
            return true;
        }else return false;
    }

    public boolean checkPassword(String inputPassword){
        if(this.password.equals(SHA3.hashPassword(inputPassword))){
            return true;
        }else return false;
    }
    public String getPassword(){return this.password;}
    public String getUsername() {
        return username;
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
        this.employmentTime = ChronoUnit.DAYS.between(this.dateRegistered, LocalDate.now());
        return employmentTime;
    }

    public UserState getState() {
        return state;
    }

    public void setState(UserState state) {
        this.state = state;
    }

    public UserPrivilege getUserPrivilege() {
        return userPrivilege;
    }

    public void setUserPrivilege(UserPrivilege userPrivilege){
        this.userPrivilege = userPrivilege;
    }
    public void resign(){
        this.userPrivilege = UserPrivilege.UNASSIGNED;
        this.dateResigned = LocalDate.now();
        this.employmentTime = ChronoUnit.DAYS.between(this.dateRegistered, this.dateResigned);
    }

    public void demoteUser(User user){
        if (hasFullPrivilege()){
            user.setUserPrivilege(UserPrivilege.UNASSIGNED);
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
