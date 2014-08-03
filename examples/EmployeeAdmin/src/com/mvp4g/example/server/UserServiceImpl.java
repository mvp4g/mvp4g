package com.mvp4g.example.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.service.UserService;
import com.mvp4g.example.shared.dto.UserBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class UserServiceImpl
    extends RemoteServiceServlet
    implements UserService,
               Constants {

  static private final String[] FIRST_NAMES = {"Karim", "Cristiano", "Iker", "Sergio", "Philip", "Mehsut", "Manuel"};
  static private final String[] LAST_NAMES  = {"Benzema", "Ronaldo", "Casillas", "Ramos", "Lahm", "Oezil", "Neuer"};
  private static List<UserBean> userList;
  static private int NB_USERS = 32;

  public List<UserBean> getUsers() {
    if (userList == null) {
      userList = this.createUserList();
    }
    return userList;
  }

  private List<UserBean> createUserList() {
    List<UserBean> users = new ArrayList<UserBean>();
    UserBean user = null;
    String firstName = null;
    String lastName = null;
    String username = null;
    List<String> roles = null;

    long id = 0;

    Random random = new Random();

    for (int i = 0; i < NB_USERS; i++) {
      user = new UserBean();

      user.setId(i);

      firstName = FIRST_NAMES[i % FIRST_NAMES.length];
      user.setFirstName(firstName);

      lastName = LAST_NAMES[i % LAST_NAMES.length];
      user.setLastName(lastName);

      username = (firstName.substring(0,
                                      1) + lastName
      ).toLowerCase();
      user.setUsername(username);

      user.setEmail(username + "@mvp4g.com");

      user.setDepartment(DEPARTMENTS[i % DEPARTMENTS.length]);

      user.setPassword("1234");

      roles = new ArrayList<String>();
      int nbRoles = random.nextInt(ROLES.length);
      String role = null;
      for (int j = 0; j < nbRoles; j++) {
        role = ROLES[random.nextInt(ROLES.length)];
        if (!roles.contains(role)) {
          roles.add(role);
        }
      }
      user.setRoles(roles);

      users.add(user);
    }

    return users;
  }

  public void deleteUser(UserBean user) {
    for (UserBean userBean : userList) {
      if (userBean.getId() == user.getId()) {
        userList.remove(userBean);
        return;
      }
    }
  }

  public UserBean createUser(UserBean user) {
    long lastId = 0;
    for (int i = 0; i < userList.size(); i++) {
      if (lastId < userList.get(i).getId()) {
        lastId = userList.get(i).getId();
      }
    }
    user.setId(lastId++);
    userList.add(user);
    return user;
  }

  public UserBean updateUser(UserBean user) {
    for (int i = 0; i < userList.size(); i++) {
      if (user.getId() == userList.get(i).getId()) {
        userList.set(i, user);
        return user;
      }
    }
    return user;
  }
}
