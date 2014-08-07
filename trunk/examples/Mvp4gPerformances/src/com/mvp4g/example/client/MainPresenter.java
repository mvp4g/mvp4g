package com.mvp4g.example.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DeferredCommand;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.events.*;
import com.mvp4g.example.client.presenters.TestHandler;

import java.util.Date;

@Presenter(view = MainView.class)
public class MainPresenter
  extends BasePresenter<MainPresenter.MainViewInterface, TestEventBus> {

  private HandlerManager handlerManager = new HandlerManager(null);

  @Override
  public void bind() {

    ListBox numbers = view.getNumbers();
    for (int i = 0; i < 5; i++) {
      numbers.addItem(Integer.toString(new Double(Math.pow(10,
                                                           i)).intValue()));
    }

    TestHandler handler = null;
    for (int i = 0; i < 10; i++) {
      //GWT handler
      handler = new TestHandler();
      handlerManager.addHandler(Event1.TYPE,
                                handler);
      handlerManager.addHandler(Event2.TYPE,
                                handler);
      handlerManager.addHandler(Event3.TYPE,
                                handler);
      handlerManager.addHandler(Event4.TYPE,
                                handler);
      handlerManager.addHandler(Event5.TYPE,
                                handler);
      handlerManager.addHandler(Event6.TYPE,
                                handler);
      handlerManager.addHandler(Event7.TYPE,
                                handler);
      handlerManager.addHandler(Event8.TYPE,
                                handler);
      handlerManager.addHandler(Event9.TYPE,
                                handler);
      handlerManager.addHandler(Event10.TYPE,
                                handler);
    }

    view.getStartButton().addClickHandler(new ClickHandler() {

      public void onClick(ClickEvent event) {

        view.getHandlerManagerResult().setText("");
        view.getMvp4gResult().setText("");

        DeferredCommand.addCommand(new Command() {

          public void execute() {
            String param = "eventParam";
            int nbTimes = Integer.parseInt(view.getNumbers().getValue(view.getNumbers().getSelectedIndex()));

            //Test Mvp4g Event bus
            Date start = new Date();
            for (int i = 0; i < nbTimes; i++) {
              eventBus.event1(param);
              eventBus.event2(param);
              eventBus.event3(param);
              eventBus.event4(param);
              eventBus.event5(param);
              eventBus.event6(param);
              eventBus.event7(param);
              eventBus.event8(param);
              eventBus.event9(param);
              eventBus.event10(param);
            }
            Date end = new Date();
            view.getMvp4gResult().setText(Long.toString(end.getTime() - start.getTime()));

            view.getMessageBar().setText("Testing GWT Handler Manager...please wait...");

          }

        });

        DeferredCommand.addCommand(new Command() {

          public void execute() {

            String param = "eventParam";
            int nbTimes = Integer.parseInt(view.getNumbers().getValue(view.getNumbers().getSelectedIndex()));

            //Test Handler Manager

            Event1 event1 = new Event1(param);
            Event2 event2 = new Event2(param);
            Event3 event3 = new Event3(param);
            Event4 event4 = new Event4(param);
            Event5 event5 = new Event5(param);
            Event6 event6 = new Event6(param);
            Event7 event7 = new Event7(param);
            Event8 event8 = new Event8(param);
            Event9 event9 = new Event9(param);
            Event10 event10 = new Event10(param);

            Date start = new Date();
            for (int i = 0; i < nbTimes; i++) {
              handlerManager.fireEvent(event1);
              handlerManager.fireEvent(event2);
              handlerManager.fireEvent(event3);
              handlerManager.fireEvent(event4);
              handlerManager.fireEvent(event5);
              handlerManager.fireEvent(event6);
              handlerManager.fireEvent(event7);
              handlerManager.fireEvent(event8);
              handlerManager.fireEvent(event9);
              handlerManager.fireEvent(event10);
            }
            Date end = new Date();
            view.getHandlerManagerResult().setText(Long.toString(end.getTime() - start.getTime()));

            view.getMessageBar().setText("Test completed.");

          }
        });

        view.getMessageBar().setText("Testing Mvp4g event bus...please wait...");

      }

    });

  }

  public void onStart() {
    for (int i = 0; i < 10; i++) {

    }
  }

  public interface MainViewInterface {

    public ListBox getNumbers();

    public Label getMvp4gResult();

    public Label getHandlerManagerResult();

    public HasClickHandlers getStartButton();

    public Label getMessageBar();
  }
}
