package com.mvp4g.example.client.product;

import com.mvp4g.client.history.PlaceService;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.Mvp4gException;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.Mvp4gEventPasser;
import com.mvp4g.client.Mvp4gModule;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.mvp4g.client.event.BaseEventBus;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.event.EventHandlerInterface;
import java.util.List;
import com.mvp4g.client.history.NavigationEventCommand;
import com.mvp4g.client.history.NavigationConfirmationInterface;

public class ProductModuleImpl implements ProductModule {
    
    private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBus implements com.mvp4g.example.client.product.ProductEventBus{}
    @GinModules({com.mvp4g.client.DefaultMvp4gGinModule.class})
    public interface com_mvp4g_example_client_product_ProductModuleGinjector extends Ginjector {
      com.mvp4g.example.client.product.presenter.ProductListPresenter getcom_mvp4g_example_client_product_presenter_ProductListPresenter();
      com.mvp4g.example.client.product.presenter.ProductEditPresenter getcom_mvp4g_example_client_product_presenter_ProductEditPresenter();
      com.mvp4g.example.client.product.presenter.ProductDisplayPresenter getcom_mvp4g_example_client_product_presenter_ProductDisplayPresenter();
      com.mvp4g.example.client.product.presenter.ProductCreationPresenter getcom_mvp4g_example_client_product_presenter_ProductCreationPresenter();
      com.mvp4g.example.client.product.view.ProductCreationView getcom_mvp4g_example_client_product_presenter_ProductCreationPresenterView();
      com.mvp4g.example.client.product.view.ProductListView getcom_mvp4g_example_client_product_presenter_ProductListPresenterView();
      com.mvp4g.example.client.product.view.ProductDisplayView getcom_mvp4g_example_client_product_presenter_ProductDisplayPresenterView();
      com.mvp4g.example.client.product.view.ProductEditView getcom_mvp4g_example_client_product_presenter_ProductEditPresenterView();
    }
    private Object startView = null;
    private PresenterInterface startPresenter = null;
    protected AbstractEventBus eventBus = null;
    protected com.mvp4g.example.client.product.ProductModule itself = this;
    private com.mvp4g.client.Mvp4gModule parentModule = null;
    private com.mvp4g.example.client.main.MainEventBus parentEventBus = null;
    public void setParentModule(com.mvp4g.client.Mvp4gModule module){
      parentModule = module;
      parentEventBus = (com.mvp4g.example.client.main.MainEventBus) module.getEventBus();
    }
    public void addConverter(String historyName, HistoryConverter<?> hc){
    }
    public void clearHistory(){
    }
    public String place(String token, String form, boolean onlyToken){
      throw new Mvp4gException("This method shouldn't be called. There is no history support for this module.");
    }
    public void dispatchHistoryEvent(String eventType, final Mvp4gEventPasser passer){
      int index = eventType.indexOf(PlaceService.MODULE_SEPARATOR);
      if(index > -1){
        String moduleHistoryName = eventType.substring(0, index);
        String nextToken = eventType.substring(index + 1);
        Mvp4gEventPasser nextPasser = new Mvp4gEventPasser(nextToken) {
          public void pass(Mvp4gModule module) {
            module.dispatchHistoryEvent((String) eventObjects[0], passer);
          }
        };
        passer.setEventObject(false);
        passer.pass(this);
      }else{
        passer.pass(this);
      }
    }
    
    public void onForward(){
    }
    
