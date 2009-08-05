package com.mvp4g.example.client;

public enum EventsEnum {

	CHANGE_TOP_WIDGET("changeTopWidget"), 
	CHANGE_LEFT_BOTTOM_WIDGET("changeLeftBottomWidget"),
	CHANGE_RIGHT_BOTTOM_WIDGET("changeRightBottomWidget"),
	CREATE_NEW_USER("createNewUser"),
	SELECT_USER("selectUser"),
	USER_CREATED("userCreated"),
	USER_UPDATED("userUpdated");
		
	private String eventType = null;
	
	private EventsEnum(String eventType){
		this.eventType = eventType;
	}
	
	@Override
	public String toString(){
		return eventType;
	}
}
