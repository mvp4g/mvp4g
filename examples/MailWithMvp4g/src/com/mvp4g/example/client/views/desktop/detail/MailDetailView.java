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
package com.mvp4g.example.client.views.desktop.detail;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Singleton;
import com.mvp4g.example.client.ui.detail.IMailDetailView;
import com.mvp4g.example.client.ui.detail.IMailDetailView.IMailDetailPresenter;
import com.mvp4g.example.client.widgets.ReverseComposite;

/**
 * A composite for displaying the details of an email message.
 */
@Singleton
public class MailDetailView
    extends ReverseComposite<IMailDetailPresenter>
    implements IMailDetailView {

  private static final Binder binder = GWT.create(Binder.class);

  @UiField
  Element subject;
  @UiField
  Element sender;
  @UiField
  Element recipient;
  @UiField
  HTML    body;

  public MailDetailView() {
    initWidget(binder.createAndBindUi(this));
  }

  public void setSubject(String s) {
    subject.setInnerText(s);
  }

  public void setSender(String s) {
    sender.setInnerText(s);

  }

  public void setRecipient(String r) {
    recipient.setInnerHTML(r);

  }

  public void setBody(String b) {
    // WARNING: For the purposes of this demo, we're using HTML directly, on
    // the assumption that the "server" would have appropriately scrubbed
    // the
    // HTML. Failure to do so would open your application to XSS attacks.
    body.setHTML(b);

  }

  interface Binder
      extends UiBinder<Widget, MailDetailView> {
  }
}
