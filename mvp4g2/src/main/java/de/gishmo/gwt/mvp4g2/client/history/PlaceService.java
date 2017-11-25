package de.gishmo.gwt.mvp4g2.client.history;

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;
import de.gishmo.gwt.mvp4g2.client.eventbus.internal.EventMetaData;
import elemental2.dom.DomGlobal;

import java.util.HashMap;
import java.util.Map;

public class PlaceService<E extends IsEventBus> {

  private static final String MODULE_SEPARATOR = "/";
  private static final String URL_SEPARATOR    = "#";
  private static final String CRAWLABLE        = "!";

  private IsEventBus                                       eventBus;
  private Map<String, EventMetaData<? extends IsEventBus>> eventMetaDataMap;

  private boolean enabled = true;

  public PlaceService(E eventBus) {
    super();

    this.eventMetaDataMap = new HashMap<>();
    this.eventBus = eventBus;

    DomGlobal.window.addEventListener("popstate",
                                      (e) -> {
                                        confirmEvent(new NavigationEventCommand(eventBus) {
                                          protected void execute() {
                                            // TODO check if this will work (avoid setting history in case back or forward button of the browser is pressed ... <- does not work ...
                                            enabled = false;
                                            convertToken(getTokenFromUrl(DomGlobal.window.location.toString()));
                                            enabled = true;
                                          }
                                        });
                                      });
  }

  /**
   * Ask for user's confirmation before firing an event
   *
   * @param event event to confirm
   */
  public void confirmEvent(NavigationEventCommand event) {
    if (this.eventBus.getNavigationConfirmationPresenter() == null) {
      //no need to remove the confirmation, there is none
      event.fireEvent(false);
    } else {
      eventBus.getNavigationConfirmationPresenter()
              .confirm(event);
    }
  }

  /**
   * Convert the token to an event
   *
   * @param token the token to convert
   */
  protected void convertToken(String token) {
    boolean toContinue = false;
    if (token != null) {
      if (token.startsWith(CRAWLABLE)) {
        token = token.substring(1);
      }
      toContinue = (token.length() > 0);
    }
    if (toContinue) {
      String[] result = parseToken(token);
//      if (!forwardToChildModuleIfNeeded(result[0],
//                                        result[1])) {
      dispatchEvent(result[0],
                    result[1]);
//      }
    } else {
      eventBus.fireInitHistoryEvent();
    }
  }

  private String getTokenFromUrl(String url) {
    if (url.indexOf(PlaceService.URL_SEPARATOR) == -1) {
      return "";
    }
    return url.substring(url.indexOf(PlaceService.URL_SEPARATOR) + 1);
  }

  /**
   * Parse the token and return a string array. The first element of this array contains the event
   * name whereas the second element contains the parameters associated to the event.
   *
   * @param token token to parse
   * @return array of string
   */
  protected String[] parseToken(String token) {
    String[] result = new String[2];
    int index = token.lastIndexOf(getParamSeparator());
    result[0] = (index == -1) ?
                token :
                token.substring(0,
                                index);
    result[1] = (index == -1) ?
                null :
                token.substring(index + 1);
    return result;
  }

  /**
   * Dispatch the event thanks to the history converter.
   *
   * @param historyName name of the event stored in the token
   * @param param       parameters stored in the token
   */
  @SuppressWarnings("unchecked")
  protected void dispatchEvent(String historyName,
                               String param) {
    if (historyName != null) {
      EventMetaData<? extends IsEventBus> metaData = this.eventMetaDataMap.get(historyName);
      if (metaData != null) {
        @SuppressWarnings("rawtypes")
        IsHistoryConverter converter = metaData.getHistoryConverter();
        if (converter == null) {
          eventBus.fireNotFoundHistoryEvent();
        } else {
          String[] tab = historyName.split(MODULE_SEPARATOR);
          String finalEventName = tab[tab.length - 1];
          converter.convertFromToken(finalEventName,
                                     param,
                                     this.eventBus);
        }
      } else {
        eventBus.fireNotFoundHistoryEvent();
      }
    } else {
      eventBus.fireNotFoundHistoryEvent();
    }
  }

  /**
   * @return separator used to differenciate the event's name and its parameters
   */
  protected String getParamSeparator() {
    return "?";
  }

  public void startApplication() {
    // the last thing we do, is to add the shell to the viewport
    eventBus.setShell();
    // fire Start event
    eventBus.fireStartEvent();
    // do we have history?
    if (this.hasHistory()) {
      if (eventBus.hasHistoryOnStart()) {
        convertToken(getTokenFromUrl(DomGlobal.window.location.toString()));
      } else {
        eventBus.fireInitHistoryEvent();
      }
    }
  }

  private boolean hasHistory() {
    return this.eventMetaDataMap.values()
                                .stream()
                                .anyMatch(metaData -> metaData.getHistoryConverter() != null);
  }

  /**
   * Add a converter for an event.
   *
   * @param eventMetaData EventMetaDAta object containing all relevant informations
   */
  public void addConverter(EventMetaData<? extends IsEventBus> eventMetaData) {
    this.eventMetaDataMap.put(eventMetaData.getEventName(),
                              eventMetaData);
  }

  /**
   * Convert an event and its associated parameters to a token.<br>
   *
   * @param eventName name of the event to store
   * @param param     string representation of the objects associated with the event that needs to be
   *                  stored in the token
   * @param onlyToken if true, only the token will be generated and browser history won't change
   * @return the generated token
   */
  public String place(String eventName,
                      String param,
                      boolean onlyToken) {
    EventMetaData<? extends IsEventBus> metaData = this.eventMetaDataMap.get(eventName);

    if (!enabled && !onlyToken) {
      return null;
    }

    String token = tokenize(metaData.getHistoryName() != null && !"".equals(metaData.getHistoryName()
                                                                                    .trim()) ? metaData.getHistoryName() : metaData.getEventName(),
                            param);

//    if (converters.get(eventName)
//                  .isCrawlable()) {
//      token = CRAWLABLE + token;
//    }
    if (!onlyToken) {
      DomGlobal.window.history.pushState(param,
                                         "",
                                         PlaceService.URL_SEPARATOR + token);
    }
    return token;
  }

  /**
   * Transform an event and its parameters to a token
   *
   * @param eventName event's name
   * @param param     event's parameters
   * @return token to store in the history
   */
  private String tokenize(String eventName,
                          String param) {
    String token = eventName;
    if ((param != null) && (param.length() > 0)) {
      token = token + getParamSeparator() + param;
    }
    return token;
  }

  public IsHistoryConverter<? extends IsEventBus> getHistoryConverter(String eventName) {
    return this.eventMetaDataMap.get(eventName)
                                .getHistoryConverter();
  }
}
