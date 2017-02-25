package com.mvp4g.client.test_tools;

import com.google.gwt.event.logical.shared.ValueChangeEvent;

public class ValueChangeEventStub<T>
  extends ValueChangeEvent<T> {

  public ValueChangeEventStub(T value) {
    super(value);
  }
}
