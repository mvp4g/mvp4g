package com.mvp4g.example.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.List;

public class UserBean
    implements IsSerializable {

  private long id;

  private String       firstName  = null;
  private String       lastName   = null;
  private String       email      = null;
  private String       username   = null;
  private String       password   = null;
  private String       department = null;
  private List<String> roles      = null;


  public UserBean() {
    roles = new ArrayList<String>();
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  public List<String> getRoles() {
    return roles;
  }

  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

  public void copy(UserBean user) {
    this.firstName = user.firstName;
    this.lastName = user.lastName;
    this.email = user.email;
    this.username = user.username;
    this.password = user.password;
    this.department = user.department;
    this.roles = user.roles;
  }

  @Override
  public String toString() {
    return "UserBean{" + "id=" + id + ", firstName='" + (firstName == null ? "" : firstName) + '\'' + ", lastName='" + (lastName == null ? "" : lastName) + '\'' + ", email='" + (email == null ? "" : email) + '\'' + ", username='" + (username == null ? "" : username) + '\'' + ", password='" + (password == null ? "" : password) + '\'' + ", department='" + (department == null ? "" : department) + '\'' + ", roles=" + (roles == null ? "" : roles.toString()) + '}';
  }
}
