package com.mvp4g.example.client.presenter;

import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.EmployeeAdminWithGXTEventBus;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.view.UserListView;
import com.mvp4g.example.client.widget.interfaces.ILabel;
import com.mvp4g.example.client.widget.interfaces.IWidget;
import com.mvp4g.example.client.widget.interfaces.gxt.IGXTButton;
import com.mvp4g.example.client.widget.interfaces.gxt.IGXTPagingToolBar;
import com.mvp4g.example.client.widget.interfaces.gxt.IGXTTable;

/**
 * SOLUTION 3
 * 
 * Description: 
 * use GXT widget only in the view implementation but use GXT events directly in presenters and view 
 * interfaces (so in presenters and view interfaces, instead of using addClickHandler, 
 * addValueChangeHandler... methods, GXT addListener method is used). Extra classes are still needed 
 * to wrap GXT widgets.
 *  
 * Advantages:
 * -You can easily test presenters with JUnit and Mock libraries
 * -You keep GXT logic
 * -Wrapping classes are easy to code 
 * 
 * Drawbacks:
 * -You have to create a lot of wrapping classes.
 * -Switching widget library has an impact on your code 
 */
@Presenter( view = UserListView.class )
public class UserListPresenter extends BasePresenter<UserListPresenter.IUserListView, EmployeeAdminWithGXTEventBus> {

	public interface IUserListView extends IWidget {

		void buildWidget( ListStore<BeanModel> store );

		IWidget getViewWidget();

		IGXTTable getTable();

		IGXTPagingToolBar getToolBar();

		IGXTButton getDeleteButton();

		IGXTButton getNewButton();

		IGXTButton getYesButton();

		IGXTButton getNoButton();

		ILabel getConfirmText();

	}

	private BeanModelFactory factory = BeanModelLookup.get().getFactory( UserBean.class );

	protected int indexSelected = -1;

	private UserServiceAsync service = null;

	private PagingLoader<PagingLoadResult<ModelData>> loader = null;
	private ListStore<BeanModel> store = null;

	@SuppressWarnings( "unchecked" )
	@Override
	public void bind() {

		RpcProxy<PagingLoadResult<UserBean>> proxy = new RpcProxy<PagingLoadResult<UserBean>>() {

			public void load( Object loadConfig, AsyncCallback<PagingLoadResult<UserBean>> callback ) {
				service.getUsers( (PagingLoadConfig)loadConfig, callback );
			}
		};

		// loader 
		loader = new BasePagingLoader<PagingLoadResult<ModelData>>( proxy, new BeanModelReader() );
		store = new ListStore<BeanModel>( loader );
		view.getToolBar().bind( loader );

		view.buildWidget( store );

		IGXTButton delete = view.getDeleteButton();
		delete.setEnabled( false );
		delete.addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				setVisibleConfirmDeletion( true );
			}

		} );
		view.getNewButton().addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				eventBus.createNewUser( new UserBean() );
			}

		} );
		IGXTTable table = view.getTable();
		table.addListener( Events.RowClick, new Listener<GridEvent>() {

			public void handleEvent( GridEvent be ) {

				selectUser( be.getRowIndex() );

			}

		} );

		view.getYesButton().addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				deleteUser();
			}

		} );
		view.getNoButton().addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				setVisibleConfirmDeletion( false );
			}

		} );
		setVisibleConfirmDeletion( false );

	}

	public void onStart() {
		eventBus.changeTopWidget( view.getViewWidget() );
		loader.load( 0, 4 );
	}

	public void onUserUpdated( UserBean user ) {
		if ( indexSelected > -1 ) {
			store.update( store.getAt( indexSelected ) );
		}
	}

	public void onUserCreated( UserBean user ) {
		store.add( factory.createModel( user ) );
	}

	public void onUnselectUser() {
		view.getTable().unSelectRow( indexSelected );
		indexSelected = -1;
	}

	@InjectService
	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	private void selectUser( int row ) {
		IGXTTable table = view.getTable();

		if ( indexSelected > -1 ) {
			table.unSelectRow( indexSelected );
		}

		indexSelected = row;
		table.selectRow( indexSelected );
		eventBus.selectUser( (UserBean)store.getAt( row ).getBean() );
		view.getDeleteButton().setEnabled( true );

	}

	private void deleteUser() {
		service.deleteUser( (UserBean)store.getAt( indexSelected ).getBean(), new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( Void result ) {
				store.remove( store.getAt( indexSelected ) );
				view.getDeleteButton().setEnabled( false );
				setVisibleConfirmDeletion( false );
				eventBus.unselectUser();
			}

		} );
	}

	private void setVisibleConfirmDeletion( boolean visible ) {
		view.getConfirmText().setVisible( visible );
		view.getYesButton().setVisible( visible );
		view.getNoButton().setVisible( visible );
	}
}
