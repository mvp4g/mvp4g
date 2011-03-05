package com.mvp4g.example.client.product;

import com.google.gwt.user.client.rpc.impl.RemoteServiceProxy;
import com.google.gwt.user.client.rpc.impl.ClientSerializationStreamWriter;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.impl.RequestCallbackAdapter.ResponseReader;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.RpcToken;
import com.google.gwt.user.client.rpc.RpcTokenException;
import com.google.gwt.core.client.impl.Impl;
import com.google.gwt.user.client.rpc.impl.RpcStatsContext;

public class ProductService_Proxy extends RemoteServiceProxy implements com.mvp4g.example.client.product.ProductServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "com.mvp4g.example.client.product.ProductService";
  private static final String SERIALIZATION_POLICY ="CACC187AD5A519924B95DC5C986169E6";
  private static final com.mvp4g.example.client.product.ProductService_TypeSerializer SERIALIZER = new com.mvp4g.example.client.product.ProductService_TypeSerializer();
  
  public ProductService_Proxy() {
    super(GWT.getModuleBaseURL(),
      "product", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void createProduct(com.mvp4g.example.client.product.bean.ProductBean product, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.createProduct", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("createProduct");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.product.bean.ProductBean/3234910311");
      streamWriter.writeObject(product);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.createProduct",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "ProductService_Proxy.createProduct", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void deleteProduct(com.mvp4g.example.client.product.bean.ProductBean product, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.deleteProduct", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("deleteProduct");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.product.bean.ProductBean/3234910311");
      streamWriter.writeObject(product);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.deleteProduct",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "ProductService_Proxy.deleteProduct", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getProducts(int start, int end, com.google.gwt.user.client.rpc.AsyncCallback async) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.getProducts", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getProducts");
      streamWriter.writeInt(2);
      streamWriter.writeString("I");
      streamWriter.writeString("I");
      streamWriter.writeInt(start);
      streamWriter.writeInt(end);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.getProducts",  "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "ProductService_Proxy.getProducts", statsContext, payload, async);
    } catch (SerializationException ex) {
      async.onFailure(ex);
    }
  }
  
  public void updateProduct(com.mvp4g.example.client.product.bean.ProductBean product, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.updateProduct", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("updateProduct");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.product.bean.ProductBean/3234910311");
      streamWriter.writeObject(product);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("ProductService_Proxy.updateProduct",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "ProductService_Proxy.updateProduct", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  @Override
  public SerializationStreamWriter createStreamWriter() {
    ClientSerializationStreamWriter toReturn =
      (ClientSerializationStreamWriter) super.createStreamWriter();
    if (getRpcToken() != null) {
      toReturn.addFlags(ClientSerializationStreamWriter.FLAG_RPC_TOKEN_INCLUDED);
    }
    return toReturn;
  }
}
