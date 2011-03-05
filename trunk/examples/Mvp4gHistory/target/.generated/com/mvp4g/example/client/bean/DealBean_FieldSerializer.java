package com.mvp4g.example.client.bean;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

@SuppressWarnings("deprecation")
public class DealBean_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getCode(com.mvp4g.example.client.bean.DealBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.DealBean::code;
  }-*/;
  
  private static native void  setCode(com.mvp4g.example.client.bean.DealBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.DealBean::code = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.mvp4g.example.client.bean.DealBean instance) throws SerializationException {
    setCode(instance, streamReader.readString());
    
    com.mvp4g.example.client.bean.BasicBean_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static com.mvp4g.example.client.bean.DealBean instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.mvp4g.example.client.bean.DealBean();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.mvp4g.example.client.bean.DealBean instance) throws SerializationException {
    streamWriter.writeString(getCode(instance));
    
    com.mvp4g.example.client.bean.BasicBean_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.mvp4g.example.client.bean.DealBean_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.DealBean_FieldSerializer.deserialize(reader, (com.mvp4g.example.client.bean.DealBean)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.DealBean_FieldSerializer.serialize(writer, (com.mvp4g.example.client.bean.DealBean)object);
  }
  
}
