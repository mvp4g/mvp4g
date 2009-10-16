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
import com.mvp4g.client.presenter.Presenter;
import com.mvp4g.example.client.EventsEnum;
import com.mvp4g.example.client.UserServiceAsync;
import com.mvp4g.example.client.bean.UserBean;
import com.mvp4g.example.client.presenter.view_interface.UserListViewInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTButtonInterface;
import com.mvp4g.example.client.presenter.view_interface.widget_interface.gxt.MyGXTTableInterface;

public class UserListPresenter extends Presenter<UserListViewInterface> {
	
	private static BeanModelFactory factory = BeanModelLookup.get().getFactory( UserBean.class );

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
		
		

		MyGXTButtonInterface delete = view.getDeleteButton();
		delete.setEnabled( false );
		delete.addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				setVisibleConfirmDeletion( true );
			}

		} );
		view.getNewButton().addListener( Events.Select, new Listener<ButtonEvent>() {

			public void handleEvent( ButtonEvent be ) {
				eventBus.dispatch( EventsEnum.CREATE_NEW_USER, new UserBean() );
			}

		} );
		MyGXTTableInterface table = view.getTable();
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
		eventBus.dispatch( EventsEnum.CHANGE_TOP_WIDGET, view.getViewWidget() );
		loader.load(0, 4);
		/*
		 * service.getUsers( new AsyncCallback<List<UserBean>>() {
		 * 
		 * public void onFailure( Throwable caught ) { // TODO Auto-generated method stub
		 * 
		 * }
		 * 
		 * public void onSuccess( List<UserBean> result ) { users = result; int nbUsers =
		 * result.size(); MyGXTTableInterface table = view.getTable(); for ( int i = 0; i < nbUsers;
		 * i++ ) { table.addUser( users.get( i ) ); }
		 * 
		 * eventBus.dispatch( EventsEnum.CHANGE_TOP_WIDGET, view.getViewWidget() );
		 * 
		 * }
		 * 
		 * } );
		 */

	}

	public void onUserUpdated( UserBean user ) {
		if(indexSelected > -1){
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

	public void setUserService( UserServiceAsync service ) {
		this.service = service;
	}

	private void selectUser( int row ) {
		MyGXTTableInterface table = view.getTable();

		if ( indexSelected > -1 ) {
			table.unSelectRow( indexSelected );
		}

		indexSelected = row;
		table.selectRow( indexSelected );
		eventBus.dispatch( EventsEnum.SELECT_USER, store.getAt( row ).getBean() );
		view.getDeleteButton().setEnabled( true );

	}

	private void deleteUser() {
		service.deleteUser((UserBean) store.getAt( indexSelected ).getBean(), new AsyncCallback<Void>() {

			public void onFailure( Throwable caught ) {
				// TODO Auto-generated method stub

			}

			public void onSuccess( Void result ) {
				store.remove( store.getAt( indexSelected ) );				
				view.getDeleteButton().setEnabled( false );
				setVisibleConfirmDeletion( false );
				eventBus.dispatch( EventsEnum.UNSELECT_USER );
			}

		} );
	}

	private void setVisibleConfirmDeletion( boolean visible ) {
		view.getConfirmText().setVisible( visible );
		view.getYesButton().setVisible( visible );
		view.getNoButton().setVisible( visible );
	}
}
