package com.mvp4g.example.client.main;

import com.google.gwt.user.client.ui.Widget;
import com.mvp4g.client.annotation.Event;
import com.mvp4g.client.annotation.Events;
import com.mvp4g.client.annotation.module.AfterLoadChildModule;
import com.mvp4g.client.annotation.module.BeforeLoadChildModule;
import com.mvp4g.client.annotation.module.ChildModule;
import com.mvp4g.client.annotation.module.ChildModules;
import com.mvp4g.client.annotation.module.LoadChildModuleError;
import com.mvp4g.client.annotation.module.UseToLoadChildModuleView;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.example.client.company.CompanyModule;
import com.mvp4g.example.client.product.ProductModule;

@Events(startView = MainView.class)
@ChildModules( { @ChildModule(moduleClass = CompanyModule.class),
		@ChildModule(moduleClass = ProductModule.class) })
public interface MainEventBus extends EventBus {

	@Event(modulesToLoad = CompanyModule.class)
	public void goToCompany();

	@Event(modulesToLoad = ProductModule.class)
	public void goToProduct();

	@UseToLoadChildModuleView({ProductModule.class, CompanyModule.class})
	@Event(handlers = MainPresenter.class)
	public void changeBody(Widget newBody);
	
	@LoadChildModuleError
	@Event(handlers = MainPresenter.class)
	public void errorOnLoad(Throwable reason);
	
	@BeforeLoadChildModule
	@Event(handlers = MainPresenter.class)
	public void beforeLoad();
	
	@AfterLoadChildModule
	@Event(handlers = MainPresenter.class)
	public void afterLoad();

}
