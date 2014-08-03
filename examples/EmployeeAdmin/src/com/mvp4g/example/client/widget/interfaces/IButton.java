package com.mvp4g.example.client.widget.interfaces;

import com.google.gwt.event.dom.client.HasClickHandlers;

public interface IButton
    extends HasClickHandlers {

  public void setEnabled(boolean enabled);

  public void setVisible(boolean isVisible);

  public void setText(String text);
}
