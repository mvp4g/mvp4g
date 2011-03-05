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
    
    private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBus implements com.mvp4g.example.client.TestEventBus{}
    @GinModules({com.mvp4g.client.DefaultMvp4gGinModule.class})
    public interface com_mvp4g_client_Mvp4gModuleGinjector extends Ginjector {
      com.mvp4g.example.client.presenter.UserDisplayPresenter getcom_mvp4g_example_client_presenter_UserDisplayPresenter();
      com.mvp4g.example.client.presenter.UserCreatePresenter getcom_mvp4g_example_client_presenter_UserCreatePresenter();
      com.mvp4g.example.client.presenter.RootPresenter getcom_mvp4g_example_client_presenter_RootPresenter();
      com.mvp4g.example.client.view.UserCreateView getcom_mvp4g_example_client_presenter_UserCreatePresenterView();
      com.mvp4g.example.client.view.RootView getcom_mvp4g_example_client_presenter_RootPresenterView();
      com.mvp4g.example.client.view.UserDisplayView getcom_mvp4g_example_client_presenter_UserDisplayPresenterView();
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
      final com.mvp4g.example.client.view.UserCreateView com_mvp4g_example_client_presenter_UserCreatePresenterView = injector.getcom_mvp4g_example_client_presenter_UserCreatePresenterView();
      final com.mvp4g.example.client.view.RootView com_mvp4g_example_client_presenter_RootPresenterView = injector.getcom_mvp4g_example_client_presenter_RootPresenterView();
      final com.mvp4g.example.client.view.UserDisplayView com_mvp4g_example_client_presenter_UserDisplayPresenterView = injector.getcom_mvp4g_example_client_presenter_UserDisplayPresenterView();
      
      
      
      placeService = new com.mvp4g.client.history.PlaceService(){
        protected void sendInitEvent(){
        }
        protected void sendNotFoundEvent(){
        }
      };
      
      final com.mvp4g.example.client.presenter.UserDisplayPresenter com_mvp4g_example_client_presenter_UserDisplayPresenter = injector.getcom_mvp4g_example_client_presenter_UserDisplayPresenter();
      com_mvp4g_example_client_presenter_UserDisplayPresenter.setView(com_mvp4g_example_client_presenter_UserDisplayPresenterView);
      final com.mvp4g.example.client.presenter.UserCreatePresenter com_mvp4g_example_client_presenter_UserCreatePresenter = injector.getcom_mvp4g_example_client_presenter_UserCreatePresenter();
      com_mvp4g_example_client_presenter_UserCreatePresenter.setView(com_mvp4g_example_client_presenter_UserCreatePresenterView);
      final com.mvp4g.example.client.presenter.RootPresenter com_mvp4g_example_client_presenter_RootPresenter = injector.getcom_mvp4g_example_client_presenter_RootPresenter();
      com_mvp4g_example_client_presenter_RootPresenter.setView(com_mvp4g_example_client_presenter_RootPresenterView);
      
      
      eventBus = new AbstractEventBus(){
        protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){
        return null;
        }
        public void userCreated(com.mvp4g.example.client.bean.UserBean attr0){
        if (com_mvp4g_example_client_presenter_UserDisplayPresenter.isActivated(false)){
          com_mvp4g_example_client_presenter_UserDisplayPresenter.onUserCreated(attr0);
        }
      }
      public void displayMessage(java.lang.String attr0){
      if (com_mvp4g_example_client_presenter_RootPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_RootPresenter.onDisplayMessage(attr0);
      }
    }
    public void start(){
    if (com_mvp4g_example_client_presenter_RootPresenter.isActivated(false)){
      com_mvp4g_example_client_presenter_RootPresenter.onStart();
    }
    if (com_mvp4g_example_client_presenter_UserCreatePresenter.isActivated(false)){
      com_mvp4g_example_client_presenter_UserCreatePresenter.onStart();
    }
  }
  public void changeBody(com.google.gwt.user.client.ui.Widget attr0){
  if (com_mvp4g_example_client_presenter_RootPresenter.isActivated(false)){
    com_mvp4g_example_client_presenter_RootPresenter.onChangeBody(attr0);
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

com_mvp4g_example_client_presenter_UserDisplayPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_UserCreatePresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_RootPresenter.setEventBus(eventBus);
placeService.setModule(itself);

this.startPresenter = com_mvp4g_example_client_presenter_RootPresenter;
this.startView = com_mvp4g_example_client_presenter_RootPresenterView;
eventBus.start();
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
