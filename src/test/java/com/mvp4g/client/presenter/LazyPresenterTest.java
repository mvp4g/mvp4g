package com.mvp4g.client.presenter;

import org.junit.Test;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.view.LazyView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LazyPresenterTest {

  @Test
  public void testLazyBinding() {
    LazyPresenterImpl presenter = new LazyPresenterImpl();
    LazyViewImpl      view      = new LazyViewImpl();
    presenter.setView(view);

    assertFalse(view.isCreated());
    assertFalse(presenter.isCreated());
    assertFalse(presenter.isBinded());
    presenter.bind();
    assertTrue(view.isCreated());
    assertTrue(presenter.isCreated());
    assertTrue(presenter.isBinded());

  }

  private class LazyViewImpl
    implements LazyView {

    private boolean created = false;

    public void createView() {
      created = true;
    }

    public boolean isCreated() {
      return created;
    }

  }

  private class LazyPresenterImpl
    extends LazyPresenter<LazyViewImpl, EventBus> {

    private boolean created = false;
    private boolean binded  = false;

    @Override
    public void createPresenter() {
      created = true;
    }

    @Override
    public void bindView() {
      binded = true;
    }

    public boolean isCreated() {
      return created;
    }

    public boolean isBinded() {
      return binded;
    }
  }

}
