package com.mvp4g.example.client.events;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class Event4
  extends GwtEvent<Event4.Event4Handler> {

  public static final GwtEvent.Type<Event4.Event4Handler> TYPE = new GwtEvent.Type<Event4.Event4Handler>();
  private String event = null;

  public Event4(String event) {
    this.event = event;
  }

  public String getEvent() {
    return event;
  }

  @Override
  protected void dispatch(Event4Handler handler) {
    handler.handle(this);
  }

  @Override
  public Type<Event4.Event4Handler> getAssociatedType() {
    return TYPE;
  }

  public interface Event4Handler
    extends EventHandler {
    void handle(Event4 event);
  }

}
