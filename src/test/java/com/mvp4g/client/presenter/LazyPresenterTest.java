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
