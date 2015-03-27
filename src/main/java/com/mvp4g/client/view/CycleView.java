package com.mvp4g.client.view;

import com.mvp4g.client.gwt_event.HasLoadHandlers;
import com.mvp4g.client.gwt_event.HasUnloadHandlers;
import com.mvp4g.client.gwt_event.LoadEvent;
import com.mvp4g.client.gwt_event.UnloadEvent;

/**
 * Represent a view that can be injected to a CyclePresenter. Any class implementing this interface
 * should fire {@link LoadEvent} and {@link UnloadEvent} when it is attached to/detached from the
 * DOM.
 *
 * @author plcoirier
 */
public interface CycleView
  extends LazyView,
          HasLoadHandlers,
          HasUnloadHandlers {

}
