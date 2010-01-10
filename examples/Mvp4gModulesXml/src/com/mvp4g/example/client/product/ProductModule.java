package com.mvp4g.example.client.product;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.XmlFilePath;
import com.mvp4g.client.event.EventBusWithLookup;

@XmlFilePath("xmlConfig/product-mvp4g.xml")
public interface ProductModule extends Mvp4gModule {
	
	public void setParentEventBus(EventBusWithLookup eventBus);
	
}
