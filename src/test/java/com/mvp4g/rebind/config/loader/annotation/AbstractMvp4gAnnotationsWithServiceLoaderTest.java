package com.mvp4g.rebind.config.loader.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.rebind.config.element.InjectedElement;
import com.mvp4g.rebind.config.element.Mvp4gWithServicesElement;
import com.mvp4g.rebind.config.element.ServiceElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.rebind.test_tools.annotation.services.SimpleService;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public abstract class AbstractMvp4gAnnotationsWithServiceLoaderTest<A extends Annotation, L extends Mvp4gAnnotationsWithServiceLoader<A>>
  extends AbstractMvp4gAnnotationLoaderTest<A, L> {

  @Test(expected = Mvp4gAnnotationException.class)
  public void testNotPublicMethod()
    throws Mvp4gAnnotationException {
    try {
      List<JClassType> annotedClasses = new ArrayList<JClassType>();
      JClassType       type           = oracle.addClass(getClassNotPublic());
      annotedClasses.add(type);
      loader.load(annotedClasses,
                  configuration);
    } catch (Mvp4gAnnotationException e) {
      assertTrue(e.getMessage()
                  .contains("Only public setter method can be used to inject a service."));
      throw e;
    }
  }

  abstract protected Class<?> getClassNotPublic();

  @Test(expected = Mvp4gAnnotationException.class)
  public void testWithNoParameter()
    throws Mvp4gAnnotationException {
    try {
      List<JClassType> annotedClasses = new ArrayList<JClassType>();
      JClassType       type           = oracle.addClass(getClassWithNoParameter());
      annotedClasses.add(type);
      loader.load(annotedClasses,
                  configuration);
    } catch (Mvp4gAnnotationException e) {
      assertTrue(e.getMessage()
                  .contains("Only setter method with one parameter can be used to inject a service"));
      throw e;
    }
  }

  abstract protected Class<?> getClassWithNoParameter();

  @Test(expected = Mvp4gAnnotationException.class)
  public void testWithMoreThanOneParameter()
    throws Mvp4gAnnotationException {
    try {
      List<JClassType> annotedClasses = new ArrayList<JClassType>();
      JClassType       type           = oracle.addClass(getClassWithMoreThanOne());
      annotedClasses.add(type);
      loader.load(annotedClasses,
                  configuration);
    } catch (Mvp4gAnnotationException e) {
      assertTrue(e.getMessage()
                  .contains("Only setter method with one parameter can be used to inject a service"));
      throw e;
    }
  }

  abstract protected Class<?> getClassWithMoreThanOne();

  @Test
  public void testServicesWithName()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    JClassType       type           = oracle.addClass(getServiceWithName());
    annotedClasses.add(type);
    loader.load(annotedClasses,
                configuration);
    Set<Mvp4gWithServicesElement> elements         = getSet();
    List<InjectedElement>         injectedServices = elements.iterator()
                                                             .next()
                                                             .getInjectedServices();
    InjectedElement               injectedService  = injectedServices.get(0);
    assertEquals(injectedServices.size(),
                 1);
    assertEquals(injectedService.getSetterName(),
                 "setSthg");
    assertEquals(injectedService.getElementName(),
                 "name");
    assertEquals(configuration.getServices()
                              .size(),
                 0);
  }

  abstract protected Class<?> getServiceWithName();

  @Test
  public void testServices()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    JClassType       type           = oracle.addClass(getService());
    annotedClasses.add(type);
    loader.load(annotedClasses,
                configuration);
    Set<Mvp4gWithServicesElement> elements = getSet();

    String serviceClass = SimpleService.class.getCanonicalName();
    String serviceName  = serviceClass.replace('.',
                                               '_');

    List<InjectedElement> injectedServices = elements.iterator()
                                                     .next()
                                                     .getInjectedServices();
    InjectedElement       injectedService  = injectedServices.get(0);
    assertEquals(injectedServices.size(),
                 1);
    assertEquals(injectedService.getSetterName(),
                 "setSthg");
    assertEquals(injectedService.getElementName(),
                 serviceName + "Async");

    Set<ServiceElement> services = configuration.getServices();
    assertEquals(services.size(),
                 1);
    ServiceElement service = services.iterator()
                                     .next();
    assertEquals(service.getName(),
                 serviceName + "Async");
    assertEquals(service.getClassName(),
                 serviceClass);

    annotedClasses.clear();
    type = oracle.addClass(getSameService());
    annotedClasses.add(type);
    loader.load(annotedClasses,
                configuration);
    assertEquals(services.size(),
                 1);

    ServiceElement service2 = services.iterator()
                                      .next();
    assertSame(service,
               service2);
  }

  abstract protected Class<?> getService();

  abstract protected Class<?> getSameService();

  abstract protected Class<?> getClassNotAsync();

}
