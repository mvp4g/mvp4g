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
    
    private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBus implements com.mvp4g.example.client.EmployeeAdminEventBus{}
    @GinModules({com.mvp4g.client.DefaultMvp4gGinModule.class})
    public interface com_mvp4g_client_Mvp4gModuleGinjector extends Ginjector {
      com.mvp4g.example.client.presenter.UserProfilePresenter getcom_mvp4g_example_client_presenter_UserProfilePresenter();
      com.mvp4g.example.client.presenter.UserRolePresenter getcom_mvp4g_example_client_presenter_UserRolePresenter();
      com.mvp4g.example.client.presenter.UserListPresenter getcom_mvp4g_example_client_presenter_UserListPresenter();
      com.mvp4g.example.client.presenter.RootTemplatePresenter getcom_mvp4g_example_client_presenter_RootTemplatePresenter();
      com.mvp4g.example.client.view.UserRoleView getcom_mvp4g_example_client_presenter_UserRolePresenterView();
      com.mvp4g.example.client.view.UserProfileView getcom_mvp4g_example_client_presenter_UserProfilePresenterView();
      com.mvp4g.example.client.view.UserListView getcom_mvp4g_example_client_presenter_UserListPresenterView();
      com.mvp4g.example.client.view.RootTemplateView getcom_mvp4g_example_client_presenter_RootTemplatePresenterView();
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
      final com.mvp4g.example.client.view.UserRoleView com_mvp4g_example_client_presenter_UserRolePresenterView = injector.getcom_mvp4g_example_client_presenter_UserRolePresenterView();
      final com.mvp4g.example.client.view.UserProfileView com_mvp4g_example_client_presenter_UserProfilePresenterView = injector.getcom_mvp4g_example_client_presenter_UserProfilePresenterView();
      final com.mvp4g.example.client.view.UserListView com_mvp4g_example_client_presenter_UserListPresenterView = injector.getcom_mvp4g_example_client_presenter_UserListPresenterView();
      final com.mvp4g.example.client.view.RootTemplateView com_mvp4g_example_client_presenter_RootTemplatePresenterView = injector.getcom_mvp4g_example_client_presenter_RootTemplatePresenterView();
      
      
      
      placeService = new com.mvp4g.client.history.PlaceService(){
        protected void sendInitEvent(){
        }
        protected void sendNotFoundEvent(){
        }
      };
      
      final com.mvp4g.example.client.presenter.UserProfilePresenter com_mvp4g_example_client_presenter_UserProfilePresenter = injector.getcom_mvp4g_example_client_presenter_UserProfilePresenter();
      com_mvp4g_example_client_presenter_UserProfilePresenter.setView(com_mvp4g_example_client_presenter_UserProfilePresenterView);
      final com.mvp4g.example.client.presenter.UserRolePresenter com_mvp4g_example_client_presenter_UserRolePresenter = injector.getcom_mvp4g_example_client_presenter_UserRolePresenter();
      com_mvp4g_example_client_presenter_UserRolePresenter.setView(com_mvp4g_example_client_presenter_UserRolePresenterView);
      final com.mvp4g.example.client.presenter.UserListPresenter com_mvp4g_example_client_presenter_UserListPresenter = injector.getcom_mvp4g_example_client_presenter_UserListPresenter();
      com_mvp4g_example_client_presenter_UserListPresenter.setView(com_mvp4g_example_client_presenter_UserListPresenterView);
      final com.mvp4g.example.client.presenter.RootTemplatePresenter com_mvp4g_example_client_presenter_RootTemplatePresenter = injector.getcom_mvp4g_example_client_presenter_RootTemplatePresenter();
      com_mvp4g_example_client_presenter_RootTemplatePresenter.setView(com_mvp4g_example_client_presenter_RootTemplatePresenterView);
      
      
      eventBus = new AbstractEventBus(){
        protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){
        return null;
        }
        public void selectUser(com.mvp4g.example.client.bean.UserBean attr0){
        if (com_mvp4g_example_client_presenter_UserProfilePresenter.isActivated(false)){
          com_mvp4g_example_client_presenter_UserProfilePresenter.onSelectUser(attr0);
        }
        if (com_mvp4g_example_client_presenter_UserRolePresenter.isActivated(false)){
          com_mvp4g_example_client_presenter_UserRolePresenter.onSelectUser(attr0);
        }
      }
      public void createNewUser(com.mvp4g.example.client.bean.UserBean attr0){
      if (com_mvp4g_example_client_presenter_UserProfilePresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_UserProfilePresenter.onCreateNewUser(attr0);
      }
      if (com_mvp4g_example_client_presenter_UserRolePresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_UserRolePresenter.onCreateNewUser(attr0);
      }
    }
    public void changeLeftBottomWidget(com.mvp4g.example.client.widget.interfaces.IWidget attr0){
    if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
      com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeLeftBottomWidget(attr0);
    }
  }
  public void userCreated(com.mvp4g.example.client.bean.UserBean attr0){
  if (com_mvp4g_example_client_presenter_UserListPresenter.isActivated(false)){
    com_mvp4g_example_client_presenter_UserListPresenter.onUserCreated(attr0);
  }
}
public void changeRightBottomWidget(com.mvp4g.example.client.widget.interfaces.IWidget attr0){
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
  com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeRightBottomWidget(attr0);
}
}
public void start(){
if (com_mvp4g_example_client_presenter_UserProfilePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserProfilePresenter.onStart();
}
if (com_mvp4g_example_client_presenter_UserRolePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserRolePresenter.onStart();
}
if (com_mvp4g_example_client_presenter_UserListPresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserListPresenter.onStart();
}
}
public void userUpdated(com.mvp4g.example.client.bean.UserBean attr0){
if (com_mvp4g_example_client_presenter_UserRolePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserRolePresenter.onUserUpdated(attr0);
}
if (com_mvp4g_example_client_presenter_UserListPresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserListPresenter.onUserUpdated(attr0);
}
}
public void unselectUser(){
if (com_mvp4g_example_client_presenter_UserProfilePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserProfilePresenter.onUnselectUser();
}
if (com_mvp4g_example_client_presenter_UserRolePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserRolePresenter.onUnselectUser();
}
if (com_mvp4g_example_client_presenter_UserListPresenter.isActivated(false)){
com_mvp4g_example_client_presenter_UserListPresenter.onUnselectUser();
}
}
public void changeTopWidget(com.mvp4g.example.client.widget.interfaces.IWidget attr0){
if (com_mvp4g_example_client_presenter_RootTemplatePresenter.isActivated(false)){
com_mvp4g_example_client_presenter_RootTemplatePresenter.onChangeTopWidget(attr0);
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

com_mvp4g_example_client_presenter_UserProfilePresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_UserRolePresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_UserListPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_RootTemplatePresenter.setEventBus(eventBus);
placeService.setModule(itself);

this.startPresenter = com_mvp4g_example_client_presenter_RootTemplatePresenter;
this.startView = com_mvp4g_example_client_presenter_RootTemplatePresenterView;
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
