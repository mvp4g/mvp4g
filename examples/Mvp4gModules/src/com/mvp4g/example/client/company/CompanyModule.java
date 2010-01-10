package com.mvp4g.example.client.company;

import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.example.client.main.MainEventBus;

public interface CompanyModule extends Mvp4gModule {
	
	public void setParentEventBus(MainEventBus eventBus);

}
