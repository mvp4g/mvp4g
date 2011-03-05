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
    
    private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBus implements com.mvp4g.example.client.MailEventBus{}
    @GinModules({com.mvp4g.client.DefaultMvp4gGinModule.class})
    public interface com_mvp4g_client_Mvp4gModuleGinjector extends Ginjector {
      com.mvp4g.example.client.presenter.ShortCutsPresenter getcom_mvp4g_example_client_presenter_ShortCutsPresenter();
      com.mvp4g.example.client.presenter.TopPresenter getcom_mvp4g_example_client_presenter_TopPresenter();
      com.mvp4g.example.client.presenter.MailListPresenter getcom_mvp4g_example_client_presenter_MailListPresenter();
      com.mvp4g.example.client.presenter.MailPresenter getcom_mvp4g_example_client_presenter_MailPresenter();
      com.mvp4g.example.client.presenter.MailDetailPresenter getcom_mvp4g_example_client_presenter_MailDetailPresenter();
      com.mvp4g.example.client.presenter.NavBarPresenter getcom_mvp4g_example_client_presenter_NavBarPresenter();
      com.mvp4g.example.client.view.TopPanel getcom_mvp4g_example_client_presenter_TopPresenterView();
      com.mvp4g.example.client.view.NavBarView getcom_mvp4g_example_client_presenter_NavBarPresenterView();
      com.mvp4g.example.client.view.ShortcutsView getcom_mvp4g_example_client_presenter_ShortCutsPresenterView();
      com.mvp4g.example.client.view.MailView getcom_mvp4g_example_client_presenter_MailPresenterView();
      com.mvp4g.example.client.view.MailDetailView getcom_mvp4g_example_client_presenter_MailDetailPresenterView();
      com.mvp4g.example.client.view.MailListView getcom_mvp4g_example_client_presenter_MailListPresenterView();
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
      final com.mvp4g.example.client.view.TopPanel com_mvp4g_example_client_presenter_TopPresenterView = injector.getcom_mvp4g_example_client_presenter_TopPresenterView();
      final com.mvp4g.example.client.view.NavBarView com_mvp4g_example_client_presenter_NavBarPresenterView = injector.getcom_mvp4g_example_client_presenter_NavBarPresenterView();
      final com.mvp4g.example.client.view.ShortcutsView com_mvp4g_example_client_presenter_ShortCutsPresenterView = injector.getcom_mvp4g_example_client_presenter_ShortCutsPresenterView();
      final com.mvp4g.example.client.view.MailView com_mvp4g_example_client_presenter_MailPresenterView = injector.getcom_mvp4g_example_client_presenter_MailPresenterView();
      final com.mvp4g.example.client.view.MailDetailView com_mvp4g_example_client_presenter_MailDetailPresenterView = injector.getcom_mvp4g_example_client_presenter_MailDetailPresenterView();
      final com.mvp4g.example.client.view.MailListView com_mvp4g_example_client_presenter_MailListPresenterView = injector.getcom_mvp4g_example_client_presenter_MailListPresenterView();
      
      
      
      placeService = new com.mvp4g.client.history.PlaceService(){
        protected void sendInitEvent(){
        }
        protected void sendNotFoundEvent(){
        }
      };
      
      final com.mvp4g.example.client.presenter.ShortCutsPresenter com_mvp4g_example_client_presenter_ShortCutsPresenter = injector.getcom_mvp4g_example_client_presenter_ShortCutsPresenter();
      com_mvp4g_example_client_presenter_ShortCutsPresenter.setView(com_mvp4g_example_client_presenter_ShortCutsPresenterView);
      final com.mvp4g.example.client.presenter.TopPresenter com_mvp4g_example_client_presenter_TopPresenter = injector.getcom_mvp4g_example_client_presenter_TopPresenter();
      com_mvp4g_example_client_presenter_TopPresenter.setView(com_mvp4g_example_client_presenter_TopPresenterView);
      final com.mvp4g.example.client.presenter.MailListPresenter com_mvp4g_example_client_presenter_MailListPresenter = injector.getcom_mvp4g_example_client_presenter_MailListPresenter();
      com_mvp4g_example_client_presenter_MailListPresenter.setView(com_mvp4g_example_client_presenter_MailListPresenterView);
      final com.mvp4g.example.client.presenter.MailPresenter com_mvp4g_example_client_presenter_MailPresenter = injector.getcom_mvp4g_example_client_presenter_MailPresenter();
      com_mvp4g_example_client_presenter_MailPresenter.setView(com_mvp4g_example_client_presenter_MailPresenterView);
      final com.mvp4g.example.client.presenter.MailDetailPresenter com_mvp4g_example_client_presenter_MailDetailPresenter = injector.getcom_mvp4g_example_client_presenter_MailDetailPresenter();
      com_mvp4g_example_client_presenter_MailDetailPresenter.setView(com_mvp4g_example_client_presenter_MailDetailPresenterView);
      final com.mvp4g.example.client.presenter.NavBarPresenter com_mvp4g_example_client_presenter_NavBarPresenter = injector.getcom_mvp4g_example_client_presenter_NavBarPresenter();
      com_mvp4g_example_client_presenter_NavBarPresenter.setView(com_mvp4g_example_client_presenter_NavBarPresenterView);
      
      
      eventBus = new AbstractEventBus(){
        protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){
        return null;
        }
        public void setNavStatus(int attr0,int attr1,int attr2){
        if (com_mvp4g_example_client_presenter_NavBarPresenter.isActivated(false)){
          com_mvp4g_example_client_presenter_NavBarPresenter.onSetNavStatus(attr0,attr1,attr2);
        }
      }
      public void start(){
      if (com_mvp4g_example_client_presenter_TopPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_TopPresenter.onStart();
      }
      if (com_mvp4g_example_client_presenter_NavBarPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_NavBarPresenter.onStart();
      }
      if (com_mvp4g_example_client_presenter_MailListPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_MailListPresenter.onStart();
      }
      if (com_mvp4g_example_client_presenter_MailDetailPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_MailDetailPresenter.onStart();
      }
      if (com_mvp4g_example_client_presenter_ShortCutsPresenter.isActivated(false)){
        com_mvp4g_example_client_presenter_ShortCutsPresenter.onStart();
      }
    }
    public void older(){
    if (com_mvp4g_example_client_presenter_MailListPresenter.isActivated(false)){
      com_mvp4g_example_client_presenter_MailListPresenter.onOlder();
    }
  }
  public void newer(){
  if (com_mvp4g_example_client_presenter_MailListPresenter.isActivated(false)){
    com_mvp4g_example_client_presenter_MailListPresenter.onNewer();
  }
}
public void itemSelected(com.mvp4g.example.client.data.MailItem attr0){
if (com_mvp4g_example_client_presenter_MailDetailPresenter.isActivated(false)){
  com_mvp4g_example_client_presenter_MailDetailPresenter.onItemSelected(attr0);
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

com_mvp4g_example_client_presenter_ShortCutsPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_TopPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_MailListPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_MailPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_MailDetailPresenter.setEventBus(eventBus);
com_mvp4g_example_client_presenter_NavBarPresenter.setEventBus(eventBus);
placeService.setModule(itself);

this.startPresenter = com_mvp4g_example_client_presenter_MailPresenter;
this.startView = com_mvp4g_example_client_presenter_MailPresenterView;
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
