package com.mvp4g.example.client.bean;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

@SuppressWarnings("deprecation")
public class ProductBean_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native java.lang.String getPrice(com.mvp4g.example.client.bean.ProductBean instance) /*-{
    return instance.@com.mvp4g.example.client.bean.ProductBean::price;
  }-*/;
  
  private static native void  setPrice(com.mvp4g.example.client.bean.ProductBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.bean.ProductBean::price = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.mvp4g.example.client.bean.ProductBean instance) throws SerializationException {
    setPrice(instance, streamReader.readString());
    
    com.mvp4g.example.client.bean.BasicBean_FieldSerializer.deserialize(streamReader, instance);
  }
  
  public static com.mvp4g.example.client.bean.ProductBean instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.mvp4g.example.client.bean.ProductBean();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.mvp4g.example.client.bean.ProductBean instance) throws SerializationException {
    streamWriter.writeString(getPrice(instance));
    
    com.mvp4g.example.client.bean.BasicBean_FieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.mvp4g.example.client.bean.ProductBean_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.ProductBean_FieldSerializer.deserialize(reader, (com.mvp4g.example.client.bean.ProductBean)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.mvp4g.example.client.bean.ProductBean_FieldSerializer.serialize(writer, (com.mvp4g.example.client.bean.ProductBean)object);
  }
  
}
