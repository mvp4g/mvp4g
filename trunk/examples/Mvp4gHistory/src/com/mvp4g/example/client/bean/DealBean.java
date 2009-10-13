package com.mvp4g.example.client.bean;

public class DealBean extends BasicBean {

	private String code = null;
	

	public DealBean() {
		//nothing to do
	}

	public DealBean( String id, String title, String description, String code ) {
		super( id, title, description );
		setCode( code );
	}

	public String getCode() {
		return code;
	}

	public void setCode( String code ) {
		this.code = code;
	}

	

}
