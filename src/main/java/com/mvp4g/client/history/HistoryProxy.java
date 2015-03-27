package com.mvp4g.client.history;

import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Interface to define methods needed to manage history<br>
 * <br>
 * This interface is needed in order to test without using GWT History class.<br>
 * GWT History class can't be used with JUnit test.
 *
 * @author plcoirier
 */
public interface HistoryProxy {

  /**
   * Adds a {@link com.google.gwt.event.logical.shared.ValueChangeEvent} handler to be informed of
   * changes to the browser's history stack.
   *
   * @param handler the handler
   * @return the registration used to remove this value change handler
   */
  HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler);

  /**
   * Programmatic equivalent to the user pressing the browser's 'back' button.
   * <br>
   * Note that this does not work correctly on Safari 2.
   */
  void back();

  /**
   * Fire
   * {@link ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)}
   * events with the current history state. This is most often called at the end of an
   * application's {@link com.google.gwt.core.client.EntryPoint#onModuleLoad()} to inform history
   * handlers of the initial application state.
   */
  void fireCurrentHistoryState();

  /**
   * Programmatic equivalent to the user pressing the browser's 'forward' button.
   */
  void forward();

  /**
   * Gets the current history token. The handler will not receive a
   * {@link ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)}
   * event for the initial token; requiring that an application request the token explicitly on
   * startup gives it an opportunity to run different initialization code in the presence or
   * absence of an initial token.
   *
   * @return the initial token, or the empty string if none is present.
   */
  String getToken();

  /**
   * Adds a new browser history entry. In hosted mode, the 'back' and 'forward' actions are
   * accessible via the standard Alt-Left and Alt-Right keystrokes. Calling this method will cause
   * {@link ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)}
   * to be called as well.
   *
   * @param historyToken the token to associate with the new history item
   */
  void newItem(String historyToken);

  /**
   * Adds a new browser history entry. In hosted mode, the 'back' and 'forward' actions are
   * accessible via the standard Alt-Left and Alt-Right keystrokes. Calling this method will cause
   * {@link ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)}
   * to be called as well if and only if issueEvent is true.
   *
   * @param historyToken the token to associate with the new history item
   * @param issueEvent   true if a
   *                     {@link ValueChangeHandler#onValueChange(com.google.gwt.event.logical.shared.ValueChangeEvent)}
   *                     event should be issued
   */
  void newItem(String historyToken,
               boolean issueEvent);

}
