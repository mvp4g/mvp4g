package com.mvp4g.example.client.bean;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

@SuppressWarnings("deprecation")
public class BasicBean_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getDescription(com.mvp4g.example.client.bean.BasicBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.BasicBean::description;
  }-*/;
  
  private static native void  setDescription(com.mvp4g.example.client.bean.BasicBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.BasicBean::description = value;
  }-*/;
  
  private static native java.lang.String getId(com.mvp4g.example.client.bean.BasicBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.BasicBean::id;
  }-*/;
  
  private static native void  setId(com.mvp4g.example.client.bean.BasicBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.BasicBean::id = value;
  }-*/;
  
  private static native java.lang.String getName(com.mvp4g.example.client.bean.BasicBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.BasicBean::name;
  }-*/;
  
  private static native void  setName(com.mvp4g.example.client.bean.BasicBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.BasicBean::name = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.mvp4g.example.client.bean.BasicBean instance) throws SerializationException {
    setDescription(instance, streamReader.readString());
    setId(instance, streamReader.readString());
    setName(instance, streamReader.readString());
    
  }
  
  public static com.mvp4g.example.client.bean.BasicBean instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.mvp4g.example.client.bean.BasicBean();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.mvp4g.example.client.bean.BasicBean instance) throws SerializationException {
    streamWriter.writeString(getDescription(instance));
    streamWriter.writeString(getId(instance));
    streamWriter.writeString(getName(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.mvp4g.example.client.bean.BasicBean_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.BasicBean_FieldSerializer.deserialize(reader, (com.mvp4g.example.client.bean.BasicBean)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.BasicBean_FieldSerializer.serialize(writer, (com.mvp4g.example.client.bean.BasicBean)object);
  }
  
}
