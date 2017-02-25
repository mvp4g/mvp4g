package com.mvp4g.rebind.config.element;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ViewElementTest
  extends SimpleMvp4gElementTest {

  @Test
  public void testInstantiateAtStartGetterSetter() {
    ViewElement element = new ViewElement();
    assertFalse(element.isInstantiateAtStart());
    element.setInstantiateAtStart(true);
    assertTrue(element.isInstantiateAtStart());
    element.setInstantiateAtStart(false);
    assertFalse(element.isInstantiateAtStart());
  }

  @Override
  protected String getTag() {
    return "view";
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new ViewElement();
  }

}