    public void createAndStartModule(){
      final com_mvp4g_example_client_product_ProductModuleGinjector injector = GWT.create( com_mvp4g_example_client_product_ProductModuleGinjector.class );
      final com.mvp4g.example.client.product.view.ProductCreationView com_mvp4g_example_client_product_presenter_ProductCreationPresenterView = injector.getcom_mvp4g_example_client_product_presenter_ProductCreationPresenterView();
      final com.mvp4g.example.client.product.view.ProductListView com_mvp4g_example_client_product_presenter_ProductListPresenterView = injector.getcom_mvp4g_example_client_product_presenter_ProductListPresenterView();
      final com.mvp4g.example.client.product.view.ProductDisplayView com_mvp4g_example_client_product_presenter_ProductDisplayPresenterView = injector.getcom_mvp4g_example_client_product_presenter_ProductDisplayPresenterView();
      final com.mvp4g.example.client.product.view.ProductEditView com_mvp4g_example_client_product_presenter_ProductEditPresenterView = injector.getcom_mvp4g_example_client_product_presenter_ProductEditPresenterView();
      
      
      
      
      final com.mvp4g.example.client.product.presenter.ProductListPresenter com_mvp4g_example_client_product_presenter_ProductListPresenter = injector.getcom_mvp4g_example_client_product_presenter_ProductListPresenter();
      com_mvp4g_example_client_product_presenter_ProductListPresenter.setView(com_mvp4g_example_client_product_presenter_ProductListPresenterView);
      final com.mvp4g.example.client.product.presenter.ProductEditPresenter com_mvp4g_example_client_product_presenter_ProductEditPresenter = injector.getcom_mvp4g_example_client_product_presenter_ProductEditPresenter();
      com_mvp4g_example_client_product_presenter_ProductEditPresenter.setView(com_mvp4g_example_client_product_presenter_ProductEditPresenterView);
      final com.mvp4g.example.client.product.presenter.ProductDisplayPresenter com_mvp4g_example_client_product_presenter_ProductDisplayPresenter = injector.getcom_mvp4g_example_client_product_presenter_ProductDisplayPresenter();
      com_mvp4g_example_client_product_presenter_ProductDisplayPresenter.setView(com_mvp4g_example_client_product_presenter_ProductDisplayPresenterView);
      final com.mvp4g.example.client.product.presenter.ProductCreationPresenter com_mvp4g_example_client_product_presenter_ProductCreationPresenter = injector.getcom_mvp4g_example_client_product_presenter_ProductCreationPresenter();
      com_mvp4g_example_client_product_presenter_ProductCreationPresenter.setView(com_mvp4g_example_client_product_presenter_ProductCreationPresenterView);
      
      
      eventBus = new AbstractEventBus(){
        protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){
        return null;
        }
        public void goToProduct(final java.lang.Integer attr0,final java.lang.Integer attr1){
        confirmNavigation(new NavigationEventCommand(this){
          public void execute(){
            if (com_mvp4g_example_client_product_presenter_ProductListPresenter.isActivated(false)){
              com_mvp4g_example_client_product_presenter_ProductListPresenter.onGoToProduct(attr0,attr1);
            }
          }
        });
      }
      public void selectProductMenu(){
      parentEventBus.selectProductMenu();
    }
    public void displayMessage(java.lang.String attr0){
    parentEventBus.displayMessage(attr0);
  }
  public void backToList(){
  confirmNavigation(new NavigationEventCommand(this){
    public void execute(){
      if (com_mvp4g_example_client_product_presenter_ProductListPresenter.isActivated(false)){
        com_mvp4g_example_client_product_presenter_ProductListPresenter.onBackToList();
      }
    }
  });
}
public void goToEdit(final com.mvp4g.example.client.product.bean.ProductBean attr0){
confirmNavigation(new NavigationEventCommand(this){
  public void execute(){
    if (com_mvp4g_example_client_product_presenter_ProductEditPresenter.isActivated(false)){
      com_mvp4g_example_client_product_presenter_ProductEditPresenter.onGoToEdit(attr0);
    }
  }
});
}
public void changeBody(com.google.gwt.user.client.ui.Widget attr0){
parentEventBus.changeBody(attr0);
}
public void goToCreation(){
confirmNavigation(new NavigationEventCommand(this){
public void execute(){
if (com_mvp4g_example_client_product_presenter_ProductCreationPresenter.isActivated(false)){
  com_mvp4g_example_client_product_presenter_ProductCreationPresenter.onGoToCreation();
}
}
});
}
public void productDeleted(com.mvp4g.example.client.product.bean.ProductBean attr0){
if (com_mvp4g_example_client_product_presenter_ProductListPresenter.isActivated(false)){
com_mvp4g_example_client_product_presenter_ProductListPresenter.onProductDeleted(attr0);
}
}
public void productCreated(com.mvp4g.example.client.product.bean.ProductBean attr0){
if (com_mvp4g_example_client_product_presenter_ProductListPresenter.isActivated(false)){
com_mvp4g_example_client_product_presenter_ProductListPresenter.onProductCreated(attr0);
}
}
public void hasBeenThere(){
Mvp4gModule module;
if (com_mvp4g_example_client_product_presenter_ProductEditPresenter.isActivated(true)){
com_mvp4g_example_client_product_presenter_ProductEditPresenter.onHasBeenThere();
}
if (com_mvp4g_example_client_product_presenter_ProductDisplayPresenter.isActivated(true)){
com_mvp4g_example_client_product_presenter_ProductDisplayPresenter.onHasBeenThere();
}
if (com_mvp4g_example_client_product_presenter_ProductCreationPresenter.isActivated(true)){
com_mvp4g_example_client_product_presenter_ProductCreationPresenter.onHasBeenThere();
}
}
public void goToDisplay(final com.mvp4g.example.client.product.bean.ProductBean attr0){
confirmNavigation(new NavigationEventCommand(this){
public void execute(){
if (com_mvp4g_example_client_product_presenter_ProductDisplayPresenter.isActivated(false)){
com_mvp4g_example_client_product_presenter_ProductDisplayPresenter.onGoToDisplay(attr0);
}
}
});
}
public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {
parentEventBus.setNavigationConfirmation(navigationConfirmation);
}
public void confirmNavigation(NavigationEventCommand event){
parentEventBus.confirmNavigation(event);
}
public void setApplicationHistoryStored( boolean historyStored ){
parentEventBus.setApplicationHistoryStored(historyStored);
}
};

com_mvp4g_example_client_product_presenter_ProductListPresenter.setEventBus(eventBus);
com_mvp4g_example_client_product_presenter_ProductEditPresenter.setEventBus(eventBus);
com_mvp4g_example_client_product_presenter_ProductDisplayPresenter.setEventBus(eventBus);
com_mvp4g_example_client_product_presenter_ProductCreationPresenter.setEventBus(eventBus);


}
public Object getStartView(){
throw new Mvp4gException("getStartView shouldn't be called since this module has no start view.");
}

public EventBus getEventBus(){
return eventBus;
}
}
