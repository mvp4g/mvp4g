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

package com.mvp4g.rebind.test_tools.annotation.views;

import com.mvp4g.client.view.ReverseViewInterface;
import com.mvp4g.rebind.test_tools.annotation.presenters.SimplePresenter;

public class SimpleInjectedView
  extends SimpleView
  implements ReverseViewInterface<SimplePresenter> {

  SimplePresenter presenter;

  public SimplePresenter getPresenter() {
    return presenter;
  }

  public void setPresenter(SimplePresenter presenter) {
    this.presenter = presenter;
  }
}
