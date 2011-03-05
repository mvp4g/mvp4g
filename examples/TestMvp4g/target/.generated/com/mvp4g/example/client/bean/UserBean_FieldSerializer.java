package com.mvp4g.example.client.bean;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

@SuppressWarnings("deprecation")
public class UserBean_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getFirstName(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::firstName;
  }-*/;
  
  private static native void  setFirstName(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::firstName = value;
  }-*/;
  
  private static native java.lang.Integer getId(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::id;
  }-*/;
  
  private static native void  setId(com.mvp4g.example.client.bean.UserBean instance, java.lang.Integer value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::id = value;
  }-*/;
  
  private static native java.lang.String getLastName(com.mvp4g.example.client.bean.UserBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.UserBean::lastName;
  }-*/;
  
  private static native void  setLastName(com.mvp4g.example.client.bean.UserBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.UserBean::lastName = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.mvp4g.example.client.bean.UserBean instance) throws SerializationException {
    setFirstName(instance, streamReader.readString());
    setId(instance, (java.lang.Integer) streamReader.readObject());
    setLastName(instance, streamReader.readString());
    
  }
  
  public static com.mvp4g.example.client.bean.UserBean instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.mvp4g.example.client.bean.UserBean();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.mvp4g.example.client.bean.UserBean instance) throws SerializationException {
    streamWriter.writeString(getFirstName(instance));
    streamWriter.writeObject(getId(instance));
    streamWriter.writeString(getLastName(instance));
    
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
