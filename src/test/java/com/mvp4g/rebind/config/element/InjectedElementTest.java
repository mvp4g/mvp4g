package com.mvp4g.rebind.config.element;

import org.junit.Test;

import static org.junit.Assert.*;

public class InjectedElementTest {

  @Test
  public void test() {
    String          name   = "test";
    String          setter = "setTest";
    InjectedElement e      = new InjectedElement(name,
                                                 setter);
    assertEquals(name,
                 e.getElementName());
    assertEquals(setter,
                 e.getSetterName());
  }

}
