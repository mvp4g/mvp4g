package com.mvp4g.rebind.config.element;

public class EventFilterElementTest
  extends SimpleMvp4gElementTest {

  @Override
  protected String getTag() {
    return "eventFilter";
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new EventFilterElement();
  }

}
