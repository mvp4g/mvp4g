package com.mvp4g.example.client.company.bean;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.client.rpc.SerializationStreamReader;
import com.google.gwt.user.client.rpc.SerializationStreamWriter;

@SuppressWarnings("deprecation")
public class CompanyBean_FieldSerializer implements com.google.gwt.user.client.rpc.impl.TypeHandler {
  private static native int getId(com.mvp4g.example.client.company.bean.CompanyBean instance) /*-{
    return instance.@com.mvp4g.example.client.company.bean.CompanyBean::id;
  }-*/;
  
  private static native void  setId(com.mvp4g.example.client.company.bean.CompanyBean instance, int value) /*-{
    instance.@com.mvp4g.example.client.company.bean.CompanyBean::id = value;
  }-*/;
  
  private static native java.lang.String getName(com.mvp4g.example.client.company.bean.CompanyBean instance) /*-{
    return instance.@com.mvp4g.example.client.company.bean.CompanyBean::name;
  }-*/;
  
  private static native void  setName(com.mvp4g.example.client.company.bean.CompanyBean instance, java.lang.String value) /*-{
    instance.@com.mvp4g.example.client.company.bean.CompanyBean::name = value;
  }-*/;
  
  public static void deserialize(SerializationStreamReader streamReader, com.mvp4g.example.client.company.bean.CompanyBean instance) throws SerializationException {
    setId(instance, streamReader.readInt());
    setName(instance, streamReader.readString());
    
  }
  
  public static com.mvp4g.example.client.company.bean.CompanyBean instantiate(SerializationStreamReader streamReader) throws SerializationException {
    return new com.mvp4g.example.client.company.bean.CompanyBean();
  }
  
  public static void serialize(SerializationStreamWriter streamWriter, com.mvp4g.example.client.company.bean.CompanyBean instance) throws SerializationException {
    streamWriter.writeInt(getId(instance));
    streamWriter.writeString(getName(instance));
    
  }
  
  public Object create(SerializationStreamReader reader) throws SerializationException {
    return com.mvp4g.example.client.company.bean.CompanyBean_FieldSerializer.instantiate(reader);
  }
  
  public void deserial(SerializationStreamReader reader, Object object) throws SerializationException {
    com.mvp4g.example.client.company.bean.CompanyBean_FieldSerializer.deserialize(reader, (com.mvp4g.example.client.company.bean.CompanyBean)object);
  }
  
  public void serial(SerializationStreamWriter writer, Object object) throws SerializationException {
    com.mvp4g.example.client.company.bean.CompanyBean_FieldSerializer.serialize(writer, (com.mvp4g.example.client.company.bean.CompanyBean)object);
  }
  
}
