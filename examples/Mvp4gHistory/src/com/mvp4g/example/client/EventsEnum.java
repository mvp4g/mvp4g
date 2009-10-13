package com.mvp4g.example.client;

public enum EventsEnum {

	CHANGE_TOP_WIDGET("changeTopWidget"), 
	CHANGE_BOTTOM_WIDGET("changeBottomWidget"),
	CHANGE_MAIN_WIDGET("changeMainWidget"),
	DISPLAY_CART("displayCart"),
	DISPLAY_DEAL("displayDeal"),
	DISPLAY_PRODUCT("displayProduct"),
	DISPLAY_MESSAGE("displayMessage"),
	LOGIN("login"),
	START("start");
		
	private String eventType = null;
	
	private EventsEnum(String eventType){
		this.eventType = eventType;
	}
	
	@Override
	public String toString(){
		return eventType;
	}
}
