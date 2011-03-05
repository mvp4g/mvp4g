package com.mvp4g.example.client;

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

public class UserService_Proxy extends RemoteServiceProxy implements com.mvp4g.example.client.UserServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "com.mvp4g.example.client.UserService";
  private static final String SERIALIZATION_POLICY ="1EAECDF611BF674E3F36D9D2DA8F5352";
  private static final com.mvp4g.example.client.UserService_TypeSerializer SERIALIZER = new com.mvp4g.example.client.UserService_TypeSerializer();
  
  public UserService_Proxy() {
    super(GWT.getModuleBaseURL(),
      "userService", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void createUser(com.mvp4g.example.client.bean.UserBean user, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.createUser", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("createUser");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.bean.UserBean/4270107197");
      streamWriter.writeObject(user);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.createUser",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "UserService_Proxy.createUser", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void deleteUser(com.mvp4g.example.client.bean.UserBean user, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.deleteUser", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("deleteUser");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.bean.UserBean/4270107197");
      streamWriter.writeObject(user);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.deleteUser",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "UserService_Proxy.deleteUser", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getUsers(com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.getUsers", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getUsers");
      streamWriter.writeInt(0);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.getUsers",  "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "UserService_Proxy.getUsers", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void updateUser(com.mvp4g.example.client.bean.UserBean user, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.updateUser", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("updateUser");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.bean.UserBean/4270107197");
      streamWriter.writeObject(user);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.updateUser",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "UserService_Proxy.updateUser", statsContext, payload, callback);
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
