package com.mvp4g.example.client.company.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CompanyBean
  implements IsSerializable {

  private int    id   = -1;
  private String name = null;

  public CompanyBean() {
    super();
  }

  public CompanyBean(String name) {
    super();
  }

  public CompanyBean(int id,
                     String name) {
    super();
    this.id = id;
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

}
