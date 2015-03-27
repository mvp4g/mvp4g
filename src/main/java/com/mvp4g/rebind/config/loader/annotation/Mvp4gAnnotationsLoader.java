/*
 * Copyright 2009 Pierre-Laurent Coirier
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

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.mvp4g.rebind.config.Mvp4gConfiguration;
import com.mvp4g.rebind.config.element.Mvp4gElement;
import com.mvp4g.rebind.config.element.SimpleMvp4gElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Set;

/**
 * Base class responsible for loading information contained in annotations. All annotation loader
 * must expand this class.
 *
 * @author plcoirier
 */
public abstract class Mvp4gAnnotationsLoader<T extends Annotation> {

  /**
   * Load information containing in annotations for each class.
   *
   * @param annotedClasses list of class that are annoted with the annotation
   * @param configuration  configuration containing loaded elements of the application
   * @throws Mvp4gAnnotationException if annotation is not used correctly
   */
  @SuppressWarnings("unchecked")
  public void load(List<JClassType> annotedClasses,
                   Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException {
    JClassType mandatoryInterface = configuration.getOracle().findType(getMandatoryInterfaceName());
    for (JClassType c : annotedClasses) {
      controlType(c,
                  mandatoryInterface);
      loadElement(c,
                  c.getAnnotation((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]),
                  configuration);
    }
  }

  /**
   * Add an element to the set and verify first if there is a duplicate.
   *
   * @param <E>            type of the element
   * @param loadedElements set where to add the element
   * @param element        element to add
   * @param c              annoted class that asks for this element to be added (can be null, only for error
   *                       information purpose)
   * @param m              annoted method that ask for this element to be added (can be null, only for error
   *                       information purpose)
   * @throws Mvp4gAnnotationException if a duplicate element is already in the list
   */
  protected <E extends Mvp4gElement> void addElement(Set<E> loadedElements,
                                                     E element,
                                                     JClassType c,
                                                     JMethod m)
    throws Mvp4gAnnotationException {
    checkForDuplicates(loadedElements,
                       element,
                       c,
                       m);
    loadedElements.add(element);
  }

  /**
   * Build a name for the element thanks to its class in case the current one is empty or null.
   *
   * @param currentName current name of the element
   * @param className   class name of the element
   * @param suffix      suffix to add to the element is needed
   * @return new name of the element if the current name was null or empty, current name otherwise
   */
  protected String buildElementNameIfNeeded(String currentName,
                                            String className,
                                            String suffix) {
    if ((currentName == null) || (currentName.length() == 0)) {
      return buildElementName(className,
                              suffix);
    } else {
      return currentName;
    }
  }

  /**
   * Build a name for the element thanks to its class by replacing '.' by '_'. Also add a suffix
   * to the name.
   *
   * @param className class name of the element
   * @param suffix    suffix to add at the end of his name
   * @return name of the element
   */
  protected String buildElementName(String className,
                                    String suffix) {
    return className.replace('.',
                             '_') + suffix;
  }

  /**
   * Find the first element with the corresponding class name and return its name.
   *
   * @param <E>              type of the elements
   * @param loadedElements   set of elements to look for
   * @param elementClassName name of the class of the seached element
   * @return name of the element found, null otherwise
   */
  protected <E extends SimpleMvp4gElement> String getElementName(Set<E> loadedElements,
                                                                 String elementClassName) {
    String elementName = null;
    for (E element : loadedElements) {
      if (elementClassName.equals(element.getClassName())) {
        elementName = element.getName();
        break;
      }
    }
    return elementName;
  }

  /**
   * Find the first element with the corresponding class name.
   *
   * @param <E>              type of the elements
   * @param loadedElements   set of elements to look for
   * @param elementClassName name of the class of the seached element
   * @return name of the element found, null otherwise
   */
  protected <E extends SimpleMvp4gElement> E getElement(Set<E> loadedElements,
                                                        String elementClassName) {
    for (E element : loadedElements) {
      if (elementClassName.equals(element.getClassName())) {
        return element;
      }
    }
    return null;
  }

  /**
   * Control if the class can accept the annotation.
   *
   * @param c                  class to control
   * @param mandatoryInterface interface that the class must implement
   * @throws Mvp4gAnnotationException if the class don't implement the interface
   */
  @SuppressWarnings("unchecked")
  protected void controlType(JClassType c,
                             JClassType mandatoryInterface)
    throws Mvp4gAnnotationException {
    if (! c.isAssignableTo(mandatoryInterface)) {
      String annotationClassName = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0])
        .getCanonicalName();
      throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                         null,
                                         "this class must implement "
                                           + mandatoryInterface.getQualifiedSourceName() + " since it is annoted with " + annotationClassName + ".");
    }
  }

  /**
   * Check if an element is already present in a set
   *
   * @param <E>            type of the elements
   * @param loadedElements set of elements where the check is done
   * @param element        searched element
   * @param c              annoted class that asks for this element to be added (can be null, only for error
   *                       information purpose)
   * @param m              annoted method that ask for this element to be added (can be null, only for error
   *                       information purpose)
   * @throws Mvp4gAnnotationException if the element is already in the set
   */
  private <E extends Mvp4gElement> void checkForDuplicates(Set<E> loadedElements,
                                                           E element,
                                                           JClassType c,
                                                           JMethod m)
    throws Mvp4gAnnotationException {
    if (loadedElements.contains(element)) {
      String err = "Duplicate " + element.getTagName() + " identified by " + "'" + element.getUniqueIdentifierName()
        + "' found in configuration file.";
      throw new Mvp4gAnnotationException(c.getQualifiedSourceName(),
                                         (m == null) ? null : m.getName(),
                                         err);
    }
  }

  /**
   * Load one class annoted with the annotation
   *
   * @param c             class annoted with the annotation
   * @param annotation    annotation of the class
   * @param configuration configuration containing loaded elements of the application
   * @throws Mvp4gAnnotationException if annotation is not used correctly
   */
  abstract protected void loadElement(JClassType c,
                                      T annotation,
                                      Mvp4gConfiguration configuration)
    throws Mvp4gAnnotationException;

  /**
   * @return name of the interface that class that has the specific annotation must implement
   */
  abstract protected String getMandatoryInterfaceName();

}
