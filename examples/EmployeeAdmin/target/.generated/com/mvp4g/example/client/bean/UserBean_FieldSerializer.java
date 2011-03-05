package com.mvp4g.example.client.bean;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

@SuppressWarnings("deprecation")
public class UserBean_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getDepartment(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::department;
  }-*/;
  
  private static native void  setDepartment(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::department = value;
  }-*/;
  
  private static native java.lang.String getEmail(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::email;
  }-*/;
  
  private static native void  setEmail(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::email = value;
  }-*/;
  
  private static native java.lang.String getFirstName(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::firstName;
  }-*/;
  
  private static native void  setFirstName(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::firstName = value;
  }-*/;
  
  private static native java.lang.String getLastName(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::lastName;
  }-*/;
  
  private static native void  setLastName(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::lastName = value;
  }-*/;
  
  private static native java.lang.String getPassword(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::password;
  }-*/;
  
  private static native void  setPassword(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::password = value;
  }-*/;
  
  private static native java.util.List getRoles(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::roles;
  }-*/;
  
  private static native void  setRoles(com.mvp4g.example.client.bean.UserBean instance, java.util.List value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::roles = value;
  }-*/;
  
  private static native java.lang.String getUsername(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::username;
  }-*/;
  
  private static native void  setUsername(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::username = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.mvp4g.example.client.bean.UserBean instance) throws SerializationException {
    setDepartment(instance, streamReader.readString());
    setEmail(instance, streamReader.readString());
    setFirstName(instance, streamReader.readString());
    setLastName(instance, streamReader.readString());
    setPassword(instance, streamReader.readString());
    setRoles(instance, (java.util.List) streamReader.readObject());
    setUsername(instance, streamReader.readString());
    
  }
  
  public static com.mvp4g.example.client.bean.UserBean instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.mvp4g.example.client.bean.UserBean();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.mvp4g.example.client.bean.UserBean instance) throws SerializationException {
    streamWriter.writeString(getDepartment(instance));
    streamWriter.writeString(getEmail(instance));
    streamWriter.writeString(getFirstName(instance));
    streamWriter.writeString(getLastName(instance));
    streamWriter.writeString(getPassword(instance));
    streamWriter.writeObject(getRoles(instance));
    streamWriter.writeString(getUsername(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.mvp4g.example.client.bean.UserBean_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.UserBean_FieldSerializer.deserialize(reader, (com.mvp4g.example.client.bean.UserBean)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.UserBean_FieldSerializer.serialize(writer, (com.mvp4g.example.client.bean.UserBean)object);
  }
  
}
