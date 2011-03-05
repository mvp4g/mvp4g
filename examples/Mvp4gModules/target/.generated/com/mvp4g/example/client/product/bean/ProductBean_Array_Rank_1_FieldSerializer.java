package com.mvp4g.example.client.product.bean;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

@SuppressWarnings("deprecation")
public class ProductBean_Array_Rank_1_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  public static void deserialize(SerializationStreamReader streamReader, com.mvp4g.example.client.product.bean.ProductBean[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.deserialize(streamReader, instance);
  }
  
  public static com.mvp4g.example.client.product.bean.ProductBean[] instantiate(SerializationStreamReader streamReader) throws SerializationException {
    int size = streamReader.readInt();
    return new com.mvp4g.example.client.product.bean.ProductBean[size];
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.mvp4g.example.client.product.bean.ProductBean[] instance) throws SerializationException {
    com.google.gwt.user.client.rpc.core.java.lang.Object_Array_CustomFieldSerializer.serialize(streamWriter, instance);
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.mvp4g.example.client.product.bean.ProductBean_Array_Rank_1_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.mvp4g.example.client.product.bean.ProductBean_Array_Rank_1_FieldSerializer.deserialize(reader, (com.mvp4g.example.client.product.bean.ProductBean[])object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.mvp4g.example.client.product.bean.ProductBean_Array_Rank_1_FieldSerializer.serialize(writer, (com.mvp4g.example.client.product.bean.ProductBean[])object);
  }
  
}