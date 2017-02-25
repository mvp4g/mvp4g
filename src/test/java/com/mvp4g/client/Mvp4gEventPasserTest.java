package com.mvp4g.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Mvp4gEventPasserTest {

  @Test
  public void testConstructor() {
    String test = "TEST";
    Mvp4gEventPasser passer = new Mvp4gEventPasser(test) {

      @Override
      public void pass(Mvp4gModule module) {
        // TODO Auto-generated method stub

      }

    };
    assertEquals(test,
                 passer.eventObjects[0]);
    assertEquals(test,
                 passer.getEventObjects()[0]);
  }

  @Test
  public void testEventObjectsSetter() {
    String test = "TEST";
    Mvp4gEventPasser passer = new Mvp4gEventPasser() {

      @Override
      public void pass(Mvp4gModule module) {
        // TODO Auto-generated method stub

      }

    };
    assertTrue(passer.eventObjects.length == 0);
    assertTrue(passer.getEventObjects().length == 0);
    passer.setEventObject(test);
    assertEquals(test,
                 passer.eventObjects[0]);
    assertEquals(test,
                 passer.getEventObjects()[0]);
  }

}
