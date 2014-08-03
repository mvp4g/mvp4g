package com.mvp4g.example.client.presenter;

import com.mvp4g.example.client.Constants;
import com.mvp4g.example.client.EmployeeAdminEventBus;
import com.mvp4g.example.shared.dto.UserBean;
import com.mvp4g.example.client.service.UserServiceAsync;
import com.mvp4g.example.client.ui.user.list.IUserListView;
import com.mvp4g.example.client.ui.user.list.UserListPresenter;
import com.mvp4g.example.client.widget.interfaces.IButton;
import com.mvp4g.example.client.widget.interfaces.ILabel;
import com.mvp4g.example.client.widget.interfaces.ITable;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.verify;

public class UserListPresenterTest implements Constants {

  UserListPresenter presenter = null;
  List<UserBean>    users     = null;
  IUserListView         mockView;
  UserServiceAsync      mockService;
  EmployeeAdminEventBus mockEventBus;

  IButton delete;
  IButton yes;
  IButton no;
  IButton newButton;
  ITable  table;
  ILabel  confirmText;

  @Before
  public void setUp() {

    delete = createMock(IButton.class);
    yes = createMock(IButton.class);
    no = createMock(IButton.class);
    newButton = createMock(IButton.class);
    table = createMock(ITable.class);
    confirmText = createMock(ILabel.class);

    presenter = new UserListPresenter();
    mockView = createMock(IUserListView.class);
    mockService = createMock(UserServiceAsync.class);
    mockEventBus = createMock(EmployeeAdminEventBus.class);
    presenter.setView(mockView);
    presenter.setEventBus(mockEventBus);
//    presenter.setUserService(mockService);
    users = createUsers();

  }

  @Test
  public void testBind() {
//    expect(mockView.getDeleteButton()).andReturn(delete);
//    expect(delete.addClickHandler(isA(DeleteClickHandler.class))).andReturn(null);
//
//    expect(mockView.getNewButton()).andReturn(newButton);
//    expect(newButton.addClickHandler(isA(NewClickHandler.class))).andReturn(null);
//
//    expect(mockView.getTable()).andReturn(table);
//    expect(table.addClickHandler(isA(TableClickHandler.class))).andReturn(null);
//
//    expect(mockView.getYesButton()).andReturn(yes);
//    expect(yes.addClickHandler(isA(YesClickHandler.class))).andReturn(null);
//
//    expect(mockView.getNoButton()).andReturn(no);
//    expect(no.addClickHandler(isA(NoClickHandler.class))).andReturn(null);
//
//    replay(mockView,
//           delete,
//           newButton,
//           table,
//           yes,
//           no);
//    presenter.bind();
//    verify(mockView);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testOnGetUserList() {

  }

  @SuppressWarnings("unchecked")
  @Test
  public void testOnStart() {
//    Capture<AsyncCallback<List<UserBean>>> callback = new Capture<AsyncCallback<List<UserBean>>>();
//    mockService.getUsers(and(capture(callback),
//                             isA(AsyncCallback.class)));
//
//    setVisibleConfirmDeletion(false);
//
//    expect(mockView.getDeleteButton()).andReturn(delete);
//    delete.setEnabled(false );
//
//		replay( mockView, mockService, yes, no, confirmText, delete );
//
//		presenter.onStart();
//		verify( mockView, mockService, yes, no, confirmText, delete );
//		reset( mockView, mockService, yes, no, confirmText, delete );
//
//		for ( int i = 0; i < users.size(); i++ ) {
//			checkOneRow( users.get( i ), i + 1 );
//		}
//		mockEventBus.changeTopWidget( mockView );
//		replay( table, mockView, mockEventBus );
//		callback.getValue().onSuccess( users );
//		verify( table, mockView );
//		reset( table, mockView, mockEventBus );
	}

	@Test
	public void testOnUserUpdated() {
		setRows();
//
//		UserBean user = users.get( 0 );
//		fillUser( user );
//		checkOneRow( user, 1 );
//
//		replay( table, mockView );
//
//		int previousSize = users.size();
//		presenter.onUserUpdated( user );
//		assertTrue( users.size() == previousSize );
//
//		verify( table, mockView );
	}

	@Test
	public void testOnUserCreated() {
		setRows();

//		UserBean user = new UserBean();
//		fillUser( user );
//		checkOneRow( user, users.size() + 1 );
//
//		replay( table, mockView );
//
//		int previousSize = users.size();
//		presenter.onUserCreated( user );
//		assertTrue( users.size() == ( previousSize + 1 ) );
//
		verify( table, mockView );
	}

	@Test
	public void testOnUserunselected() {
//		setRows();
//		selectUser( 2 );
//
//		expect( mockView.getTable() ).andReturn( table );
//		table.unSelectRow( 2 );
//		replay( mockView, table );
//		presenter.onUnselectUser();
//		verify( mockView, table );
//		reset( mockView, table );
//
//		//verify selectIndex has been set to 0
//		expect( mockView.getTable() ).andReturn( table );
//		table.unSelectRow( 0 );
//		replay( mockView, table );
//		presenter.onUnselectUser();
//		verify( mockView, table );
	}

	@Test
	public void testDeleteClick() {

//		expect( mockView.getConfirmText() ).andReturn( confirmText );
//		expect( mockView.getYesButton() ).andReturn( yes );
//		expect( mockView.getNoButton() ).andReturn( no );
//
//		yes.setVisible( true );
//		no.setVisible( true );
//		confirmText.setVisible( true );
//		replay( mockView, yes, no, confirmText );
//		presenter.new DeleteClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
//		verify( mockView, yes, no, confirmText );
	}

