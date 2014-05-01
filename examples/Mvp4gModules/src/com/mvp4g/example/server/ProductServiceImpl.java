package com.mvp4g.example.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mvp4g.example.client.product.ProductService;
import com.mvp4g.example.client.product.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl
  extends RemoteServiceServlet
  implements ProductService {

  /**
   *
   */
  private static final long serialVersionUID = 4546417863195659071L;

  public void deleteProduct(ProductBean product) {
    // TODO Auto-generated method stub
  }

  public List<ProductBean> getProducts(int start,
                                       int end) {
    List<ProductBean> companies = new ArrayList<ProductBean>();
    for (int i = start; i <= end; i++) {
      companies.add(new ProductBean(i,
                                    "Product " + i));
    }
    return companies;
  }

  public void updateProduct(ProductBean product) {
    // TODO Auto-generated method stub
  }

  public void createProduct(ProductBean product) {
    // TODO Auto-generated method stub
  }

}
