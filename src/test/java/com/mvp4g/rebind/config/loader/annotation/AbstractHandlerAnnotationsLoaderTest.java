package com.mvp4g.rebind.config.loader.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.SingleSplitter;
import com.mvp4g.rebind.config.element.EventHandlerElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public abstract class AbstractHandlerAnnotationsLoaderTest<A extends Annotation, E extends EventHandlerElement, L extends AbstractHandlerAnnotationsLoader<A, E>>
  extends AbstractMvp4gAnnotationsWithServiceLoaderTest<A, L> {

  @Test
  public void testNotMultiple()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    JClassType       type           = oracle.addClass(getSimpleClass());
    annotedClasses.add(type);
    loader.load(annotedClasses,
                configuration);
    E element = loader.getConfigList(configuration)
                      .iterator()
                      .next();
    assertFalse(element.isMultiple());
  }

  @Test
  public void testMultiple()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    JClassType       type           = oracle.addClass(getMultipleClass());
    annotedClasses.add(type);
    loader.load(annotedClasses,
                configuration);
    E element = loader.getConfigList(configuration)
                      .iterator()
                      .next();
    assertTrue(element.isMultiple());
  }

  abstract protected Class<?> getMultipleClass();

  @Test
  public void testNotAsync()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    JClassType       type           = oracle.addClass(getSimpleClass());
    annotedClasses.add(type);
    loader.load(annotedClasses,
                configuration);
    E element = loader.getConfigList(configuration)
                      .iterator()
                      .next();
    assertFalse(element.isAsync());
  }

  @Test
  public void testAsync()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    JClassType       type           = oracle.addClass(getAsyncClass());
    annotedClasses.add(type);
    loader.load(annotedClasses,
                configuration);
    E element = loader.getConfigList(configuration)
                      .iterator()
                      .next();
    assertTrue(element.isAsync());
    assertEquals(SingleSplitter.class.getCanonicalName(),
                 element.getAsync());
  }

  abstract protected Class<?> getAsyncClass();

}
