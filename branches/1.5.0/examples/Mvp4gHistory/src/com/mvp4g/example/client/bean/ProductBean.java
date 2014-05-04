package com.mvp4g.example.client.bean;

public class ProductBean
  extends BasicBean {

  private String price = null;

  public ProductBean() {
    //nothing to do
  }

  public ProductBean(String id,
                     String title,
                     String description,
                     String price) {
    super(id,
          title,
          description);
    setPrice(price);
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

}
