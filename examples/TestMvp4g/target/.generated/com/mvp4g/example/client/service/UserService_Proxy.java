package com.mvp4g.example.client.service;

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

public class UserService_Proxy extends RemoteServiceProxy implements com.mvp4g.example.client.service.UserServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "com.mvp4g.example.client.service.UserService";
  private static final String SERIALIZATION_POLICY ="1A40AE2ABF0E648851A3FF96B23CC995";
  private static final com.mvp4g.example.client.service.UserService_TypeSerializer SERIALIZER = new com.mvp4g.example.client.service.UserService_TypeSerializer();
  
  public UserService_Proxy() {
    super(GWT.getModuleBaseURL(),
      "users.rpc", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void create(com.mvp4g.example.client.bean.UserBean user, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.create", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("create");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.bean.UserBean/4130032414");
      streamWriter.writeObject(user);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("UserService_Proxy.create",  "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "UserService_Proxy.create", statsContext, payload, callback);
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
