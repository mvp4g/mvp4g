package com.mvp4g.rebind.test_tools.annotation;

import com.mvp4g.client.SingleSplitter;
import com.mvp4g.client.annotation.InjectService;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.rebind.test_tools.annotation.services.SimpleService;
import com.mvp4g.rebind.test_tools.annotation.services.SimpleServiceAsync;

@SuppressWarnings("deprecation")
public class Presenters {

  @Presenter(view = Object.class,
             multiple = true)
  public static class MultiplePresenter
    extends BasePresenter<Object, EventBus> {
  }

  @Presenter(view = Object.class,
             async = SingleSplitter.class)
  public static class AsyncPresenter
    extends BasePresenter<Object, EventBus> {
  }

  @Presenter(view = Object.class,
             viewName = "name")
  public static class PresenterWithViewName
    extends BasePresenter<Object, EventBus> {
  }

  @Presenter(view = Object.class)
  public static class PresenterNotPublic
    extends BasePresenter<Object, EventBus> {

    @InjectService
    void setSthg(SimpleServiceAsync service) {
    }

  }

  @Presenter(view = Object.class)
  public static class PresenterNoParameter
    extends BasePresenter<Object, EventBus> {

    @InjectService
    public void setSthg() {
    }

  }

  @Presenter(view = Object.class)
  public static class PresenterWithMoreThanOneParameter
    extends BasePresenter<Object, EventBus> {

    @InjectService
    public void setSthg(SimpleServiceAsync service,
                        Boolean test) {
    }

  }

  @Presenter(view = Object.class)
  public static class PresenterNotAsync
    extends BasePresenter<Object, EventBus> {

    @InjectService
    public void setSthg(SimpleService service) {
    }

  }

  @Presenter(view = Object.class)
  public static class PresenterWithService
    extends BasePresenter<Object, EventBus> {

    @InjectService
    public void setSthg(SimpleServiceAsync service) {
    }

  }

  @Presenter(view = Object.class)
  public static class PresenterWithSameService
    extends BasePresenter<Object, EventBus> {

    @InjectService
    public void setSthg(SimpleServiceAsync service) {
    }

  }

  @Presenter(view = Object.class)
  public static class PresenterWithServiceAndName
    extends BasePresenter<Object, EventBus> {

    @InjectService(serviceName = "name")
    public void setSthg(SimpleServiceAsync service) {
    }

  }

  @Presenter(view = Object.class)
  public static class PresenterWithEvent
    extends BasePresenter<Object, EventBus> {

    public void onEvent1(String form) {
    }

    public void onEvent2() {
    }

  }

  @Presenter(view = Object.class)
  public static class BroadcastPresenter
    extends BasePresenter<Object, EventBus>
    implements TestBroadcast {

  }

  @Presenter(view = Object.class)
  public static class BroadcastPresenter2
    extends BasePresenter<Object, EventBus>
    implements TestBroadcast,
               TestBroadcast2 {

  }

}
