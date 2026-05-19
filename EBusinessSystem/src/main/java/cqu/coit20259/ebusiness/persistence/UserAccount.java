/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cqu.coit20259.ebusiness.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

/**
 * Represents a user account in the system.
 * This class stores login details and the user role.
 * 
 * @author Cardoso Pepe
 */
@Entity
@Table(name = "USER_ACCOUNTS")
public class UserAccount implements Serializable {

    @Id
    private String username;

    private String password;

    // Stores the user role, such as Admin or Customer.
    private String groupName;

    // Default constructor required by JPA.
    public UserAccount() {}

    // Returns the username.
    public String getUsername() { return username; }

    // Sets the username.
    public void setUsername(String username) { this.username = username; }

    // Returns the password.
    public String getPassword() { return password; }

    // Sets the password.
    public void setPassword(String password) { this.password = password; }

    // Returns the user role.
    public String getGroupName() { return groupName; }

    // Sets the user role.
    public void setGroupName(String groupName) { this.groupName = groupName; }
}