package com.mvp4g.rebind.test_tools.annotation;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventFilter;

public class EventFilters {

  public static class EventFilter1
    implements EventFilter<EventBus> {

    public boolean filterEvent(String eventType,
                               Object[] params,
                               EventBus eventBus) {
      return false;
    }

  }

  public static class EventFilter2
    implements EventFilter<EventBus> {

    public boolean filterEvent(String eventType,
                               Object[] params,
                               EventBus eventBus) {
      return false;
    }

  }

  public static class EventFilter3
    implements EventFilter<EventBusWithLookup> {

    public boolean filterEvent(String eventType,
                               Object[] params,
                               EventBusWithLookup eventBus) {
      return false;
    }

  }

}
