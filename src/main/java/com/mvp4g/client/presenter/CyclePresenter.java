package com.mvp4g.client.presenter;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.gwt_event.LoadEvent;
import com.mvp4g.client.gwt_event.LoadHandler;
import com.mvp4g.client.gwt_event.UnloadEvent;
import com.mvp4g.client.gwt_event.UnloadHandler;
import com.mvp4g.client.view.CycleView;

/**
 * Presenter that receives from its view a {@link LoadEvent} when the view is attached to the DOM
 * and a {@link UnloadEvent} when the view is detached to the DOM.
 *
 * @param <V> Type of the view injected into the presenter. Must extends <code>CycleView</code>.
 * @param <E> Type of the event bus used by the presenter.
 * @author plcoirier
 */
public class CyclePresenter<V extends CycleView, E extends EventBus>
  extends LazyPresenter<V, E>
  implements LoadHandler,
             UnloadHandler {

  @Override
  public void setView(V view) {
    super.setView(view);
    view.addLoadHandler(this);
    view.addUnloadHandler(this);
  }

  /**
   * Called when the view is loaded.
   */
  public void onLoad() {

  }

  /**
   * Called when the view is unloaded.
   */
  public void onUnload() {

  }

}
