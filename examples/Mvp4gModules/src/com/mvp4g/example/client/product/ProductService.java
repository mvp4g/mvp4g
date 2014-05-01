package com.mvp4g.example.client.product;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.mvp4g.example.client.product.bean.ProductBean;

import java.util.List;

@RemoteServiceRelativePath("product")
public interface ProductService
  extends RemoteService {

  public List<ProductBean> getProducts(int start,
                                       int end);

  public void createProduct(ProductBean product);

  public void deleteProduct(ProductBean product);

  public void updateProduct(ProductBean product);

}
