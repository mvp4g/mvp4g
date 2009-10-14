package com.mvp4g.example.client.presenter.gxt;

import com.extjs.gxt.ui.client.data.BaseModelData;
import com.mvp4g.example.client.bean.UserBean;

public class MyUserListModel extends BaseModelData {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6366208219041678176L;
	

	public MyUserListModel( UserBean user ) {
		setData( user );
	}
	
	public void updateData(UserBean user){
		setData( user );
	}
	
	private void setData(UserBean user){
		set( "firstName", user.getFirstName() );
		set( "lastName", user.getLastName() );
		set( "username", user.getUsername() );
		set( "email", user.getEmail() );
		set( "department", user.getDepartment() );	
	}

}
