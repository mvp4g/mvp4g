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

package com.mvp4g.client.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation allows to define an history converter for the framework.<br>
 * <br>
 * You can define the name of the history converter thanks to the optional attribute <i>name</i>. If
 * you don't give a name, the framework will generate one.<br>
 * It is recommended to affect a name only if needed.<br>
 * <br>
 * This annotation also has a convertParams attribute. By default, an history converter must define
 * an handling method for each event it converts. If you set this attribute to SIMPLE, you will have
 * to define one convertToToken method for each different parameter signature. If you set this
 * attribute to NONE, you won't have to define any conversion method.<br>
 * <br>
 * This annotation can be used only on classes that implements <code>HistoryConverter</code>.
 *
 * @author plcoirier
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface History {

  String name() default "";

  HistoryConverterType type() default HistoryConverterType.DEFAULT;

  public enum HistoryConverterType {
    DEFAULT,
    SIMPLE,
    NONE
  }

}
