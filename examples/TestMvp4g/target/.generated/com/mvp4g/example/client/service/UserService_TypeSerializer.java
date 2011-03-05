package com.mvp4g.example.client.service;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.user.client.rpc.impl.TypeHandler;
import java.util.HashMap;
import java.util.Map;
import com.google.gwt.core.client.GwtScriptOnly;

public class UserService_TypeSerializer extends com.google.gwt.user.client.rpc.impl.SerializerBase {
  private static final Map<String, String> methodMapJava;
  private static final MethodMap methodMapNative;
  private static final Map<String, String> signatureMapJava;
  private static final JsArrayString signatureMapNative;
  
  static {
    if (GWT.isScript()) {
      methodMapJava = null;
      methodMapNative = loadMethodsNative();
      signatureMapJava = null;
      signatureMapNative = loadSignaturesNative();
    } else {
      methodMapJava = loadMethodsJava();
      methodMapNative = null;
      signatureMapJava = loadSignaturesJava();
      signatureMapNative = null;
    }
  }
  
  @SuppressWarnings("deprecation")
  private static Map<String, String> loadMethodsJava() {
    Map<String, String> result = new HashMap<String, String>();
    result.put("com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533", "com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException_FieldSerializer");
    result.put("com.mvp4g.example.client.bean.UserBean/4130032414", "com.mvp4g.example.client.bean.UserBean_FieldSerializer");
    result.put("java.lang.Integer/3438268394", "com.google.gwt.user.client.rpc.core.java.lang.Integer_FieldSerializer");
    result.put("java.lang.String/2004016611", "com.google.gwt.user.client.rpc.core.java.lang.String_FieldSerializer");
    return result;
  }
  
  @SuppressWarnings("deprecation")
  @GwtScriptOnly
  private static native MethodMap loadMethodsNative() /*-{
    var result = {};
    result["com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533"] = [
        @com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lcom/google/gwt/user/client/rpc/IncompatibleRemoteServiceException;),
        @com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Lcom/google/gwt/user/client/rpc/IncompatibleRemoteServiceException;)
      ];
    
    result["com.mvp4g.example.client.bean.UserBean/4130032414"] = [
        @com.mvp4g.example.client.bean.UserBean_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.mvp4g.example.client.bean.UserBean_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lcom/mvp4g/example/client/bean/UserBean;),
        @com.mvp4g.example.client.bean.UserBean_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Lcom/mvp4g/example/client/bean/UserBean;)
      ];
    
    result["java.lang.Integer/3438268394"] = [
        @com.google.gwt.user.client.rpc.core.java.lang.Integer_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.Integer_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/lang/Integer;),
        @com.google.gwt.user.client.rpc.core.java.lang.Integer_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/lang/Integer;)
      ];
    
    result["java.lang.String/2004016611"] = [
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/lang/String;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/lang/String;)
      ];
    
    return result;
  }-*/;
  
  @SuppressWarnings("deprecation")
  private static Map<String, String> loadSignaturesJava() {
    Map<String, String> result = new HashMap<String, String>();
    result.put("com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException", "com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533");
    result.put("com.mvp4g.example.client.bean.UserBean", "com.mvp4g.example.client.bean.UserBean/4130032414");
    result.put("java.lang.Integer", "java.lang.Integer/3438268394");
    result.put("java.lang.String", "java.lang.String/2004016611");
    return result;
  }
  
  @SuppressWarnings("deprecation")
  @GwtScriptOnly
  private static native JsArrayString loadSignaturesNative() /*-{
    var result = [];
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException::class)] = "com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@com.mvp4g.example.client.bean.UserBean::class)] = "com.mvp4g.example.client.bean.UserBean/4130032414";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.lang.Integer::class)] = "java.lang.Integer/3438268394";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.lang.String::class)] = "java.lang.String/2004016611";
    return result;
  }-*/;
  
  public UserService_TypeSerializer() {
    super(methodMapJava, methodMapNative, signatureMapJava, signatureMapNative);
  }
  
}
