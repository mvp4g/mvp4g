package com.mvp4g.example.client.main.presenter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mvp4g.client.Mvp4gLoader;
import com.mvp4g.client.Mvp4gModule;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.main.MainEventBus;
import com.mvp4g.example.client.main.MainEventFilter;
import com.mvp4g.example.client.main.view.MainView;
import com.mvp4g.example.client.util.index.IndexGenerator;

import java.util.HashMap;
import java.util.Map;

@Singleton
@Presenter(view = MainView.class)
public class MainPresenter
  extends BasePresenter<MainPresenter.MainViewInterface, MainEventBus>
  implements Mvp4gLoader<MainEventBus> {

  public Map<Class<? extends Mvp4gModule>, Mvp4gModule> modules = new HashMap<Class<? extends Mvp4gModule>, Mvp4gModule>();

  public interface MainViewInterface {

    HasClickHandlers getCompanyMenu();

    HasClickHandlers getProductMenu();

    void setBody(IsWidget newBody);

    void displayErrorMessage(String error);

    void setWaitVisible(boolean visible);

    void displayText(String message);

    void selectCompanyMenu();

    void selectProductMenu();

    HasClickHandlers getClearHistoryButton();

    void alert(String message);

    int getStartIndex();

    int getLastIndex();

    HasValue<Boolean> getFilter();

    HasValue<Boolean> getFilterByActivate();

    HasValue<Boolean> getCompanyModuleFilter();

    HasClickHandlers getHasBeenThere();

    HasClickHandlers getBroadcastInfo();

    HasClickHandlers getShowStatus();

    HasClickHandlers getActivateStatus();

    void setActivateText(boolean showActivate);

  }

  @Inject
  private IndexGenerator indexGenerator;

  // have this filter to test force filter option & add/remove event filter
  private MainEventFilter filter = new MainEventFilter();

  private int infoCount = 0;

  private boolean isStatusActivated = true;

  public void bind() {
    view.getCompanyMenu().addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        eventBus.goToCompany(indexGenerator.generateIndex(view.getStartIndex()),
                             indexGenerator.generateIndex(view.getLastIndex()));
      }
    });
    view.getProductMenu().addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        eventBus.goToProduct(indexGenerator.generateIndex(view.getStartIndex()),
                             indexGenerator.generateIndex(view.getLastIndex()));
      }
    });
    view.getClearHistoryButton().addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        eventBus.clearHistory();
      }
    });
    view.getFilter().addValueChangeHandler(new ValueChangeHandler<Boolean>() {

      public void onValueChange(ValueChangeEvent<Boolean> event) {
        if (event.getValue()) {
          eventBus.addEventFilter(filter);
        } else {
          eventBus.removeEventFilter(filter);
        }
      }
    });
    view.getHasBeenThere().addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {
        eventBus.hasBeenThere();
      }

    });
    view.getBroadcastInfo().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        eventBus.broadcastInfo(new String[]{"Info" + infoCount++, "Info" + infoCount++});
      }

    });
    view.getShowStatus().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        eventBus.showStatus("");
      }

    });
    view.getActivateStatus().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {
        if (isStatusActivated) {
          eventBus.deactivateStatus();
        } else {
          eventBus.activateStatus();
        }
        view.setActivateText(isStatusActivated);
        isStatusActivated = !isStatusActivated;
      }

    });
    view.setActivateText(!isStatusActivated);
  }

  public void onChangeBody(IsWidget w) {
    view.setBody(w);
    onDisplayMessage("");
  }

  public void onErrorOnLoad(Throwable reason) {
    view.displayErrorMessage(reason.getMessage());
  }

  public void onBeforeLoad() {
    view.setWaitVisible(true);
  }

  public void onAfterLoad() {
    view.setWaitVisible(false);
  }

  public void onDisplayMessage(String message) {
    view.displayText(message);
  }

  public void onGoToCompany(int start,
                            int end) {
    view.selectCompanyMenu();
  }

  public void onGoToProduct(Integer start,
                            Integer end) {
    view.selectProductMenu();
  }

  public void onSelectProductMenu() {
    view.selectProductMenu();
  }

  public void onSelectCompanyMenu() {
    view.selectCompanyMenu();
  }

  public void onClearHistory() {
    view.alert("History has been cleared");
  }

  public void onBroadcastInfoFromProduct(String info) {
    view.alert("Main Info from product: " + info);
  }

  public void onBroadcastInfoFromProductPassive(String info) {
    view.alert("Main Info from passive product: " + info);
  }

  @Override
  public boolean pass(String eventName,
                      Object... parameters) {
    if (view.getFilterByActivate().getValue()) {
      view.displayText("Filtered by Activate: " + eventName);
      return false;
    } else {
      return true;
    }
  }

  @Override
  public void preLoad(MainEventBus eventBus,
                      String eventName,
                      Object[] params,
                      Command load) {
    if (view.getCompanyModuleFilter().getValue()) {
      if (eventName != null) {
        view.displayText("Event " + eventName + " hasn't been forwarded to CompanyModule and/or StatusSplitter.");
      } else {
        view.displayText("History change wasn't forwarded to Company module.");
      }
    } else {
      load.execute();
    }
  }

  @Override
  public void onSuccess(MainEventBus eventBus,
                        final String eventName,
                        Object[] params) {
    // Use a scheduler because when I switch body, the
    Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
      @Override
      public void execute() {
        if (eventName != null) {
          view.displayText("Event " + eventName + " has been forwarded to CompanyModule and/or StatusSplitter.");
        } else {
          view.displayText("CompanyModule loaded because of History change.");
        }
      }
    });
  }

  @Override
  public void onFailure(MainEventBus eventBus,
                        String eventName,
                        Object[] params,
                        Throwable err) {
    view.displayText("Event " + eventName + " has been forwarded to CompanyModule and/or StatusSplitter but it couldn't be loaded: " + err);
  }

}
