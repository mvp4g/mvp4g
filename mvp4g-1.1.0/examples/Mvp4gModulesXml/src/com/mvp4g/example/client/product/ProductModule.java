package com.mvp4g.example.client.product;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.XmlFilePath;
import com.mvp4g.client.annotation.module.HistoryName;

@HistoryName( "product" )
@XmlFilePath( "xmlConfig/product-mvp4g.xml" )
public interface ProductModule extends Mvp4gModule {

	public void setParentModule( Mvp4gModule eventBus );

}
