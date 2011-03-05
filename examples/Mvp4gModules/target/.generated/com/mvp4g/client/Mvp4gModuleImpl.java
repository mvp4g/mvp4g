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
    
    private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBusWithLookUp implements com.mvp4g.example.client.main.MainEventBus{}
    @GinModules({com.mvp4g.example.client.Mvp4gGinModule.class})
    public interface com_mvp4g_client_Mvp4gModuleGinjector extends Ginjector {
      com.mvp4g.example.client.main.MainPresenter getcom_mvp4g_example_client_main_MainPresenter();
      com.mvp4g.example.client.main.MainView getcom_mvp4g_example_client_main_MainPresenterView();
      com.mvp4g.client.history.ClearHistory getcom_mvp4g_client_history_ClearHistory();
      com.mvp4g.example.client.main.historyConverter.MenuHistoryConverter getcom_mvp4g_example_client_main_historyConverter_MenuHistoryConverter();
    }
    private Object startView = null;
    private PresenterInterface startPresenter = null;
    protected AbstractEventBus eventBus = null;
    protected com.mvp4g.client.Mvp4gModule itself = this;
    private PlaceService placeService = null;
    public void setParentModule(com.mvp4g.client.Mvp4gModule module){}
    public java.util.Map<Class<? extends Mvp4gModule>, Mvp4gModule> modules = new java.util.HashMap<Class<? extends Mvp4gModule>, Mvp4gModule>();
    
    private void loadcom_mvp4g_example_client_company_CompanyModule(final Mvp4gEventPasser passer){
      eventBus.beforeLoad();
      GWT.runAsync(new com.google.gwt.core.client.RunAsyncCallback() {
        public void onSuccess() {
          eventBus.afterLoad();
          com.mvp4g.example.client.company.CompanyModule newModule = (com.mvp4g.example.client.company.CompanyModule) modules.get(com.mvp4g.example.client.company.CompanyModule.class);
          if(newModule == null){
            newModule = GWT.create(com.mvp4g.example.client.company.CompanyModule.class);
            modules.put(com.mvp4g.example.client.company.CompanyModule.class, newModule);
            newModule.setParentModule(itself);
            newModule.createAndStartModule();
          }
          newModule.onForward();
          eventBus.changeBody((com.google.gwt.user.client.ui.Widget) newModule.getStartView());
          if(passer != null) passer.pass(newModule);
        }
        public void onFailure(Throwable reason) {
        eventBus.afterLoad();
          eventBus.errorOnLoad(reason);
        }
      });
    }
    private void loadcom_mvp4g_example_client_product_ProductModule(final Mvp4gEventPasser passer){
      com.mvp4g.example.client.product.ProductModule newModule = (com.mvp4g.example.client.product.ProductModule) modules.get(com.mvp4g.example.client.product.ProductModule.class);
      if(newModule == null){
        newModule = GWT.create(com.mvp4g.example.client.product.ProductModule.class);
        modules.put(com.mvp4g.example.client.product.ProductModule.class, newModule);
        newModule.setParentModule(itself);
        newModule.createAndStartModule();
      }
      newModule.onForward();
      if(passer != null) passer.pass(newModule);
    }
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
        if("company".equals(moduleHistoryName)){
          loadcom_mvp4g_example_client_company_CompanyModule(nextPasser);
          return;
        }
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
      
      final com.mvp4g.example.client.main.CustomLogger logger = new com.mvp4g.example.client.main.CustomLogger();
      
      
      placeService = new com.mvp4g.example.client.main.CustomPlaceService(){
        protected void sendInitEvent(){
          eventBus.start();
        }
        protected void sendNotFoundEvent(){
          eventBus.start();
        }
      };
      final com.mvp4g.client.history.ClearHistory com_mvp4g_client_history_ClearHistory = injector.getcom_mvp4g_client_history_ClearHistory();
      final com.mvp4g.example.client.main.historyConverter.MenuHistoryConverter com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter = injector.getcom_mvp4g_example_client_main_historyConverter_MenuHistoryConverter();
      
      
      
      eventBus = new AbstractEventBus(){
        protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){
          if (com.mvp4g.example.client.main.MainPresenter.class.equals(handlerClass)){
            com.mvp4g.example.client.main.MainPresenter com_mvp4g_example_client_main_MainPresenter = injector.getcom_mvp4g_example_client_main_MainPresenter();
            com.mvp4g.example.client.main.MainView com_mvp4g_example_client_main_MainPresenterView = injector.getcom_mvp4g_example_client_main_MainPresenterView();
            com_mvp4g_example_client_main_MainPresenter.setView(com_mvp4g_example_client_main_MainPresenterView);
            com_mvp4g_example_client_main_MainPresenter.setEventBus(eventBus);
            return (T) com_mvp4g_example_client_main_MainPresenter;
          }
        return null;
        }
        public String goToProduct(final java.lang.Integer attr0,final java.lang.Integer attr1){
        if(tokenMode){
          return place( itself, "goToProduct",com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter.convertToToken("goToProduct",attr0,attr1),true);
        } else {
          logger.log("Asking for user confirmation: Module: Mvp4gModule || event: goToProduct || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
          confirmNavigation(new NavigationEventCommand(this){
            public void execute(){
              int startLogDepth = BaseEventBus.logDepth;
              try {
                ++BaseEventBus.logDepth;
                logger.log("Module: Mvp4gModule || event: goToProduct || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
                ++BaseEventBus.logDepth;
                  if (!filterEvent("goToProduct",attr0,attr1)){
                    logger.log("event goToProduct didn't pass filter(s)", BaseEventBus.logDepth);
                    return;
                  }
                  place( itself, "goToProduct",com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter.convertToToken("goToProduct",attr0,attr1),false);
                  loadcom_mvp4g_example_client_product_ProductModule(new Mvp4gEventPasser(attr0,attr1){
                    public void pass(Mvp4gModule module){
                      com.mvp4g.example.client.product.ProductEventBus eventBus = (com.mvp4g.example.client.product.ProductEventBus) module.getEventBus();
                      eventBus.goToProduct((java.lang.Integer) eventObjects[0],(java.lang.Integer) eventObjects[1]);
                    }
                  });
                  List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
                  if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
                    com.mvp4g.example.client.main.MainPresenter handler;
                    int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
                    for(int i=0; i<handlerCount; i++){
                      handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                      if (handler.isActivated(false)){
                        logger.log(handler.toString() + " handles goToProduct", BaseEventBus.logDepth);
                        handler.onGoToProduct(attr0,attr1);
                      }
                    }
                  }
                }
                finally {
                  BaseEventBus.logDepth = startLogDepth;
                }
              }
            });
          return null;
          }
        }
        public void selectProductMenu(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: selectProductMenu", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("selectProductMenu")){
              logger.log("event selectProductMenu didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles selectProductMenu", BaseEventBus.logDepth);
                  handler.onSelectProductMenu();
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void displayMessage(java.lang.String attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: displayMessage || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("displayMessage",attr0)){
              logger.log("event displayMessage didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles displayMessage", BaseEventBus.logDepth);
                  handler.onDisplayMessage(attr0);
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void errorOnLoad(java.lang.Throwable attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: errorOnLoad || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("errorOnLoad",attr0)){
              logger.log("event errorOnLoad didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles errorOnLoad", BaseEventBus.logDepth);
                  handler.onErrorOnLoad(attr0);
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void start(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: start", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("start")){
              logger.log("event start didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void changeBody(com.google.gwt.user.client.ui.Widget attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: changeBody || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("changeBody",attr0)){
              logger.log("event changeBody didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles changeBody", BaseEventBus.logDepth);
                  handler.onChangeBody(attr0);
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void beforeLoad(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: beforeLoad", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("beforeLoad")){
              logger.log("event beforeLoad didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles beforeLoad", BaseEventBus.logDepth);
                  handler.onBeforeLoad();
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void selectCompanyMenu(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: selectCompanyMenu", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("selectCompanyMenu")){
              logger.log("event selectCompanyMenu didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles selectCompanyMenu", BaseEventBus.logDepth);
                  handler.onSelectCompanyMenu();
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void hasBeenThere(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: hasBeenThere", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("hasBeenThere")){
              logger.log("event hasBeenThere didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            Mvp4gModule module;
            module = modules.get(com.mvp4g.example.client.company.CompanyModule.class);
            if(module != null){
              com.mvp4g.example.client.company.CompanyEventBus eventBus = (com.mvp4g.example.client.company.CompanyEventBus) module.getEventBus();
              eventBus.hasBeenThere();
            }
            module = modules.get(com.mvp4g.example.client.product.ProductModule.class);
            if(module != null){
              com.mvp4g.example.client.product.ProductEventBus eventBus = (com.mvp4g.example.client.product.ProductEventBus) module.getEventBus();
              eventBus.hasBeenThere();
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void afterLoad(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: afterLoad", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("afterLoad")){
              logger.log("event afterLoad didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles afterLoad", BaseEventBus.logDepth);
                  handler.onAfterLoad();
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void goToCompany(final int attr0,final int attr1){
        logger.log("Asking for user confirmation: Module: Mvp4gModule || event: goToCompany || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
        confirmNavigation(new NavigationEventCommand(this){
          public void execute(){
            int startLogDepth = BaseEventBus.logDepth;
            try {
              ++BaseEventBus.logDepth;
              logger.log("Module: Mvp4gModule || event: goToCompany || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
              ++BaseEventBus.logDepth;
                if (!filterEvent("companies",attr0,attr1)){
                  logger.log("event goToCompany didn't pass filter(s)", BaseEventBus.logDepth);
                  return;
                }
                place( itself, "companies",com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter.convertToToken("companies",attr0,attr1),false);
                loadcom_mvp4g_example_client_company_CompanyModule(new Mvp4gEventPasser(attr0,attr1){
                  public void pass(Mvp4gModule module){
                    com.mvp4g.example.client.company.CompanyEventBus eventBus = (com.mvp4g.example.client.company.CompanyEventBus) module.getEventBus();
                    eventBus.goToCompany((Integer) eventObjects[0],(Integer) eventObjects[1]);
                  }
                });
                List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
                if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
                  com.mvp4g.example.client.main.MainPresenter handler;
                  int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
                  for(int i=0; i<handlerCount; i++){
                    handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                    if (handler.isActivated(false)){
                      logger.log(handler.toString() + " handles goToCompany", BaseEventBus.logDepth);
                      handler.onGoToCompany(attr0,attr1);
                    }
                  }
                }
              }
              finally {
                BaseEventBus.logDepth = startLogDepth;
              }
            }
          });
        }
        public void clearHistory(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: Mvp4gModule || event: clearHistory", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("clearHistory")){
              logger.log("event clearHistory didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            clearHistory(itself);
            List<com.mvp4g.example.client.main.MainPresenter> handlerscom_mvp4g_example_client_main_MainPresenter = getHandlers(com.mvp4g.example.client.main.MainPresenter.class);
            if(handlerscom_mvp4g_example_client_main_MainPresenter!= null){
              com.mvp4g.example.client.main.MainPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_main_MainPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_main_MainPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles clearHistory", BaseEventBus.logDepth);
                  handler.onClearHistory();
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void dispatch( String eventType, Object... data ){
          try{
            if ( "goToProduct".equals( eventType ) ){
              goToProduct((java.lang.Integer) data[0],(java.lang.Integer) data[1]);
            } else if ( "selectProductMenu".equals( eventType ) ){
              selectProductMenu();
            } else if ( "displayMessage".equals( eventType ) ){
              displayMessage((java.lang.String) data[0]);
            } else if ( "errorOnLoad".equals( eventType ) ){
              errorOnLoad((java.lang.Throwable) data[0]);
            } else if ( "start".equals( eventType ) ){
              start();
            } else if ( "changeBody".equals( eventType ) ){
              changeBody((com.google.gwt.user.client.ui.Widget) data[0]);
            } else if ( "beforeLoad".equals( eventType ) ){
              beforeLoad();
            } else if ( "selectCompanyMenu".equals( eventType ) ){
              selectCompanyMenu();
            } else if ( "hasBeenThere".equals( eventType ) ){
              hasBeenThere();
            } else if ( "afterLoad".equals( eventType ) ){
              afterLoad();
            } else if ( "companies".equals( eventType ) ){
              goToCompany((Integer) data[0],(Integer) data[1]);
            } else if ( "clearHistory".equals( eventType ) ){
              clearHistory();
            } else {
              throw new Mvp4gException( "Event " + eventType + " doesn't exist. Have you forgotten to add it to your Mvp4g configuration file?" );
            }
          } catch ( ClassCastException e ) {
            handleClassCastException( e, eventType );
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
      addConverter( "goToProduct",com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter);addConverter( "companies",com_mvp4g_example_client_main_historyConverter_MenuHistoryConverter);
      placeService.setModule(itself);
      
      this.startPresenter = eventBus.addHandler(com.mvp4g.example.client.main.MainPresenter.class);
      this.startView = startPresenter.getView();
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
