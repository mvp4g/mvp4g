package com.mvp4g.client.event;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DefaultMvp4gLoggerTest {

  private DefaultMvp4gLogger logger;

  @Before
  public void setUp() {
    logger = new DefaultMvp4gLogger();
  }

  @Test
  public void testLog() {
    String test = "test";
    assertEquals(logger.createLog(test,
                                  0),
                 test);
    assertEquals(logger.createLog(test,
                                  1),
                 DefaultMvp4gLogger.INDENT + test);

    String message = logger.createLog("",
                                      5);
    assertTrue(message.length() == (DefaultMvp4gLogger.INDENT.length() * 5));
    assertTrue(message.trim()
                      .length() == 0);
  }

}
