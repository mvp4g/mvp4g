/*
 * Copyright (c) 2009 - 2017 - Pierre-Laurent Coirer, Frank Hossfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mvp4g.client.presenter;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.gwt_event.LoadEvent;
import com.mvp4g.client.gwt_event.LoadHandler;
import com.mvp4g.client.gwt_event.UnloadEvent;
import com.mvp4g.client.gwt_event.UnloadHandler;
import com.mvp4g.client.view.CycleView;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CyclePresenterTest {

  private TestCycleView      view;
  private TestCyclePresenter presenter;

  @Before
  public void setUp() {
    view = new TestCycleView();
    presenter = new TestCyclePresenter();
    presenter.setView(view);
  }

  @Test
  public void testLoadEvent() {
    view.fireEvent(new LoadEvent());
    assertTrue(presenter.isOnLoad());
    assertFalse(presenter.isOnUnload());
  }

  @Test
  public void testUnloadEvent() {
    view.fireEvent(new UnloadEvent());
    assertTrue(presenter.isOnUnload());
    assertFalse(presenter.isOnLoad());
  }

  @Test
  public void testBeforeEvent() {
    presenter.setActivated(false);
    presenter.isActivated(false,
                          null);
    assertFalse(presenter.isOnBeforeEvent());

    presenter.setActivated(true);
    presenter.isActivated(false,
                          null);
    assertTrue(presenter.isOnBeforeEvent());
  }

  private class TestCycleView
    implements CycleView {

    private HandlerManager eventBus = new HandlerManager(this);

    public void createView() {
      //nothing to do
    }

    public HandlerRegistration addLoadHandler(LoadHandler handler) {
      return eventBus.addHandler(LoadEvent.TYPE,
                                 handler);
    }

    public void fireEvent(GwtEvent<?> event) {
      eventBus.fireEvent(event);
    }

    public HandlerRegistration addUnloadHandler(UnloadHandler handler) {
      return eventBus.addHandler(UnloadEvent.TYPE,
                                 handler);
    }

  }

  private class TestCyclePresenter
    extends CyclePresenter<TestCycleView, EventBus> {

    private boolean onLoad, onUnload, onBeforeEvent;

    public void onLoad() {
      super.onLoad();
      onLoad = true;
    }

    public void onUnload() {
      super.onUnload();
      onUnload = true;
    }

    public void onBeforeEvent() {
      super.onBeforeEvent();
      onBeforeEvent = true;
    }

    /**
     * @return the onLoad
     */
    public boolean isOnLoad() {
      return onLoad;
    }

    /**
     * @return the onUnload
     */
    public boolean isOnUnload() {
      return onUnload;
    }

    /**
     * @return the onBeforeEvent
     */
    public boolean isOnBeforeEvent() {
      return onBeforeEvent;
    }

  }

}