	@Test
	public void testNewClick() {
//		mockEventBus.createNewUser( isA( UserBean.class ) );
//		replay( mockEventBus );
//		presenter.new NewClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
//		verify( mockEventBus );
	}

	@Test
	public void testTableClick() {
//		setRows();
//
//		expect( table.getRowForEvent( isA( ClickEvent.class ) ) ).andReturn( 2 );
//		expect( mockView.getTable() ).andReturn( table );
//		expectLastCall().times( 2 );
//
//		table.selectRow( 2 );
//		mockEventBus.selectUser( users.get( 1 ) );
//
//		expect( mockView.getDeleteButton() ).andReturn( delete );
//		delete.setEnabled( true );
//
//		replay( table, delete, mockView, mockEventBus );
//		presenter.new TableClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
//		verify( table, delete, mockView, mockEventBus );

	}

	@SuppressWarnings( "unchecked" )
	@Test
	public void testYesClick() {

//		setRows();
//		selectUser( 2 );
//
//		Capture<AsyncCallback<Void>> callback = new Capture<AsyncCallback<Void>>();
//		mockService.deleteUser( eq( users.get( 1 ) ), and( capture( callback ), isA( AsyncCallback.class ) ) );
//		replay( mockService );
//		presenter.new YesClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
//		verify( mockService );
//
//		int previousSize = users.size();
//		UserBean user = users.get( 1 );
//		expect( mockView.getTable() ).andReturn( table );
//		expect( mockView.getDeleteButton() ).andReturn( delete );
//		table.removeRow( 2 );
//		delete.setEnabled( false );
//		setVisibleConfirmDeletion( false );
//		replay( table, delete, mockView, yes, no, confirmText );
//		callback.getValue().onSuccess( null );
//		verify( table, delete, mockView, yes, no, confirmText );
//		assertTrue( users.size() == ( previousSize - 1 ) );
//		assertFalse( users.contains( user ) );
	}

	@Test
	public void testNoClick() {

//		setVisibleConfirmDeletion( false );
//		replay( mockView, yes, no );
//		presenter.new NoClickHandler().onClick( MockGwtEvent.CLICK_EVENT );
//		verify( mockView, yes, no );
	}

	@Test
	public void testSelectAndUnselect() {
		setRows();
		selectUser( 2 );

		table.unSelectRow( 2 );
		selectUser( 3 );
	}

	private void setVisibleConfirmDeletion( boolean visible ) {
//		expect( mockView.getYesButton() ).andReturn( yes );
//		expect( mockView.getNoButton() ).andReturn( no );
//		expect( mockView.getConfirmText() ).andReturn( confirmText );
//
//		yes.setVisible( visible );
//		no.setVisible( visible );
//		confirmText.setVisible( visible );
	}

	private void setRows() {
//		for ( int i = 0; i < users.size(); i++ ) {
//			checkOneRow( users.get( i ), i + 1 );
//		}
//		replay( mockView, table );
//		presenter.setUserList( users );
//		verify( mockView, table );
//		reset( mockView, table );
	}

	private void checkOneRow( UserBean user, int row ) {
//		expect( mockView.getTable() ).andReturn( table );
//		table.setText( row, 0, user.getUsername() );
//		table.setText( row, 1, user.getFirstName() );
//		table.setText( row, 2, user.getLastName() );
//		table.setText( row, 3, user.getEmail() );
//		table.setText( row, 4, user.getDepartment() );
	}

	private void selectUser( int row ) {
//		expect( mockView.getTable() ).andReturn( table );
//		expect( mockView.getDeleteButton() ).andReturn( delete );
//		table.selectRow( row );
//		mockEventBus.selectUser( users.get( row - 1 ) );
//		delete.setEnabled( true );
//		replay( mockView, table, delete, mockEventBus );
//		presenter.selectUser( row );
//		verify( mockView, table, delete, mockEventBus );
//		reset( mockView, table, delete, mockEventBus );
	}

	private List<UserBean> createUsers() {
		List<UserBean> users = new ArrayList<UserBean>();

		String[] firstNames = { "Karim", "Cristiano", "Iker", "Sergio" };
		String[] lastNames = { "Benzema", "Ronaldo", "Casillas", "Ramos" };
		int NB_USERS = 4;

		UserBean user = null;
		String firstName = null;
		String lastName = null;
		String username = null;
		List<String> roles = null;

		for ( int i = 0; i < NB_USERS; i++ ) {
			user = new UserBean();

			firstName = firstNames[i % firstNames.length];
			user.setFirstName( firstName );

			lastName = lastNames[i % lastNames.length];
			user.setLastName( lastName );

			username = ( firstName.substring( 0, 1 ) + lastName ).toLowerCase();
			user.setUsername( username );

			user.setEmail( username + "@real.com" );

			user.setDepartment( DEPARTMENTS[i % DEPARTMENTS.length] );

			user.setPassword( "1234" );

			roles = new ArrayList<String>();
			roles.add( ROLES[i] );
			user.setRoles( roles );

			users.add( user );
		}
		return users;
	}

	private void fillUser( UserBean user ) {
		user.setUsername( "username" );
		user.setFirstName( "firstName" );
		user.setLastName( "lastName" );
		user.setEmail( "email" );
		user.setDepartment( "department" );
	}

}
