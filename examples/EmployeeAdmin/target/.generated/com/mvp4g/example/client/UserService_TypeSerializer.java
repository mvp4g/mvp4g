package com.mvp4g.example.client;

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
    result.put("com.mvp4g.example.client.bean.UserBean/4270107197", "com.mvp4g.example.client.bean.UserBean_FieldSerializer");
    result.put("[Lcom.mvp4g.example.client.bean.UserBean;/930155444", "com.mvp4g.example.client.bean.UserBean_Array_Rank_1_FieldSerializer");
    result.put("java.lang.String/2004016611", "com.google.gwt.user.client.rpc.core.java.lang.String_FieldSerializer");
    result.put("[Ljava.lang.String;/2600011424", "com.google.gwt.user.client.rpc.core.java.lang.String_Array_Rank_1_FieldSerializer");
    result.put("java.util.ArrayList/3821976829", "com.google.gwt.user.client.rpc.core.java.util.ArrayList_FieldSerializer");
    result.put("java.util.Arrays$ArrayList/1243019747", "com.google.gwt.user.client.rpc.core.java.util.Arrays_ArrayList_FieldSerializer");
    result.put("java.util.Collections$EmptyList/3012082351", "com.google.gwt.user.client.rpc.core.java.util.Collections_EmptyList_FieldSerializer");
    result.put("java.util.Collections$SingletonList/833432284", "com.google.gwt.user.client.rpc.core.java.util.Collections_SingletonList_FieldSerializer");
    result.put("java.util.LinkedList/1060625595", "com.google.gwt.user.client.rpc.core.java.util.LinkedList_FieldSerializer");
    result.put("java.util.Stack/1031431137", "com.google.gwt.user.client.rpc.core.java.util.Stack_FieldSerializer");
    result.put("java.util.Vector/3125574444", "com.google.gwt.user.client.rpc.core.java.util.Vector_FieldSerializer");
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
    
    result["com.mvp4g.example.client.bean.UserBean/4270107197"] = [
        @com.mvp4g.example.client.bean.UserBean_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.mvp4g.example.client.bean.UserBean_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Lcom/mvp4g/example/client/bean/UserBean;),
        @com.mvp4g.example.client.bean.UserBean_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Lcom/mvp4g/example/client/bean/UserBean;)
      ];
    
    result["[Lcom.mvp4g.example.client.bean.UserBean;/930155444"] = [
        @com.mvp4g.example.client.bean.UserBean_Array_Rank_1_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.mvp4g.example.client.bean.UserBean_Array_Rank_1_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;[Lcom/mvp4g/example/client/bean/UserBean;),
      ];
    
    result["java.lang.String/2004016611"] = [
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/lang/String;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/lang/String;)
      ];
    
    result["[Ljava.lang.String;/2600011424"] = [
        @com.google.gwt.user.client.rpc.core.java.lang.String_Array_Rank_1_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_Array_Rank_1_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;[Ljava/lang/String;),
        @com.google.gwt.user.client.rpc.core.java.lang.String_Array_Rank_1_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;[Ljava/lang/String;)
      ];
    
    result["java.util.ArrayList/3821976829"] = [
        @com.google.gwt.user.client.rpc.core.java.util.ArrayList_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.ArrayList_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/ArrayList;),
        @com.google.gwt.user.client.rpc.core.java.util.ArrayList_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/ArrayList;)
      ];
    
    result["java.util.Arrays$ArrayList/1243019747"] = [
        @com.google.gwt.user.client.rpc.core.java.util.Arrays.ArrayList_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.Arrays.ArrayList_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/List;),
        @com.google.gwt.user.client.rpc.core.java.util.Arrays.ArrayList_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/List;)
      ];
    
    result["java.util.Collections$EmptyList/3012082351"] = [
        @com.google.gwt.user.client.rpc.core.java.util.Collections.EmptyList_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.Collections.EmptyList_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/List;),
        @com.google.gwt.user.client.rpc.core.java.util.Collections.EmptyList_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/List;)
      ];
    
    result["java.util.Collections$SingletonList/833432284"] = [
        @com.google.gwt.user.client.rpc.core.java.util.Collections.SingletonList_CustomFieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.Collections.SingletonList_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/List;),
        @com.google.gwt.user.client.rpc.core.java.util.Collections.SingletonList_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/List;)
      ];
    
    result["java.util.LinkedList/1060625595"] = [
        @com.google.gwt.user.client.rpc.core.java.util.LinkedList_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.LinkedList_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/LinkedList;),
        @com.google.gwt.user.client.rpc.core.java.util.LinkedList_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/LinkedList;)
      ];
    
    result["java.util.Stack/1031431137"] = [
        @com.google.gwt.user.client.rpc.core.java.util.Stack_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.Stack_FieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/Stack;),
        @com.google.gwt.user.client.rpc.core.java.util.Stack_FieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/Stack;)
      ];
    
    result["java.util.Vector/3125574444"] = [
        @com.google.gwt.user.client.rpc.core.java.util.Vector_FieldSerializer::instantiate(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;),
        @com.google.gwt.user.client.rpc.core.java.util.Vector_CustomFieldSerializer::deserialize(Lcom/google/gwt/user/client/rpc/SerializationStreamReader;Ljava/util/Vector;),
        @com.google.gwt.user.client.rpc.core.java.util.Vector_CustomFieldSerializer::serialize(Lcom/google/gwt/user/client/rpc/SerializationStreamWriter;Ljava/util/Vector;)
      ];
    
    return result;
  }-*/;
  
  @SuppressWarnings("deprecation")
  private static Map<String, String> loadSignaturesJava() {
    Map<String, String> result = new HashMap<String, String>();
    result.put("com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException", "com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533");
    result.put("com.mvp4g.example.client.bean.UserBean", "com.mvp4g.example.client.bean.UserBean/4270107197");
    result.put("[Lcom.mvp4g.example.client.bean.UserBean;", "[Lcom.mvp4g.example.client.bean.UserBean;/930155444");
    result.put("java.lang.String", "java.lang.String/2004016611");
    result.put("[Ljava.lang.String;", "[Ljava.lang.String;/2600011424");
    result.put("java.util.ArrayList", "java.util.ArrayList/3821976829");
    result.put(com.google.gwt.user.client.rpc.core.java.util.Arrays.ArrayList_CustomFieldSerializer.concreteType(), "java.util.Arrays$ArrayList/1243019747");
    result.put(com.google.gwt.user.client.rpc.core.java.util.Collections.EmptyList_CustomFieldSerializer.concreteType(), "java.util.Collections$EmptyList/3012082351");
    result.put(com.google.gwt.user.client.rpc.core.java.util.Collections.SingletonList_CustomFieldSerializer.concreteType(), "java.util.Collections$SingletonList/833432284");
    result.put("java.util.LinkedList", "java.util.LinkedList/1060625595");
    result.put("java.util.Stack", "java.util.Stack/1031431137");
    result.put("java.util.Vector", "java.util.Vector/3125574444");
    return result;
  }
  
  @SuppressWarnings("deprecation")
  @GwtScriptOnly
  private static native JsArrayString loadSignaturesNative() /*-{
    var result = [];
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException::class)] = "com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException/3936916533";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@com.mvp4g.example.client.bean.UserBean::class)] = "com.mvp4g.example.client.bean.UserBean/4270107197";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@com.mvp4g.example.client.bean.UserBean[]::class)] = "[Lcom.mvp4g.example.client.bean.UserBean;/930155444";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.lang.String::class)] = "java.lang.String/2004016611";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.lang.String[]::class)] = "[Ljava.lang.String;/2600011424";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.util.ArrayList::class)] = "java.util.ArrayList/3821976829";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.util.Arrays$ArrayList::class)] = "java.util.Arrays$ArrayList/1243019747";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.util.Collections$EmptyList::class)] = "java.util.Collections$EmptyList/3012082351";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.util.Collections$SingletonList::class)] = "java.util.Collections$SingletonList/833432284";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.util.LinkedList::class)] = "java.util.LinkedList/1060625595";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.util.Stack::class)] = "java.util.Stack/1031431137";
    result[@com.google.gwt.core.client.impl.Impl::getHashCode(Ljava/lang/Object;)(@java.util.Vector::class)] = "java.util.Vector/3125574444";
    return result;
  }-*/;
  
  public UserService_TypeSerializer() {
    super(methodMapJava, methodMapNative, signatureMapJava, signatureMapNative);
  }
  
}
