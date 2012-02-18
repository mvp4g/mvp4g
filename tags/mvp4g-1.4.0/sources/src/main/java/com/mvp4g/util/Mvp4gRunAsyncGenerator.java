/*
 * Copyright 2010 Pierre-Laurent Coirier
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

import java.io.PrintWriter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.RunAsyncCallback;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Class uses to create the implementation class of Mvp4gStarter
 * 
 * @author plcoirier
 * 
 */
public class Mvp4gRunAsyncGenerator extends Generator {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.gwt.core.ext.Generator#generate(com.google.gwt.core.ext.TreeLogger,
	 * com.google.gwt.core.ext.GeneratorContext, java.lang.String)
	 */
	@Override
	public String generate( TreeLogger logger, GeneratorContext context, String typeName ) throws UnableToCompleteException {
		TypeOracle typeOracle = context.getTypeOracle();

		JClassType originalType = typeOracle.findType( typeName );
		if ( originalType == null ) {
			logger.log( TreeLogger.ERROR, "Unable to find metadata for type '" + typeName + "'", null );
			throw new UnableToCompleteException();
		}

		String packageName = originalType.getPackage().getName();
		String originalClassName = originalType.getSimpleSourceName();
		String generatedClassName = originalClassName + "Impl";

		logger.log( TreeLogger.INFO, "Generating writer for " + packageName + "." + generatedClassName, null );

		PrintWriter printWriter = context.tryCreate( logger, packageName, generatedClassName );

		ClassSourceFileComposerFactory classFactory = new ClassSourceFileComposerFactory( packageName, generatedClassName );

		classFactory.addImplementedInterface( originalType.getName() );
		String[] classesToImport = getClassesToImport();
		for ( String classToImport : classesToImport ) {
			classFactory.addImport( classToImport );
		}

		String generatedClassQualifiedName = packageName + "." + generatedClassName;
		if ( printWriter != null ) {
			SourceWriter sourceWriter = classFactory.createSourceWriter( context, printWriter );
			logger.log( TreeLogger.INFO, "Generating source for " + generatedClassQualifiedName + " ", null );
			writeClass( sourceWriter, getRunAsync( originalType ) );
			sourceWriter.commit( logger );
		}

		return generatedClassQualifiedName;
	}

	void writeClass( SourceWriter sourceWriter, String callBackType) {
		sourceWriter.print( "public void load(" );
		sourceWriter.print( callBackType );
		sourceWriter.println( " callback) {" );
		sourceWriter.indent();
		sourceWriter.println( "GWT.runAsync(callback);" );
		sourceWriter.outdent();
		sourceWriter.println( "}" );
	}

	String[] getClassesToImport() {
		return new String[] { GWT.class.getName(), RunAsyncCallback.class.getName() };
	}

	String getRunAsync( JClassType originalType ) {
		JMethod[] methods = originalType.getOverridableMethods();
		for ( JMethod method : methods ) {
			if ( "load".equals( method.getName() ) ) {
				return method.getParameters()[0].getType().getQualifiedSourceName();
			}
		}
		return null;
	}
}
