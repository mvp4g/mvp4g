package com.mvp4g.example.client.company;

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

public class CompanyModuleImpl implements CompanyModule {
    
    private abstract class AbstractEventBus extends com.mvp4g.client.event.BaseEventBus implements com.mvp4g.example.client.company.CompanyEventBus{}
    @GinModules({com.mvp4g.client.DefaultMvp4gGinModule.class})
    public interface com_mvp4g_example_client_company_CompanyModuleGinjector extends Ginjector {
      com.mvp4g.example.client.company.presenter.CompanyListPresenter getcom_mvp4g_example_client_company_presenter_CompanyListPresenter();
      com.mvp4g.example.client.company.presenter.CompanyCreationPresenter getcom_mvp4g_example_client_company_presenter_CompanyCreationPresenter();
      com.mvp4g.example.client.company.presenter.CompanyNameSelectorPresenter getcom_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter();
      com.mvp4g.example.client.company.presenter.CompanyDisplayPresenter getcom_mvp4g_example_client_company_presenter_CompanyDisplayPresenter();
      com.mvp4g.example.client.company.presenter.CompanyEditPresenter getcom_mvp4g_example_client_company_presenter_CompanyEditPresenter();
      com.mvp4g.example.client.company.presenter.CompanyRowPresenter getcom_mvp4g_example_client_company_presenter_CompanyRowPresenter();
      com.mvp4g.example.client.company.handler.CompanyListHandler getcom_mvp4g_example_client_company_handler_CompanyListHandler();
      com.mvp4g.example.client.company.view.CompanyRowView getcom_mvp4g_example_client_company_presenter_CompanyRowPresenterView();
      com.mvp4g.example.client.company.view.CompanyNameSelectorView getcom_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenterView();
      com.mvp4g.example.client.company.view.CompanyListView getcom_mvp4g_example_client_company_presenter_CompanyListPresenterView();
      com.mvp4g.example.client.company.view.CompanyCreationView getcom_mvp4g_example_client_company_presenter_CompanyCreationPresenterView();
      com.mvp4g.example.client.company.view.CompanyEditView getcom_mvp4g_example_client_company_presenter_CompanyEditPresenterView();
      com.mvp4g.example.client.company.view.CompanyDisplayView getcom_mvp4g_example_client_company_presenter_CompanyDisplayPresenterView();
      com.mvp4g.example.client.company.history.CompanyCreationHistoryConverter getcom_mvp4g_example_client_company_history_CompanyCreationHistoryConverter();
      com.mvp4g.example.client.company.history.CompanyHistoryConverter getcom_mvp4g_example_client_company_history_CompanyHistoryConverter();
      com.mvp4g.example.client.company.CompanyEventFilter getcom_mvp4g_example_client_company_CompanyEventFilter();
    }
    private Object startView = null;
    private PresenterInterface startPresenter = null;
    protected AbstractEventBus eventBus = null;
    protected com.mvp4g.example.client.company.CompanyModule itself = this;
    private com.mvp4g.client.Mvp4gModule parentModule = null;
    private com.mvp4g.example.client.main.MainEventBus parentEventBus = null;
    public void setParentModule(com.mvp4g.client.Mvp4gModule module){
      parentModule = module;
      parentEventBus = (com.mvp4g.example.client.main.MainEventBus) module.getEventBus();
    }
    public void addConverter(String historyName, HistoryConverter<?> hc){
      parentModule.addConverter("company/" + historyName, hc);
    }
    public void clearHistory(){
      parentModule.clearHistory();
    }
    public String place(String token, String form, boolean onlyToken){
      return parentModule.place("company/" + token, form, onlyToken );
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
      eventBus.setFilteringEnabledForNextOne(false);
      eventBus.forward();
    }
    
