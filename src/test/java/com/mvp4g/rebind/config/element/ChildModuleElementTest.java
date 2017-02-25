package com.mvp4g.rebind.config.element;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.javac.typemodel.TypeOracleStub;
import com.mvp4g.rebind.test_tools.annotation.events.EventBusOk;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ChildModuleElementTest
  extends SimpleMvp4gElementTest {

  private static final String[] properties = SimpleMvp4gElementTest.addProperties(new String[] { "eventToDisplayView",
                                                                                                 "async",
                                                                                                 "autoDisplay",
                                                                                                 "historyName",
                                                                                                 "parentModuleClass",
                                                                                                 "loader" });

  @Test
  public void testIsAsyncPath() {
    ChildModuleElement childModuleElement = new ChildModuleElement();
    assertTrue(childModuleElement.isAsync());
    childModuleElement.setAsync("true");
    assertTrue(childModuleElement.isAsync());

    childModuleElement = new ChildModuleElement();
    childModuleElement.setAsync("false");
    assertFalse(childModuleElement.isAsync());

    childModuleElement = new ChildModuleElement();
    childModuleElement.setAsync("123");
    assertFalse(childModuleElement.isAsync());
  }

  @Test
  public void testIsAutoLoadPath() {
    ChildModuleElement childModuleElement = new ChildModuleElement();
    assertTrue(childModuleElement.isAutoDisplay());
    childModuleElement.setAutoDisplay("true");
    assertTrue(childModuleElement.isAutoDisplay());

    childModuleElement = new ChildModuleElement();
    childModuleElement.setAutoDisplay("false");
    assertFalse(childModuleElement.isAutoDisplay());

    childModuleElement = new ChildModuleElement();
    childModuleElement.setAutoDisplay("123");
    assertFalse(childModuleElement.isAutoDisplay());
  }

  @Test
  public void testParentEventBus() {
    ChildModuleElement childModuleElement = new ChildModuleElement();
    assertNull(childModuleElement.getParentEventBus());

    JClassType parentEventBus = new TypeOracleStub().addClass(EventBusOk.class);
    childModuleElement.setParentEventBus(parentEventBus);

    assertSame(parentEventBus,
               childModuleElement.getParentEventBus());
  }

  @Override
  protected String[] getProperties() {
    return properties;
  }

  @Override
  protected String getTag() {
    return "childModule";
  }

  @Override
  protected SimpleMvp4gElement newElement() {
    return new ChildModuleElement();
  }

}
