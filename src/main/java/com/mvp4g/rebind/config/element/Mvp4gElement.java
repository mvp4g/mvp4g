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
package com.mvp4g.rebind.config.element;

import java.util.*;

/**
 * A representation of an Mvp4g configuration element.
 * <br>
 * <br>
 * This is the base class from which all Mvp4g configuration elements derive.
 *
 * @author javier
 */
public abstract class Mvp4gElement {

  private String tagName;
  private Properties properties = new Properties();

  //TODO: remove multiValueProperties and only use flexibleMultiValueProperties
  private Map<String, String[]> multiValueProperties = new HashMap<String, String[]>();

  private Map<String, List<String>> flexibleMultiValueProperties = new HashMap<String, List<String>>();

  public Mvp4gElement(String tagName) {
    this.tagName = tagName;
  }

  /**
   * Returns the name of the property that uniquely identifies this element.
   * <br>
   * The value associated with the property name returned by this method must be globally unique
   * across the entire mvp4g configuration.
   *
   * @return attribute name used as unique identifier.
   */
  public abstract String getUniqueIdentifierName();

  /**
   * Returns the value uniquely identifying this element in the configuration.
   *
   * @return if defined, a value uniquely identifying the element, if not defined, an empty
   * string.
   */
  public String getUniqueIdentifier() {
    String uid = properties.getProperty(getUniqueIdentifierName());
    return uid == null ? "" : uid;
  }

  public String getProperty(String name) {
    return properties.getProperty(name);
  }

  public void setProperty(String name,
                          String value) {
    if (name != null && value != null) {
      properties.setProperty(name,
                             value);
    }
  }

  public String getTagName() {
    return tagName;
  }

  public String[] getValues(String name) {
    return multiValueProperties.get(name);
  }

  public List<String> getFlexibleValues(String name) {
    List<String> values = flexibleMultiValueProperties.get(name);
    if (values == null) {
      setFlexibleValues(name,
                        new String[0]);
      values = flexibleMultiValueProperties.get(name);
    }
    return values;
  }

  public void setValues(String name,
                        String[] values) {

    if (name != null && values != null) {
      //if values is an array containing only ""
      if (values.length == 1 && ((values[0] == null) || (values[0].length() == 0))) {
        values = new String[] {};
      }

      multiValueProperties.put(name,
                               values);
    }
  }

  public void setFlexibleValues(String name,
                                String[] values) {
    if (name != null && values != null) {
      List<String> valuesList = new ArrayList<String>();
      for (String value : values) {
        valuesList.add(value);
      }

      flexibleMultiValueProperties.put(name,
                                       valuesList);
    }
  }


  public int totalProperties() {
    return properties.size();
  }

  public int totalMultiValues() {
    return multiValueProperties.size();
  }

  @Override
  public boolean equals(Object another) {
    if (this.getClass().isInstance(another)) {
      Mvp4gElement that = (Mvp4gElement) another;
      return this.getUniqueIdentifier().equals(that.getUniqueIdentifier());
    }
    return false;
  }

  @Override
  public int hashCode() {
    return getUniqueIdentifier().hashCode();
  }
}
