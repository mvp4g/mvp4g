package com.mvp4g.client;

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

public class Mvp4gModuleImpl implements Mvp4gModule {
    
    private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBus implements com.mvp4g.example.client.MyEventBus{}
    @GinModules({com.mvp4g.client.DefaultMvp4gGinModule.class})
    public interface com_mvp4g_client_Mvp4gModuleGinjector extends Ginjector {
      com.mvp4g.example.client.presenter.AccountPresenter getcom_mvp4g_example_client_presenter_AccountPresenter();
      com.mvp4g.example.client.presenter.display.DealDisplayPresenter getcom_mvp4g_example_client_presenter_display_DealDisplayPresenter();
      com.mvp4g.example.client.presenter.display.CartDisplayPresenter getcom_mvp4g_example_client_presenter_display_CartDisplayPresenter();
      com.mvp4g.example.client.presenter.LoginPresenter getcom_mvp4g_example_client_presenter_LoginPresenter();
      com.mvp4g.example.client.presenter.display.ProductDisplayPresenter getcom_mvp4g_example_client_presenter_display_ProductDisplayPresenter();
      com.mvp4g.example.client.presenter.RootTemplatePresenter getcom_mvp4g_example_client_presenter_RootTemplatePresenter();
      com.mvp4g.example.client.presenter.TopBarPresenter getcom_mvp4g_example_client_presenter_TopBarPresenter();
      com.mvp4g.example.client.view.LoginView getcom_mvp4g_example_client_presenter_LoginPresenterView();
      com.mvp4g.example.client.view.display.ProductDisplayView getcom_mvp4g_example_client_presenter_display_ProductDisplayPresenterView();
      com.mvp4g.example.client.view.display.CartDisplayView getcom_mvp4g_example_client_presenter_display_CartDisplayPresenterView();
      com.mvp4g.example.client.view.TopBarView getcom_mvp4g_example_client_presenter_TopBarPresenterView();
      com.mvp4g.example.client.view.AccountView getcom_mvp4g_example_client_presenter_AccountPresenterView();
      com.mvp4g.example.client.view.RootTemplateView getcom_mvp4g_example_client_presenter_RootTemplatePresenterView();
      com.mvp4g.example.client.view.display.DealDisplayView getcom_mvp4g_example_client_presenter_display_DealDisplayPresenterView();
      com.mvp4g.example.client.history.ProductHistoryConverter getcom_mvp4g_example_client_history_ProductHistoryConverter();
      com.mvp4g.example.client.history.ShowCartConverter getcom_mvp4g_example_client_history_ShowCartConverter();
      com.mvp4g.example.client.history.DealHistoryConverter getcom_mvp4g_example_client_history_DealHistoryConverter();
    }
    private Object startView = null;
    private PresenterInterface startPresenter = null;
    protected AbstractEventBus eventBus = null;
    protected com.mvp4g.client.Mvp4gModule itself = this;
    private PlaceService placeService = null;
    public void setParentModule(com.mvp4g.client.Mvp4gModule module){}
    public void addConverter(String historyName, HistoryConverter<?> hc){
      placeService.addConverter(historyName, hc);
    }
    public void clearHistory(){
      placeService.clearHistory();
    }
    public String place(String token, String form, boolean onlyToken){
      return placeService.place( token, form, onlyToken );
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
      final com_mvp4g_client_Mvp4gModuleGinjector injector = GWT.create( com_mvp4g_client_Mvp4gModuleGinjector.class );
      final com.mvp4g.example.client.view.LoginView com_mvp4g_example_client_presenter_LoginPresenterView = injector.getcom_mvp4g_example_client_presenter_LoginPresenterView();
      final com.mvp4g.example.client.view.display.ProductDisplayView com_mvp4g_example_client_presenter_display_ProductDisplayPresenterView = injector.getcom_mvp4g_example_client_presenter_display_ProductDisplayPresenterView();
      final com.mvp4g.example.client.view.display.CartDisplayView com_mvp4g_example_client_presenter_display_CartDisplayPresenterView = injector.getcom_mvp4g_example_client_presenter_display_CartDisplayPresenterView();
      final com.mvp4g.example.client.view.TopBarView com_mvp4g_example_client_presenter_TopBarPresenterView = injector.getcom_mvp4g_example_client_presenter_TopBarPresenterView();
      final com.mvp4g.example.client.view.AccountView com_mvp4g_example_client_presenter_AccountPresenterView = injector.getcom_mvp4g_example_client_presenter_AccountPresenterView();
      final com.mvp4g.example.client.view.RootTemplateView com_mvp4g_example_client_presenter_RootTemplatePresenterView = injector.getcom_mvp4g_example_client_presenter_RootTemplatePresenterView();
      final com.mvp4g.example.client.view.display.DealDisplayView com_mvp4g_example_client_presenter_display_DealDisplayPresenterView = injector.getcom_mvp4g_example_client_presenter_display_DealDisplayPresenterView();
      
      
      
      placeService = new com.mvp4g.client.history.PlaceService(){
        protected void sendInitEvent(){
          eventBus.init();
        }
        protected void sendNotFoundEvent(){
          eventBus.notFound();
        }
      };
      final com.mvp4g.example.client.history.ProductHistoryConverter com_mvp4g_example_client_history_ProductHistoryConverter = injector.getcom_mvp4g_example_client_history_ProductHistoryConverter();
      final com.mvp4g.example.client.history.ShowCartConverter com_mvp4g_example_client_history_ShowCartConverter = injector.getcom_mvp4g_example_client_history_ShowCartConverter();
      final com.mvp4g.example.client.history.DealHistoryConverter com_mvp4g_example_client_history_DealHistoryConverter = injector.getcom_mvp4g_example_client_history_DealHistoryConverter();
      
      final com.mvp4g.example.client.presenter.AccountPresenter com_mvp4g_example_client_presenter_AccountPresenter = injector.getcom_mvp4g_example_client_presenter_AccountPresenter();
      com_mvp4g_example_client_presenter_AccountPresenter.setView(com_mvp4g_example_client_presenter_AccountPresenterView);
      final com.mvp4g.example.client.presenter.display.DealDisplayPresenter com_mvp4g_example_client_presenter_display_DealDisplayPresenter = injector.getcom_mvp4g_example_client_presenter_display_DealDisplayPresenter();
      com_mvp4g_example_client_presenter_display_DealDisplayPresenter.setView(com_mvp4g_example_client_presenter_display_DealDisplayPresenterView);
      final com.mvp4g.example.client.presenter.display.CartDisplayPresenter com_mvp4g_example_client_presenter_display_CartDisplayPresenter = injector.getcom_mvp4g_example_client_presenter_display_CartDisplayPresenter();
      com_mvp4g_example_client_presenter_display_CartDisplayPresenter.setView(com_mvp4g_example_client_presenter_display_CartDisplayPresenterView);
      final com.mvp4g.example.client.presenter.LoginPresenter com_mvp4g_example_client_presenter_LoginPresenter = injector.getcom_mvp4g_example_client_presenter_LoginPresenter();
      com_mvp4g_example_client_presenter_LoginPresenter.setView(com_mvp4g_example_client_presenter_LoginPresenterView);
      final com.mvp4g.example.client.presenter.display.ProductDisplayPresenter com_mvp4g_example_client_presenter_display_ProductDisplayPresenter = injector.getcom_mvp4g_example_client_presenter_display_ProductDisplayPresenter();
      com_mvp4g_example_client_presenter_display_ProductDisplayPresenter.setView(com_mvp4g_example_client_presenter_display_ProductDisplayPresenterView);
      final com.mvp4g.example.client.presenter.RootTemplatePresenter com_mvp4g_example_client_presenter_RootTemplatePresenter = injector.getcom_mvp4g_example_client_presenter_RootTemplatePresenter();
      com_mvp4g_example_client_presenter_RootTemplatePresenter.setView(com_mvp4g_example_client_presenter_RootTemplatePresenterView);
      final com.mvp4g.example.client.presenter.TopBarPresenter com_mvp4g_example_client_presenter_TopBarPresenter = injector.getcom_mvp4g_example_client_presenter_TopBarPresenter();
      com_mvp4g_example_client_presenter_TopBarPresenter.setView(com_mvp4g_example_client_presenter_TopBarPresenterView);
      
      
      eventBus = new AbstractEventBus(){
        protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){
        return null;
        }
        public void displayMessage(java.lang.String attr0){
        if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
          com_mvp4g_example_client_presenter_RootTemplatePresenter.onDisplayMessage(attr0);
        }
      }
      public void displayDeal(com.mvp4g.example.client.bean.DealBean attr0){
      place( itself, "displayDeal",com_mvp4g_example_client_history_DealHistoryConverter.onDisplayDeal(attr0),false);
      if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_RootTemplatePresenter.onDisplayDeal(attr0);
      }
      if (com_mvp4g_example_client_presenter_display_DealDisplayPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_display_DealDisplayPresenter.onDisplayDeal(attr0);
      }
      if (com_mvp4g_example_client_presenter_TopBarPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_TopBarPresenter.onDisplayDeal(attr0);
      }
    }
    public void start(){
    if (com_mvp4g_example_client_presenter_TopBarPresenter.isActivated(false)){
      com_mvp4g_example_client_presenter_TopBarPresenter.onStart();
    }
    if (com_mvp4g_example_client_presenter_LoginPresenter.isActivated(false)){
      com_mvp4g_example_client_presenter_LoginPresenter.onStart();
    }
  }
  public void changeBottomWidget(com.mvp4g.example.client.widget.IView attr0){
  if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
    com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeBottomWidget(attr0);
  }
}
public void init(){
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
  com_mvp4g_example_client_presenter_RootTemplatePresenter.onInit();
}
if (com_mvp4g_example_client_presenter_TopBarPresenter.isActivated(false)){
  com_mvp4g_example_client_presenter_TopBarPresenter.onInit();
}
}
public void login(java.lang.String attr0){
if (com_mvp4g_example_client_presenter_AccountPresenter.isActivated(false)){
com_mvp4g_example_client_presenter_AccountPresenter.onLogin(attr0);
}
}
public void displayCart(java.lang.String attr0){
place( itself, "displayCart",null,false);
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_RootTemplatePresenter.onDisplayCart(attr0);
}
if (com_mvp4g_example_client_presenter_display_CartDisplayPresenter.isActivated(false)){
com_mvp4g_example_client_presenter_display_CartDisplayPresenter.onDisplayCart(attr0);
}
}
public void notFound(){
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_RootTemplatePresenter.onNotFound();
}
}
public void displayProduct(com.mvp4g.example.client.bean.ProductBean attr0){
place( itself, "displayProduct",com_mvp4g_example_client_history_ProductHistoryConverter.onDisplayProduct(attr0),false);
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_RootTemplatePresenter.onDisplayProduct(attr0);
}
if (com_mvp4g_example_client_presenter_display_ProductDisplayPresenter.isActivated(false)){
com_mvp4g_example_client_presenter_display_ProductDisplayPresenter.onDisplayProduct(attr0);
}
if (com_mvp4g_example_client_presenter_TopBarPresenter.isActivated(false)){
com_mvp4g_example_client_presenter_TopBarPresenter.onDisplayProduct(attr0);
}
}
public void changeTopWidget(com.mvp4g.example.client.widget.IView attr0){
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeTopWidget(attr0);
}
}
public void changeMainWidget(com.mvp4g.example.client.widget.IView attr0){
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeMainWidget(attr0);
}
}
public void setNavigationConfirmation( NavigationConfirmationInterface navigationConfirmation ) {
placeService.setNavigationConfirmation(navigationConfirmation);
}
public void confirmNavigation(NavigationEventCommand event){
placeService.confirmEvent(event);
}
public void setApplicationHistoryStored( boolean historyStored ){
placeService.setEnabled(historyStored);
}
};
addConverter( "displayDeal",com_mvp4g_example_client_history_DealHistoryConverter);addConverter( "displayCart",com_mvp4g_example_client_history_ShowCartConverter);addConverter( "displayProduct",com_mvp4g_example_client_history_ProductHistoryConverter);
com_mvp4g_example_client_presenter_AccountPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_display_DealDisplayPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_display_CartDisplayPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_LoginPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_display_ProductDisplayPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_RootTemplatePresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_TopBarPresenter.setEventBus(eventBus);
placeService.setModule(itself);

this.startPresenter = com_mvp4g_example_client_presenter_RootTemplatePresenter;
this.startView = com_mvp4g_example_client_presenter_RootTemplatePresenterView;
eventBus.start();
History.fireCurrentHistoryState();
}
public Object getStartView(){
if (startPresenter != null) {
startPresenter.setActivated(true);
startPresenter.isActivated(false);
}return startView;
}

public EventBus getEventBus(){
return eventBus;
}
}
