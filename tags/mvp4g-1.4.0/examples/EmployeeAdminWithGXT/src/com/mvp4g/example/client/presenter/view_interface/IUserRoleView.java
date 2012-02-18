package com.mvp4g.example.client.presenter.view_interface;

import java.util.List;

import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.example.client.widget.interfaces.IWidget;

public interface IUserRoleView extends IWidget, ReverseViewInterface<IUserRoleView.IUserRolePresenter> {

	public interface IUserRolePresenter {
		void onAddButtonClicked();

		void onRemoveButtonClicked();

		void onRoleSelected();

		void onPossibleRoleSelected();
	}

	void displayError( String error );
	
	void setAddButtonEnabled( boolean enabled );

	void setRemoveButtonEnabled( boolean enabled );

	/*
	 * New role drop down
	 */
	String getPossibleRoleSelected();

	void addPossibleRole( String role );

	void setPossibleRole( String role );

	void setPossibleRoleEnabled( boolean enabled );	

	/*
	 * User roles list
	 */
	List<String> getSelectedRoles();

	void addUserRole( String role );

	void removeUserRole( String role );

	void clearUserRoles();	

}
