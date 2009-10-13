package com.mvp4g.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.mvp4g.client.Mvp4gStarter;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class TestMvp4g implements EntryPoint {
	

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Mvp4gStarter starter = GWT.create(Mvp4gStarter.class);
		starter.start();
	}
}
