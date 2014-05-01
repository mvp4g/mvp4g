package com.mvp4g.example.client.company;

import com.mvp4g.client.event.EventFilter;

public class CompanyEventFilter
  implements EventFilter<CompanyEventBus> {

  public boolean filterEvent(String eventType,
                             Object[] params,
                             CompanyEventBus eventBus) {
    eventBus.setFilteringEnabledForNextOne(false);
    eventBus.displayMessage("Company Event " + eventType + " has been filtered.");
    return false;
  }
}
