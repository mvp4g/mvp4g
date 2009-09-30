package com.mvp4g.example.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.mvp4g.client.Mvp4gStarter;
import com.mvp4g.example.client.lib.MyMvp4gStarter;

public class Mvp4gHistory implements EntryPoint {

	public void onModuleLoad() {
		Mvp4gStarter starter = GWT.create( Mvp4gStarter.class );
		//Mvp4gStarter starter = new MyMvp4gStarter();
		starter.start();
	}

}
