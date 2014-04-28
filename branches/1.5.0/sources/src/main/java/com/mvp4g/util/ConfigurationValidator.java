/*
 * Copyright 2010-2014 Pierre-Laurent Coirier
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
package com.mvp4g.util;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.util.config.Mvp4gConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class validates ...
 *
 * @author Frank Hossfeld
 * @version 1.0.0
 */
public class ConfigurationValidator {

  public static ConfigurationValidator impl = new ConfigurationValidator();

  public boolean isValidate(TreeLogger logger,
                            GeneratorContext context,
                            JClassType module,
                            Mvp4gConfiguration configuration) {
    List<String> messages = new ArrayList<String>();

    Date start = new Date();

    logger.log(TreeLogger.INFO,
               "validation started...");

    validate(logger,
             context,
             module,
             messages);

    Date end = new Date();

    logger.log(TreeLogger.INFO,
               "validation completed: " + Long.toString(end.getTime() - start.getTime()) + " ms.");

    if (messages.size() > 0) {
      TreeLogger errorLogger = logger.branch(TreeLogger.INFO,
                                             "validation found " + Integer.toString(messages.size()) + " errors");
      for (int i = 0; i < messages.size(); i++) {
        errorLogger.log(TreeLogger.ERROR,
                        messages.get(i));
      }
    }

    logger.log(TreeLogger.INFO,
               "validation completed: " + Long.toString(end.getTime() - start.getTime()) + " ms.");

    return messages.size() == 0 ? true : false;
  }

//------------------------------------------------------------------------------

  private void validate(TreeLogger logger,
                        GeneratorContext context,
                        JClassType module,
                        List<String> messages) {

  }

//------------------------------------------------------------------------------

}
