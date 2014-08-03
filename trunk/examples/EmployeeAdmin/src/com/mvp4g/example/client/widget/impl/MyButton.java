package com.mvp4g.example.client.widget.impl;

import com.google.gwt.user.client.ui.Button;
import com.mvp4g.example.client.widget.interfaces.IButton;

public class MyButton
    extends Button
    implements IButton {

  public MyButton() {
    //nothing to do
  }

  public MyButton(String text) {
    super(text);
  }

}
