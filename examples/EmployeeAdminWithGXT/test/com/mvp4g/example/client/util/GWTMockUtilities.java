package com.mvp4g.example.client.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWTBridge;

public class GWTMockUtilities {

	public static void disarm() {
		GWTBridge bridge = new GXTBridge();
        //Inject my own bridge to be able to create my own BeanModelLookup
		setGwtBridge( bridge );
	}

	public static void restore() {
		setGwtBridge( null );
	}

	private static void setGwtBridge( GWTBridge bridge ) {
		Class gwtClass = GWT.class;
		Class[] paramTypes = new Class[] { GWTBridge.class };
		Method setBridgeMethod = null;
		try {
			setBridgeMethod = gwtClass.getDeclaredMethod( "setBridge", paramTypes );
		} catch ( NoSuchMethodException e ) {
			throw new RuntimeException( e );
		}
		setBridgeMethod.setAccessible( true );
		try {
			setBridgeMethod.invoke( gwtClass, new Object[] { bridge } );
		} catch ( IllegalAccessException e ) {
			throw new RuntimeException( e );
		} catch ( InvocationTargetException e ) {
			throw new RuntimeException( e );
		}
	}

}
