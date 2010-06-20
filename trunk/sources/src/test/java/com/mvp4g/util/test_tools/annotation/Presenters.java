package com.mvp4g.util.test_tools.annotation;

import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;

public class Presenters {

	@Presenter( view = Object.class )
	public static class SimplePresenter extends BasePresenter<Object, EventBus> {
	}

	@Presenter( view = Object.class, multiple = true )
	public static class MultiplePresenter extends BasePresenter<Object, EventBus> {
	}

	@Presenter( view = Object.class, name = "name" )
	public static class PresenterWithName extends BasePresenter<Object, EventBus> {
	}

	@Presenter( view = Object.class, viewName = "name" )
	public static class PresenterWithViewName extends BasePresenter<Object, EventBus> {
	}

	@Presenter( view = Object.class )
	public static class PresenterNotPublic extends BasePresenter<Object, EventBus> {

		@InjectService
		void setSthg( Services.SimpleServiceAsync service ) {
		}

	}

	@Presenter( view = Object.class )
	public static class PresenterNoParameter extends BasePresenter<Object, EventBus> {

		@InjectService
		public void setSthg() {
		}

	}

	@Presenter( view = Object.class )
	public static class PresenterWithMoreThanOneParameter extends BasePresenter<Object, EventBus> {

		@InjectService
		public void setSthg( Services.SimpleServiceAsync service, Boolean test ) {
		}

	}

	@Presenter( view = Object.class )
	public static class PresenterNotAsync extends BasePresenter<Object, EventBus> {

		@InjectService
		public void setSthg( Services.SimpleService service ) {
		}

	}

	@Presenter( view = Object.class )
	public static class PresenterWithService extends BasePresenter<Object, EventBus> {

		@InjectService
		public void setSthg( Services.SimpleServiceAsync service ) {
		}

	}

	@Presenter( view = Object.class )
	public static class PresenterWithSameService extends BasePresenter<Object, EventBus> {

		@InjectService
		public void setSthg( Services.SimpleServiceAsync service ) {
		}

	}

	@Presenter( view = Object.class )
	public static class PresenterWithServiceAndName extends BasePresenter<Object, EventBus> {

		@InjectService( serviceName = "name" )
		public void setSthg( Services.SimpleServiceAsync service ) {
		}

	}

	@Presenter( view = Object.class )
	public static class PresenterWithEvent extends BasePresenter<Object, EventBus> {

		public void onEvent1( String form ) {
		}

		public void onEvent2() {
		}

	}

}
