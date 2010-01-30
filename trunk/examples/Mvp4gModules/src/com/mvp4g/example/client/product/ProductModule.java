package com.mvp4g.example.client.product;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.module.HistoryName;

@HistoryName("product")
public interface ProductModule extends Mvp4gModule {
	
	public void setParentModule(Mvp4gModule module);

}
