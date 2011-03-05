package com.mvp4g.example.client.company;

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

public class CompanyService_Proxy extends RemoteServiceProxy implements com.mvp4g.example.client.company.CompanyServiceAsync {
  private static final String REMOTE_SERVICE_INTERFACE_NAME = "com.mvp4g.example.client.company.CompanyService";
  private static final String SERIALIZATION_POLICY ="65F28491B74BE5DC78EB151F951848C1";
  private static final com.mvp4g.example.client.company.CompanyService_TypeSerializer SERIALIZER = new com.mvp4g.example.client.company.CompanyService_TypeSerializer();
  
  public CompanyService_Proxy() {
    super(GWT.getModuleBaseURL(),
      "company", 
      SERIALIZATION_POLICY, 
      SERIALIZER);
  }
  
  public void createCompany(com.mvp4g.example.client.company.bean.CompanyBean company, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.createCompany", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("createCompany");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.company.bean.CompanyBean/1983513559");
      streamWriter.writeObject(company);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.createCompany",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "CompanyService_Proxy.createCompany", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void deleteCompany(com.mvp4g.example.client.company.bean.CompanyBean company, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.deleteCompany", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("deleteCompany");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.company.bean.CompanyBean/1983513559");
      streamWriter.writeObject(company);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.deleteCompany",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "CompanyService_Proxy.deleteCompany", statsContext, payload, callback);
    } catch (SerializationException ex) {
      callback.onFailure(ex);
    }
  }
  
  public void getCompanies(int start, int end, com.google.gwt.user.client.rpc.AsyncCallback async) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.getCompanies", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("getCompanies");
      streamWriter.writeInt(2);
      streamWriter.writeString("I");
      streamWriter.writeString("I");
      streamWriter.writeInt(start);
      streamWriter.writeInt(end);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.getCompanies",  "requestSerialized"));
      doInvoke(ResponseReader.OBJECT, "CompanyService_Proxy.getCompanies", statsContext, payload, async);
    } catch (SerializationException ex) {
      async.onFailure(ex);
    }
  }
  
  public void updateCompany(com.mvp4g.example.client.company.bean.CompanyBean company, com.google.gwt.user.client.rpc.AsyncCallback callback) {
    RpcStatsContext statsContext = new RpcStatsContext();
    boolean toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.updateCompany", "begin"));
    SerializationStreamWriter streamWriter = createStreamWriter();
    // createStreamWriter() prepared the stream
    try {
      if (getRpcToken() != null) {
        streamWriter.writeObject(getRpcToken());
      }
      streamWriter.writeString(REMOTE_SERVICE_INTERFACE_NAME);
      streamWriter.writeString("updateCompany");
      streamWriter.writeInt(1);
      streamWriter.writeString("com.mvp4g.example.client.company.bean.CompanyBean/1983513559");
      streamWriter.writeObject(company);
      String payload = streamWriter.toString();
      toss = statsContext.isStatsAvailable() && statsContext.stats(statsContext.timeStat("CompanyService_Proxy.updateCompany",  "requestSerialized"));
      doInvoke(ResponseReader.VOID, "CompanyService_Proxy.updateCompany", statsContext, payload, callback);
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
