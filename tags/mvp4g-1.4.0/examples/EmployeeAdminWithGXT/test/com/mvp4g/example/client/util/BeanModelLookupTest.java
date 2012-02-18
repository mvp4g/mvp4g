package com.mvp4g.example.client.util;

import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;

public class BeanModelLookupTest extends BeanModelLookup {

	@Override
	public BeanModelFactory getFactory( Class<?> bean ) {
		//just return a Test factory
		return new BeanModelFactoryTest();
	}

}
