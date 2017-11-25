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

package com.mvp4g.rebind.test_tools;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.SelectionProperty;
import com.google.gwt.core.ext.TreeLogger;
import com.mvp4g.client.DefaultMvp4gGinModule;
import com.mvp4g.rebind.test_tools.annotation.gin.OneGinModule;

public class PropertyOracleStub
  implements PropertyOracle {

  public static final String PROPERTY_OK             = "propertyOK";
  public static final String PROPERTY_OK2            = "propertyOK2";
  public static final String PROPERTY_NOT_GIN_MODULE = "propertyNotGinModule";

  private Map<String, SelectionProperty> properties = new HashMap<String, SelectionProperty>();

  public PropertyOracleStub() {
    properties.put(PROPERTY_OK,
                   new SelectionPropertyStub(PROPERTY_OK,
                                             DefaultMvp4gGinModule.class.getCanonicalName()
                                                                        .replace(".",
                                                                                 "$")));
    properties.put(PROPERTY_OK2,
                   new SelectionPropertyStub(PROPERTY_OK2,
                                             OneGinModule.class.getCanonicalName()
                                                               .replace(".",
                                                                        "$")));
    properties.put(PROPERTY_NOT_GIN_MODULE,
                   new SelectionPropertyStub(PROPERTY_NOT_GIN_MODULE,
                                             String.class.getCanonicalName()
                                                         .replace(".",
                                                                  "$")));
  }

  public ConfigurationProperty getConfigurationProperty(String propertyName)
    throws BadPropertyValueException {
    // nothing to do
    return null;
  }

  public SelectionProperty getSelectionProperty(TreeLogger logger,
                                                String propertyName)
    throws BadPropertyValueException {
    SelectionProperty property = properties.get(propertyName);
    if (property == null) {
      throw new BadPropertyValueException("error");
    }
    return properties.get(propertyName);
  }

  public String getPropertyValue(TreeLogger logger,
                                 String propertyName)
    throws BadPropertyValueException {
    // nothing to do
    return null;
  }

  public String[] getPropertyValueSet(TreeLogger logger,
                                      String propertyName)
    throws BadPropertyValueException {
    // TODO Auto-generated method stub
    return null;
  }

}
