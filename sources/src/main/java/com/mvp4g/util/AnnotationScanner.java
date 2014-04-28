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
package com.mvp4g.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.TypeOracle;

public class AnnotationScanner {

	public static Map<Class<? extends Annotation>, List<JClassType>> scan(TreeLogger logger,
                                                                        TypeOracle typeOracle,
			                                                                  Class<? extends Annotation>[] annotationClasses ) {

		Map<Class<? extends Annotation>, List<JClassType>> annotationMap = new HashMap<Class<? extends Annotation>, List<JClassType>>();
		for ( Class<? extends Annotation> c : annotationClasses ) {
			annotationMap.put( c, new ArrayList<JClassType>() );
		}

		int nbClasses = 0;

		Date start = new Date();

		for ( JPackage pack : typeOracle.getPackages() ) {

			for ( JClassType type : pack.getTypes() ) {
				for ( Class<? extends Annotation> c : annotationClasses ) {
					if ( type.getAnnotation( c ) != null ) {
						annotationMap.get( c ).add( type );
					}

				}
			}

			nbClasses += pack.getTypes().length;

		}

		Date end = new Date();

		logger.log( TreeLogger.INFO, nbClasses + " classes scanned in " + Long.toString( end.getTime() - start.getTime() ) + " ms." );

		return annotationMap;

	}

}
