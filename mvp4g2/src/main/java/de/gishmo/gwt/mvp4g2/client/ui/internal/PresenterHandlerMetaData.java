package de.gishmo.gwt.mvp4g2.client.ui.internal;

import de.gishmo.gwt.mvp4g2.client.ui.IsLazyReverseView;
import de.gishmo.gwt.mvp4g2.client.ui.IsPresenter;
import de.gishmo.gwt.mvp4g2.client.ui.annotation.Presenter;

/**
 * type of the eventBus
 */
public abstract class PresenterHandlerMetaData<P extends IsPresenter<?, ?>, V extends IsLazyReverseView<?>>
  extends HandlerMetaData {

  protected P                              presenter;
  protected V                              view;
  private   boolean                        multiple;
  private   Presenter.VIEW_CREATION_METHOD viewCreationMethod;

  public PresenterHandlerMetaData(String canonicalName,
                                  Kind kind,
                                  boolean multiple,
                                  Presenter.VIEW_CREATION_METHOD viewCreationMethod) {
    super(canonicalName,
          kind);
    this.presenter = presenter;
    this.multiple = multiple;
    this.viewCreationMethod = viewCreationMethod;
  }

  public P getPresenter() {
    return presenter;
  }

  public V getView() {
    return view;
  }

  public boolean isMultiple() {
    return multiple;
  }

  public Presenter.VIEW_CREATION_METHOD getViewCreationMethod() {
    return viewCreationMethod;
  }
}
