package com.mvp4g.example.client.product;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.example.client.main.MainEventBus;

public interface ProductModule extends Mvp4gModule {
	
	public void setParentEventBus(MainEventBus eventBus);

}
