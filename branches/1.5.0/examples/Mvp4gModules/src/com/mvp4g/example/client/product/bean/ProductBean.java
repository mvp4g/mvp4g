package com.mvp4g.example.client.product.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProductBean
  implements IsSerializable {

  private int    id   = -1;
  private String name = null;

  public ProductBean() {
    super();
  }

  public ProductBean(String name) {
    super();
  }

  public ProductBean(int id,
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
