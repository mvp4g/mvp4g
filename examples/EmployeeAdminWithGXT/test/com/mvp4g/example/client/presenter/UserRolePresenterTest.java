package com.mvp4g.example.client.presenter;

import static com.mvp4g.example.client.Constants.ROLES;
import static org.easymock.EasyMock.and;
import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.IUserRoleView;

public class UserRolePresenterTest {

	UserRolePresenter presenter = null;
	IUserRoleView mockView;
	UserServiceAsync mockService;
	EmployeeAdminWithGXTEventBus mockEventBus;
	UserBean mockUser;

	@Before
	public void setUp() {
		presenter = new UserRolePresenter();
		mockView = createMock( IUserRoleView.class );
		mockService = createMock( UserServiceAsync.class );
		mockEventBus = createMock( EmployeeAdminWithGXTEventBus.class );
		presenter.setView( mockView );
		presenter.setUserService( mockService );
		presenter.setEventBus( mockEventBus );
		mockUser = new UserBean();
	}

	@Test
	public void testBind() {

		mockView.addPossibleRole( UserRolePresenter.NONE_SELECTED );
		for ( String role : ROLES ) {
			mockView.addPossibleRole( role );
		}
		disable();

		replay( mockView );
		presenter.bind();
		verify( mockView );
	}

	@Test
	public void testOnStart() {
		mockEventBus.changeRightBottomWidget( mockView );
		replay( mockEventBus );
		presenter.onStart();
		verify( mockEventBus );
	}

	@Test
	public void testOnSelectUser() {
		String role1 = "role1";
		String role2 = "role2";
		mockUser.setRoles( Arrays.asList( role1, role2 ) );

		mockView.clearUserRoles();
		mockView.addUserRole( role1 );
		mockView.addUserRole( role2 );

		mockView.setPossibleRole( UserRolePresenter.NONE_SELECTED );
		mockView.setPossibleRoleEnabled( true );
		mockView.setAddButtonEnabled( false );
		mockView.setRemoveButtonEnabled( false );

		replay( mockView );
		presenter.onSelectUser( mockUser );
		verify( mockView );

		assertSame( presenter.user, mockUser );

	}

	@Test
	public void testOnUserUpdated() {
		disable();
		replay( mockView );
		presenter.onUserUpdated( null );
		verify( mockView );
		assertNull( presenter.user );
	}

	@Test
	public void testOnCreateNewUser() {
		disable();
		replay( mockView );
		presenter.onCreateNewUser( mockUser );
		verify( mockView );
		assertSame( presenter.user, mockUser );
	}

	@Test
	public void testOnUnselectUser() {
		disable();
		replay( mockView );
		presenter.onUnselectUser();
		verify( mockView );
		assertNull( presenter.user );
	}

	@SuppressWarnings( "unchecked" )
	@Test
	public void testOnAddButtonClicked() {

		presenter.user = mockUser;

		String role = "role";

		Capture<AsyncCallback<Void>> callback = new Capture<AsyncCallback<Void>>();
		mockService.updateUser( eq( mockUser ), and( capture( callback ), isA( AsyncCallback.class ) ) );
		expect( mockView.getPossibleRoleSelected() ).andReturn( role );
		replay( mockService, mockView );
		presenter.onAddButtonClicked();
		verify( mockService, mockView );
		assertTrue( mockUser.getRoles().contains( role ) );

		callback.getValue().onFailure( null );
		assertFalse( mockUser.getRoles().contains( role ) );

		reset( mockView );
		mockView.addUserRole( role );
		replay( mockView );
		callback.getValue().onSuccess( null );
		verify( mockView );
	}

	@Test
	public void testSameRole() {
		presenter.user = mockUser;

		String role = "role";
		mockUser.setRoles( Arrays.asList( role ) );

		expect( mockView.getPossibleRoleSelected() ).andReturn( role );
		mockView.displayError( "Role already exists for this user" );

		replay( mockView );
		presenter.onAddButtonClicked();
		verify( mockView );
	}

	@Test
	public void testOnPossibleRoleSelected() {

		presenter.enabled = false;

		replay( mockView );
		presenter.onPossibleRoleSelected();
		verify( mockView );

		reset( mockView );
		presenter.enabled = true;
		expect( mockView.getPossibleRoleSelected() ).andReturn( "role" );
		mockView.setAddButtonEnabled( true );
		replay( mockView );
		presenter.onPossibleRoleSelected();
		verify( mockView );

		reset( mockView );
		expect( mockView.getPossibleRoleSelected() ).andReturn( UserRolePresenter.NONE_SELECTED );
		mockView.setAddButtonEnabled( false );
		replay( mockView );
		presenter.onPossibleRoleSelected();
		verify( mockView );

	}

	@SuppressWarnings( "unchecked" )
	@Test
	public void testOnRemoveButtonClicked() {

		String role1 = "role1";
		String role2 = "role2";
		List<String> roles = new ArrayList<String>();
		roles.add( role1 );
		roles.add( role2 );
		mockUser.setRoles( roles );

		presenter.user = mockUser;

		expect( mockView.getSelectedRoles() ).andReturn( Arrays.asList( role1, role2 ) );

		Capture<AsyncCallback<Void>> callback1 = new Capture<AsyncCallback<Void>>();
		mockService.updateUser( eq( mockUser ), and( capture( callback1 ), isA( AsyncCallback.class ) ) );

		Capture<AsyncCallback<Void>> callback2 = new Capture<AsyncCallback<Void>>();
		mockService.updateUser( eq( mockUser ), and( capture( callback2 ), isA( AsyncCallback.class ) ) );

		replay( mockService, mockView );
		presenter.onRemoveButtonClicked();
		verify( mockService, mockView );
		assertFalse( mockUser.getRoles().contains( role1 ) );
		assertFalse( mockUser.getRoles().contains( role1 ) );

		testDeleteCallback( callback1.getValue(), role1 );
		testDeleteCallback( callback2.getValue(), role2 );

	}

	@Test
	public void testOnRoleSelected() {

		presenter.enabled = false;

		replay( mockView );
		presenter.onRoleSelected();
		verify( mockView );

		presenter.enabled = true;

		reset( mockView );
		expect( mockView.getSelectedRoles() ).andReturn( null );
		replay( mockView );
		presenter.onRoleSelected();
		verify( mockView );

		reset( mockView );
		expect( mockView.getSelectedRoles() ).andReturn( Arrays.asList( "role" ) );
		mockView.setRemoveButtonEnabled( true );
		replay( mockView );
		presenter.onRoleSelected();
		verify( mockView );

	}

	private void testDeleteCallback( AsyncCallback<Void> callback, String role ) {
		reset( mockView );

		callback.onFailure( null );
		assertTrue( mockUser.getRoles().contains( role ) );

		mockView.removeUserRole( role );
		mockView.setRemoveButtonEnabled( false );
		replay( mockView );
		callback.onSuccess( null );
		verify( mockView );
	}

	private void disable() {
		mockView.setRemoveButtonEnabled( false );
		mockView.setAddButtonEnabled( false );
		mockView.clearUserRoles();
		mockView.setPossibleRole( UserRolePresenter.NONE_SELECTED );
		mockView.setPossibleRoleEnabled( false );
	}

}
