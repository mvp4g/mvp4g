/*
 * Copyright (c) 2009 - 2017 - Pierre-Laurent Coirer, Frank Hossfeld
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.mvp4g.rebind.config.loader.annotation;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.dev.javac.typemodel.TypeOracleStub;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.SimpleMvp4gElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.rebind.test_tools.GeneratorContextStub;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public abstract class AbstractMvp4gAnnotationLoaderTest<A extends Annotation, L extends Mvp4gAnnotationsLoader<A>> {

  protected Mvp4gConfiguration configuration = null;
  protected TypeOracleStub     oracle        = null;
  protected L                  loader        = null;

  @Before
  public void setUp() {
    GeneratorContextStub context = new GeneratorContextStub();
    oracle = context.getTypeOracleStub();
    configuration = new Mvp4gConfiguration(null,
                                           context);
    loader = createLoader();
  }

  abstract protected L createLoader();

  @Test(expected = Mvp4gAnnotationException.class)
  public void testSameElementTwice()
    throws Mvp4gAnnotationException {
    try {
      List<JClassType> annotedClasses = new ArrayList<JClassType>();
      JClassType       type           = oracle.addClass(getSimpleClass());
      annotedClasses.add(type);
      annotedClasses.add(type);
      loader.load(annotedClasses,
                  configuration);
    } catch (Mvp4gAnnotationException e) {
      assertTrue(e.getMessage()
                  .contains("Duplicate"));
      throw e;
    }
  }

  abstract protected Class<?> getSimpleClass();

  @Test
  public void testSimpleElement()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    annotedClasses.add(oracle.addClass(getSimpleClass()));
    Set<SimpleMvp4gElement> elements = getSet();
    assertEquals(elements.size(),
                 0);
    loader.load(annotedClasses,
                configuration);
    assertEquals(elements.size(),
                 1);
    SimpleMvp4gElement element = elements.iterator()
                                         .next();
    assertEquals(element.getName(),
                 element.getClassName()
                        .replace('.',
                                 '_'));
  }

  abstract protected <T extends SimpleMvp4gElement> Set<T> getSet();

  @Test
  public void testElementWithName()
    throws Mvp4gAnnotationException {
    List<JClassType> annotedClasses = new ArrayList<JClassType>();
    annotedClasses.add(oracle.addClass(getWithNameClass()));
    Set<SimpleMvp4gElement> elements = getSet();
    assertEquals(elements.size(),
                 0);
    loader.load(annotedClasses,
                configuration);
    assertEquals(elements.size(),
                 1);
    SimpleMvp4gElement element = elements.iterator()
                                         .next();
    assertEquals(element.getName(),
                 "name");
  }

  abstract protected Class<?> getWithNameClass();

  @Test
  public void testWrongInterface() {
    Class<?> wrongInterface = getWrongInterface();
    if (wrongInterface != null) {
      List<JClassType> annotedClasses = new ArrayList<JClassType>();
      annotedClasses.add(oracle.addClass(wrongInterface));
      Set<SimpleMvp4gElement> elements = getSet();
      assertEquals(elements.size(),
                   0);
      try {
        loader.load(annotedClasses,
                    configuration);
        fail();
      } catch (Mvp4gAnnotationException e) {
        assertTrue(e.getMessage()
                    .contains("this class must implement "));
      }
    }
  }

  abstract protected Class<?> getWrongInterface();

}
