package com.mvp4g.client;

public abstract class Mvp4gEventPasser<T> {
	
	T eventObject = null;
	
	public Mvp4gEventPasser(T eventObject){
		this.eventObject = eventObject;
	}	
	
	public abstract void pass(Mvp4gModule module);

} 
