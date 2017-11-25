package de.gishmo.gwt.mvp4g2.client.ui;

import de.gishmo.gwt.mvp4g2.client.eventbus.IsEventBus;

public abstract class AbstractPresenter<E extends IsEventBus, V extends IsLazyReverseView<?>>
  extends AbstractEventHandler<E>
  implements IsPresenter<E, V> {

  protected V view = null;

  /*
   * (non-Javadoc)
   * @see com.mvp4g.client.presenter.PresenterInterface#getView()
   */
  public final V getView() {
    return view;
  }

  /*
   * (non-Javadoc)
   * @see com.mvp4g.client.presenter.PresenterInterface#setView(java.lang.Object)
   */
  public final void setView(V view) {
    this.view = view;
  }

}
