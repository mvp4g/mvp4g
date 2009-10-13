package com.mvp4g.example.client.bean;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BasicBean implements IsSerializable{

	private String id = null;
	private String name = null;	
	private String description = null;
	
	public BasicBean(){
		
	}
	
	public BasicBean(String id, String name, String description){
		setId( id );
		setName( name );		
		setDescription( description );
	}

	public String getId() {
		return id;
	}

	public void setId( String id ) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName( String name ) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription( String description ) {
		this.description = description;
	}

}
