package com.mvp4g.example.client.util;

import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.google.gwt.core.client.GWTBridge;
import com.google.gwt.dev.About;

public class GXTBridge extends GWTBridge {

	@Override
	public <T> T create( Class<?> classLiteral ) {
		//if we try to create an instance of BeanModelLookup, 
		//return an instance of my own BeanModelLookup
		//otherwise return null
		T obj = null;
		if ( BeanModelLookup.class.equals( classLiteral ) ) {
			obj = (T)new BeanModelLookupTest();
		}

		return obj;
	}

	@Override
	public String getVersion() {
		return About.GWT_VERSION_NUM;
	}

	@Override
	public boolean isClient() {
		return false;
	}

	@Override
	public void log( String s, Throwable throwable ) {
		System.out.println( s );
	}

}
