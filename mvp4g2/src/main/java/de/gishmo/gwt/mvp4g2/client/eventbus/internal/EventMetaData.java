package de.gishmo.gwt.mvp4g2.client.eventbus.internal;

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;
import de.gishmo.gwt.mvp4g2.client.history.IsHistoryConverter;
import de.gishmo.gwt.mvp4g2.client.history.internal.HistoryMetaData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * type of the eventBus
 */
public abstract class EventMetaData<E extends IsEventBus> {

  /* historyName of the event */
  private String                eventName;
  /* event historyName -> history */
  private String                historyName;
  /* history converter meta Data*/
  private HistoryMetaData       historyMetaData;
  /* history converter */
  private IsHistoryConverter<E> historyConverter;
  /* list of the parameter types */
  private Map<String, String>   paraemterTypes;
  /* canonical names of the eventhandler to be executed on this event */
  private List<String>          handlerTypes;
  /* canonical names of the eventhandler to be executed when bind is set on this event */
  /* will only be executed in case the event is the first time called */
  private List<String>          bindHandlerTypes;
  /* flag if event is passive */
  private boolean               passive;
  /* flag if event is navigation event */
  private boolean               navigationEvent;

  public EventMetaData(String eventName,
                       String historyName,
                       HistoryMetaData historyMetaData,
                       IsHistoryConverter<E> historyConverter,
                       boolean passive,
                       boolean navigationEvent) {
    this.eventName = eventName;
    this.historyName = historyName;
    this.historyMetaData = historyMetaData;
    this.historyConverter = historyConverter;
    this.passive = passive;
    this.navigationEvent = navigationEvent;


    this.paraemterTypes = new HashMap<>();
    this.handlerTypes = new ArrayList<>();
    this.bindHandlerTypes = new ArrayList<>();
  }

  public void addBindHandler(String handler) {
    this.bindHandlerTypes.add(handler);
  }

  public void addHandler(String handler) {
    this.handlerTypes.add(handler);
  }

  public void addParameterType(String parameterName,
                               String parameterType) {
    this.paraemterTypes.put(parameterName,
                            parameterType);
  }

  public String getEventName() {
    return eventName;
  }

  public List<String> getHandlerTypes() {
    return handlerTypes;
  }

  public Map<String, String> getParaemterTypes() {
    return paraemterTypes;
  }

  public List<String> getBindHandlerTypes() {
    return bindHandlerTypes;
  }

  public boolean isPassive() {
    return passive;
  }

  public boolean isNavigationEvent() {
    return navigationEvent;
  }

  public String getHistoryName() {
    return historyName;
  }

  public HistoryMetaData getHistoryMetaData() {
    return historyMetaData;
  }

  public IsHistoryConverter<E> getHistoryConverter() {
    return historyConverter;
  }
}
