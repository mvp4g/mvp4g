package com.mvp4g.processor.controls;

import java.util.Map;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic.Kind;

import com.mvp4g.processor.Messages;
import com.mvp4g.processor.ProcessorUtil;
import com.mvp4g.processor.bean.ApplicationInfo;
import com.mvp4g.processor.bean.ModuleInfo;

public class ParentEventControl {

	public void control( ProcessingEnvironment processingEnv, ExecutableElement e, TypeElement eventBus, boolean forwardToParent,
			ApplicationInfo appInfo ) {

		if ( forwardToParent ) {

			String module = appInfo.getEventBus().get( eventBus.getQualifiedName().toString() );
			Map<String, ModuleInfo> modules = appInfo.getModules();
			ModuleInfo info = ( module == null ) ? null : modules.get( module );
			if ( ( info == null ) || ( info.getParentEventBus() == null ) ) {
				processingEnv.getMessager().printMessage( Kind.ERROR, String.format( Messages.MODULE_NO_PARENT, eventBus.getQualifiedName() ), e );
			} else {
				TypeElement type = info.getParentEventBus();
				boolean found = false;
				String mName;
				String methodName = e.getSimpleName().toString();
				for ( ExecutableElement method : ElementFilter.methodsIn( processingEnv.getElementUtils().getAllMembers( type ) ) ) {
					mName = method.getSimpleName().toString();
					if ( mName.toString().equals( methodName ) ) {
						if ( method.getModifiers().contains( Modifier.PUBLIC ) ) {
							found = ProcessorUtil.sameParameters( e.getParameters(), method.getParameters(), e );
							if ( found ) {
								break;
							}
						}
					}
				}
				if ( !found ) {
					processingEnv.getMessager().printMessage(
							Kind.ERROR,
							String.format( Messages.MISSING_METHOD, type.getSimpleName(),
									ProcessorUtil.getMethodName( methodName, e.getParameters() ), e.getReturnType() ), e );
				}
			}
		}
	}

}
