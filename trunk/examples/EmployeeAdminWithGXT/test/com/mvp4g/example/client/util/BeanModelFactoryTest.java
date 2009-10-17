package com.mvp4g.example.client.util;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;

public class BeanModelFactoryTest extends BeanModelFactory {
	
	//Have to create a subclass to have access to the constructor
	public class BeanModelTest extends BeanModel{		
		
	}

	@Override
	protected BeanModel newInstance() {
		//Just create a simple instance of BeanModel
		//No need for specific Bean Model since they
		//won't be used by GXT widgets
		return new BeanModelTest();
	}

}
