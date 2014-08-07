package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event5
  extends GwtEvent<Event5.Event5Handler> {

  public static final GwtEvent.Type<Event5.Event5Handler> TYPE = new GwtEvent.Type<Event5.Event5Handler>();
  private String event = null;

  public Event5(String event) {
    this.event = event;
  }

  public String getEvent() {
    return event;
  }

  @Override
  protected void dispatch(Event5Handler handler) {
    handler.handle(this);
  }

  @Override
  public Type<Event5.Event5Handler> getAssociatedType() {
    return TYPE;
  }

  public interface Event5Handler
    extends EventHandler {
    void handle(Event5 event);
  }

}