    public void createAndStartModule(){
      final com_mvp4g_example_client_company_CompanyModuleGinjector injector = GWT.create( com_mvp4g_example_client_company_CompanyModuleGinjector.class );
      final com.mvp4g.example.client.company.view.CompanyNameSelectorView com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenterView = injector.getcom_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenterView();
      final com.mvp4g.example.client.company.view.CompanyListView com_mvp4g_example_client_company_presenter_CompanyListPresenterView = injector.getcom_mvp4g_example_client_company_presenter_CompanyListPresenterView();
      final com.mvp4g.example.client.company.view.CompanyCreationView com_mvp4g_example_client_company_presenter_CompanyCreationPresenterView = injector.getcom_mvp4g_example_client_company_presenter_CompanyCreationPresenterView();
      final com.mvp4g.example.client.company.view.CompanyEditView com_mvp4g_example_client_company_presenter_CompanyEditPresenterView = injector.getcom_mvp4g_example_client_company_presenter_CompanyEditPresenterView();
      final com.mvp4g.example.client.company.view.CompanyDisplayView com_mvp4g_example_client_company_presenter_CompanyDisplayPresenterView = injector.getcom_mvp4g_example_client_company_presenter_CompanyDisplayPresenterView();
      
      final com.mvp4g.client.event.DefaultMvp4gLogger logger = new com.mvp4g.client.event.DefaultMvp4gLogger();
      
      
      final com.mvp4g.example.client.company.history.CompanyCreationHistoryConverter com_mvp4g_example_client_company_history_CompanyCreationHistoryConverter = injector.getcom_mvp4g_example_client_company_history_CompanyCreationHistoryConverter();
      final com.mvp4g.example.client.company.history.CompanyHistoryConverter com_mvp4g_example_client_company_history_CompanyHistoryConverter = injector.getcom_mvp4g_example_client_company_history_CompanyHistoryConverter();
      
      final com.mvp4g.example.client.company.presenter.CompanyListPresenter com_mvp4g_example_client_company_presenter_CompanyListPresenter = injector.getcom_mvp4g_example_client_company_presenter_CompanyListPresenter();
      com_mvp4g_example_client_company_presenter_CompanyListPresenter.setView(com_mvp4g_example_client_company_presenter_CompanyListPresenterView);
      final com.mvp4g.example.client.company.presenter.CompanyCreationPresenter com_mvp4g_example_client_company_presenter_CompanyCreationPresenter = injector.getcom_mvp4g_example_client_company_presenter_CompanyCreationPresenter();
      com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.setView(com_mvp4g_example_client_company_presenter_CompanyCreationPresenterView);
      final com.mvp4g.example.client.company.presenter.CompanyNameSelectorPresenter com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter = injector.getcom_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter();
      com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter.setView(com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenterView);
      final com.mvp4g.example.client.company.presenter.CompanyDisplayPresenter com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter = injector.getcom_mvp4g_example_client_company_presenter_CompanyDisplayPresenter();
      com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.setView(com_mvp4g_example_client_company_presenter_CompanyDisplayPresenterView);
      final com.mvp4g.example.client.company.presenter.CompanyEditPresenter com_mvp4g_example_client_company_presenter_CompanyEditPresenter = injector.getcom_mvp4g_example_client_company_presenter_CompanyEditPresenter();
      com_mvp4g_example_client_company_presenter_CompanyEditPresenter.setView(com_mvp4g_example_client_company_presenter_CompanyEditPresenterView);
      
      final com.mvp4g.example.client.company.handler.CompanyListHandler com_mvp4g_example_client_company_handler_CompanyListHandler = injector.getcom_mvp4g_example_client_company_handler_CompanyListHandler();
      
      eventBus = new AbstractEventBus(){
        protected <T extends EventHandlerInterface<?>> T createHandler( Class<T> handlerClass ){
          if (com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class.equals(handlerClass)){
            com.mvp4g.example.client.company.presenter.CompanyRowPresenter com_mvp4g_example_client_company_presenter_CompanyRowPresenter = injector.getcom_mvp4g_example_client_company_presenter_CompanyRowPresenter();
            com.mvp4g.example.client.company.view.CompanyRowView com_mvp4g_example_client_company_presenter_CompanyRowPresenterView = injector.getcom_mvp4g_example_client_company_presenter_CompanyRowPresenterView();
            com_mvp4g_example_client_company_presenter_CompanyRowPresenter.setView(com_mvp4g_example_client_company_presenter_CompanyRowPresenterView);
            com_mvp4g_example_client_company_presenter_CompanyRowPresenter.setEventBus(eventBus);
            return (T) com_mvp4g_example_client_company_presenter_CompanyRowPresenter;
          }
        return null;
        }
        public String goToProduct(java.lang.Integer attr0,java.lang.Integer attr1){
        if(tokenMode){
          tokenMode=false;
          ((com.mvp4g.client.event.BaseEventBus) parentEventBus).tokenMode = true;
          return parentEventBus.goToProduct(attr0,attr1);
        } else {
          int startLogDepth = BaseEventBus.logDepth;
          try {
            ++BaseEventBus.logDepth;
            logger.log("Module: CompanyModule || event: goToProduct || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
            ++BaseEventBus.logDepth;
              if (!filterEvent("goToProduct",attr0,attr1)){
                logger.log("event goToProduct didn't pass filter(s)", BaseEventBus.logDepth);
                return null;
              }
              parentEventBus.goToProduct(attr0,attr1);
            }
            finally {
              BaseEventBus.logDepth = startLogDepth;
            }
          return null;
          }
        }
        public void companyListRetrieved(java.util.List attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: companyListRetrieved || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("companyListRetrieved",attr0)){
              logger.log("event companyListRetrieved didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            com_mvp4g_example_client_company_handler_CompanyListHandler.setActivated(false);
            if (com_mvp4g_example_client_company_presenter_CompanyListPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyListPresenter.toString() + " handles companyListRetrieved", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyListPresenter.onCompanyListRetrieved(attr0);
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
          logger.log("Module: CompanyModule || event: displayMessage || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("displayMessage",attr0)){
              logger.log("event displayMessage didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            parentEventBus.displayMessage(attr0);
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void backToList(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: backToList", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("backToList")){
              logger.log("event backToList didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.company.presenter.CompanyRowPresenter> handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract = getHandlers(com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class);
            if(handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract!= null){
              com.mvp4g.example.client.company.presenter.CompanyRowPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract.get(i);
                handler.setActivated(true);
              }
            }
            com_mvp4g_example_client_company_presenter_CompanyEditPresenter.setActivated(false);
            com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.setActivated(false);
            com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.setActivated(false);
            if (com_mvp4g_example_client_company_presenter_CompanyListPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyListPresenter.toString() + " handles backToList", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyListPresenter.onBackToList();
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void companyCreated(com.mvp4g.example.client.company.bean.CompanyBean attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: companyCreated || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("companyCreated",attr0)){
              logger.log("event companyCreated didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            com_mvp4g_example_client_company_presenter_CompanyListPresenter.setActivated(true);
            if (com_mvp4g_example_client_company_presenter_CompanyListPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyListPresenter.toString() + " handles companyCreated", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyListPresenter.onCompanyCreated(attr0);
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void goToEdit(final com.mvp4g.example.client.company.bean.CompanyBean attr0){
        logger.log("Asking for user confirmation: Module: CompanyModule || event: goToEdit || param(s): " + attr0, BaseEventBus.logDepth);
        confirmNavigation(new NavigationEventCommand(this){
          public void execute(){
            int startLogDepth = BaseEventBus.logDepth;
            try {
              ++BaseEventBus.logDepth;
              logger.log("Module: CompanyModule || event: goToEdit || param(s): " + attr0, BaseEventBus.logDepth);
              ++BaseEventBus.logDepth;
                if (!filterEvent("goToEdit",attr0)){
                  logger.log("event goToEdit didn't pass filter(s)", BaseEventBus.logDepth);
                  return;
                }
                com_mvp4g_example_client_company_presenter_CompanyEditPresenter.setActivated(true);
                com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.setActivated(false);
                com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.setActivated(false);
                List<com.mvp4g.example.client.company.presenter.CompanyRowPresenter> handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde = getHandlers(com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class);
                if(handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde!= null){
                  com.mvp4g.example.client.company.presenter.CompanyRowPresenter handler;
                  int handlerCount = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde.size();
                  for(int i=0; i<handlerCount; i++){
                    handler = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde.get(i);
                    handler.setActivated(false);
                  }
                }
                if (com_mvp4g_example_client_company_presenter_CompanyEditPresenter.isActivated(false)){
                  logger.log(com_mvp4g_example_client_company_presenter_CompanyEditPresenter.toString() + " handles goToEdit", BaseEventBus.logDepth);
                  com_mvp4g_example_client_company_presenter_CompanyEditPresenter.onGoToEdit(attr0);
                }
              }
              finally {
                BaseEventBus.logDepth = startLogDepth;
              }
            }
          });
        }
        public void changeBody(com.google.gwt.user.client.ui.Widget attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: changeBody || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("changeBody",attr0)){
              logger.log("event changeBody didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            parentEventBus.changeBody(attr0);
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void displayNameSelector(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: displayNameSelector", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("displayNameSelector")){
              logger.log("event displayNameSelector didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            if (com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter.toString() + " handles displayNameSelector", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter.onDisplayNameSelector();
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void goToDisplay(final com.mvp4g.example.client.company.bean.CompanyBean attr0){
        logger.log("Asking for user confirmation: Module: CompanyModule || event: goToDisplay || param(s): " + attr0, BaseEventBus.logDepth);
        confirmNavigation(new NavigationEventCommand(this){
          public void execute(){
            int startLogDepth = BaseEventBus.logDepth;
            try {
              ++BaseEventBus.logDepth;
              logger.log("Module: CompanyModule || event: goToDisplay || param(s): " + attr0, BaseEventBus.logDepth);
              ++BaseEventBus.logDepth;
                if (!filterEvent("",attr0)){
                  logger.log("event goToDisplay didn't pass filter(s)", BaseEventBus.logDepth);
                  return;
                }
                place( itself, "",com_mvp4g_example_client_company_history_CompanyHistoryConverter.onGoToDisplay(attr0),false);
                com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.setActivated(true);
                com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.setActivated(false);
                com_mvp4g_example_client_company_presenter_CompanyEditPresenter.setActivated(false);
                List<com.mvp4g.example.client.company.presenter.CompanyRowPresenter> handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde = getHandlers(com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class);
                if(handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde!= null){
                  com.mvp4g.example.client.company.presenter.CompanyRowPresenter handler;
                  int handlerCount = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde.size();
                  for(int i=0; i<handlerCount; i++){
                    handler = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenterde.get(i);
                    handler.setActivated(false);
                  }
                }
                if (com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.isActivated(false)){
                  logger.log(com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.toString() + " handles goToDisplay", BaseEventBus.logDepth);
                  com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.onGoToDisplay(attr0);
                }
              }
              finally {
                BaseEventBus.logDepth = startLogDepth;
              }
            }
          });
        }
        public void getCompanyList(int attr0,int attr1){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: getCompanyList || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("getCompanyList",attr0,attr1)){
              logger.log("event getCompanyList didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            com_mvp4g_example_client_company_handler_CompanyListHandler.setActivated(true);
            if (com_mvp4g_example_client_company_handler_CompanyListHandler.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_handler_CompanyListHandler.toString() + " handles getCompanyList", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_handler_CompanyListHandler.onGetCompanyList(attr0,attr1);
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void companyUpdated(com.mvp4g.example.client.company.bean.CompanyBean attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: companyUpdated || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("companyUpdated",attr0)){
              logger.log("event companyUpdated didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            List<com.mvp4g.example.client.company.presenter.CompanyRowPresenter> handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract = getHandlers(com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class);
            if(handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract!= null){
              com.mvp4g.example.client.company.presenter.CompanyRowPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract.get(i);
                handler.setActivated(true);
              }
            }
            List<com.mvp4g.example.client.company.presenter.CompanyRowPresenter> handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter = getHandlers(com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class);
            if(handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter!= null){
              com.mvp4g.example.client.company.presenter.CompanyRowPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles companyUpdated", BaseEventBus.logDepth);
                  handler.onCompanyUpdated(attr0);
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void forward(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: forward", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("forward")){
              logger.log("event forward didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            if (com_mvp4g_example_client_company_presenter_CompanyListPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyListPresenter.toString() + " handles forward", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyListPresenter.onForward();
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public String goToCreation(){
        if(tokenMode){
          return place( itself, "create",null,true);
        } else {
          logger.log("Asking for user confirmation: Module: CompanyModule || event: goToCreation", BaseEventBus.logDepth);
          confirmNavigation(new NavigationEventCommand(this){
            public void execute(){
              int startLogDepth = BaseEventBus.logDepth;
              try {
                ++BaseEventBus.logDepth;
                logger.log("Module: CompanyModule || event: goToCreation", BaseEventBus.logDepth);
                ++BaseEventBus.logDepth;
                  if (!filterEvent("create")){
                    logger.log("event goToCreation didn't pass filter(s)", BaseEventBus.logDepth);
                    return;
                  }
                  place( itself, "create",null,false);
                  com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.setActivated(true);
                  com_mvp4g_example_client_company_presenter_CompanyEditPresenter.setActivated(false);
                  com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.setActivated(false);
                  if (com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.isActivated(false)){
                    logger.log(com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.toString() + " handles goToCreation", BaseEventBus.logDepth);
                    com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.onGoToCreation();
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
        public void selectCompanyMenu(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: selectCompanyMenu", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("selectCompanyMenu")){
              logger.log("event selectCompanyMenu didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            parentEventBus.selectCompanyMenu();
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void hasBeenThere(){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: hasBeenThere", BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("hasBeenThere")){
              logger.log("event hasBeenThere didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            Mvp4gModule module;
            if (com_mvp4g_example_client_company_presenter_CompanyListPresenter.isActivated(true)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyListPresenter.toString() + " handles hasBeenThere", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyListPresenter.onHasBeenThere();
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void companyDeleted(com.mvp4g.example.client.company.bean.CompanyBean attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: companyDeleted || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("companyDeleted",attr0)){
              logger.log("event companyDeleted didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            com_mvp4g_example_client_company_presenter_CompanyListPresenter.setActivated(true);
            if (com_mvp4g_example_client_company_presenter_CompanyListPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyListPresenter.toString() + " handles companyDeleted", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyListPresenter.onCompanyDeleted(attr0);
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
        }
        public void goToCompany(final int attr0,final int attr1){
        logger.log("Asking for user confirmation: Module: CompanyModule || event: goToCompany || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
        confirmNavigation(new NavigationEventCommand(this){
          public void execute(){
            int startLogDepth = BaseEventBus.logDepth;
            try {
              ++BaseEventBus.logDepth;
              logger.log("Module: CompanyModule || event: goToCompany || param(s): " + attr0+ ", " + attr1, BaseEventBus.logDepth);
              ++BaseEventBus.logDepth;
                if (!filterEvent("goToCompany",attr0,attr1)){
                  logger.log("event goToCompany didn't pass filter(s)", BaseEventBus.logDepth);
                  return;
                }
                List<com.mvp4g.example.client.company.presenter.CompanyRowPresenter> handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract = getHandlers(com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class);
                if(handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract!= null){
                  com.mvp4g.example.client.company.presenter.CompanyRowPresenter handler;
                  int handlerCount = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract.size();
                  for(int i=0; i<handlerCount; i++){
                    handler = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenteract.get(i);
                    handler.setActivated(true);
                  }
                }
                com_mvp4g_example_client_company_presenter_CompanyEditPresenter.setActivated(false);
                com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.setActivated(false);
                com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.setActivated(false);
                if (com_mvp4g_example_client_company_presenter_CompanyListPresenter.isActivated(false)){
                  logger.log(com_mvp4g_example_client_company_presenter_CompanyListPresenter.toString() + " handles goToCompany", BaseEventBus.logDepth);
                  com_mvp4g_example_client_company_presenter_CompanyListPresenter.onGoToCompany(attr0,attr1);
                }
              }
              finally {
                BaseEventBus.logDepth = startLogDepth;
              }
            }
          });
        }
        public void nameSelected(java.lang.String attr0){
        int startLogDepth = BaseEventBus.logDepth;
        try {
          ++BaseEventBus.logDepth;
          logger.log("Module: CompanyModule || event: nameSelected || param(s): " + attr0, BaseEventBus.logDepth);
          ++BaseEventBus.logDepth;
            if (!filterEvent("nameSelected",attr0)){
              logger.log("event nameSelected didn't pass filter(s)", BaseEventBus.logDepth);
              return;
            }
            if (com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.toString() + " handles nameSelected", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.onNameSelected(attr0);
            }
            if (com_mvp4g_example_client_company_presenter_CompanyEditPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyEditPresenter.toString() + " handles nameSelected", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyEditPresenter.onNameSelected(attr0);
            }
            if (com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.isActivated(false)){
              logger.log(com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.toString() + " handles nameSelected", BaseEventBus.logDepth);
              com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.onNameSelected(attr0);
            }
            List<com.mvp4g.example.client.company.presenter.CompanyRowPresenter> handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter = getHandlers(com.mvp4g.example.client.company.presenter.CompanyRowPresenter.class);
            if(handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter!= null){
              com.mvp4g.example.client.company.presenter.CompanyRowPresenter handler;
              int handlerCount = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter.size();
              for(int i=0; i<handlerCount; i++){
                handler = handlerscom_mvp4g_example_client_company_presenter_CompanyRowPresenter.get(i);
                if (handler.isActivated(false)){
                  logger.log(handler.toString() + " handles nameSelected", BaseEventBus.logDepth);
                  handler.onNameSelected(attr0);
                }
              }
            }
          }
          finally {
            BaseEventBus.logDepth = startLogDepth;
          }
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
        addConverter( "",com_mvp4g_example_client_company_history_CompanyHistoryConverter);addConverter( "create",com_mvp4g_example_client_company_history_CompanyCreationHistoryConverter);
        com_mvp4g_example_client_company_presenter_CompanyListPresenter.setEventBus(eventBus);
        com_mvp4g_example_client_company_presenter_CompanyCreationPresenter.setEventBus(eventBus);
        com_mvp4g_example_client_company_presenter_CompanyNameSelectorPresenter.setEventBus(eventBus);
        com_mvp4g_example_client_company_presenter_CompanyDisplayPresenter.setEventBus(eventBus);
        com_mvp4g_example_client_company_presenter_CompanyEditPresenter.setEventBus(eventBus);
        com_mvp4g_example_client_company_handler_CompanyListHandler.setEventBus(eventBus);
        
        final com.mvp4g.example.client.company.CompanyEventFilter com_mvp4g_example_client_company_CompanyEventFilter = injector.getcom_mvp4g_example_client_company_CompanyEventFilter();
        eventBus.addEventFilter(com_mvp4g_example_client_company_CompanyEventFilter);
        this.startPresenter = com_mvp4g_example_client_company_presenter_CompanyListPresenter;
        this.startView = com_mvp4g_example_client_company_presenter_CompanyListPresenterView;
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
