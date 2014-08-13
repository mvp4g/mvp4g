/*
 * Copyright 2007 Google Inc.
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
package com.mvp4g.example.client.ui.detail;

import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.client.presenter.BasePresenter;
import com.mvp4g.example.client.MailEventBus;
import com.mvp4g.example.client.bean.MailItem;
import com.mvp4g.example.client.ui.detail.IMailDetailView.IMailDetailPresenter;

/**
 * A composite for displaying the details of an email message.
 */
@Presenter(view = IMailDetailView.class)
public class MailDetailPresenter
    extends BasePresenter<IMailDetailView, MailEventBus>
    implements IMailDetailPresenter {

  public void onItemSelected(MailItem item) {
    view.setSubject(item.subject);
    view.setSender(item.sender);
    view.setRecipient("foo@example.com");
    view.setBody(item.body);
  }

  @Override
  public void bind() {
    eventBus.setDetailView(view.asWidget());
  }
}
