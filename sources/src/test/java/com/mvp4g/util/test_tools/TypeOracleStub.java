package com.mvp4g.util.test_tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JRealClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.mvp4g.util.test_tools.annotation.Presenters;

public class TypeOracleStub extends TypeOracle {

	@Override
	public JClassType findType( String name ) {
		JClassType type = super.findType( name );

		if ( type == null ) {
			try {
				type = addClass( Class.forName( name ) );
			} catch ( ClassNotFoundException e ) {
				e.printStackTrace();
			}
		}

		return type;
	}

	public JRealClassType addClass( Class<?> c ) {
		JPackage p = getOrCreatePackage( c.getPackage().getName() );
		Class<?> enclosingClass = c.getEnclosingClass();
		JClassType enclosingType = null;
		if ( enclosingClass != null ) {
			enclosingType = findType( enclosingClass.getName() );
			if ( enclosingType == null ) {
				addClass( enclosingClass );
			}
		}

		JRealClassType type = new JRealClassType( this, p, enclosingType, c.isLocalClass(), c.getSimpleName(), c.isInterface() );

		Class<?> superClass = c.getSuperclass();
		if ( superClass != null ) {
			type.setSuperclass( findType( superClass.getName() ) );
		}

		List<JClassType> implementedInterfaces = getImplementedInterfaces( c );

		for ( JClassType implementedInterface : implementedInterfaces ) {
			type.addImplementedInterface( implementedInterface );
		}

		Map<Class<? extends Annotation>, Annotation> annotations = new HashMap<Class<? extends Annotation>, Annotation>();
		for ( Annotation a : c.getAnnotations() ) {
			annotations.put( a.annotationType(), a );
		}
		type.addAnnotations( annotations );

		if ( c.getPackage().getName().contains( Presenters.class.getPackage().getName() ) ) {
			JMethod method = null;
			for ( Method m : c.getDeclaredMethods() ) {
				annotations = new HashMap<Class<? extends Annotation>, Annotation>();
				for ( Annotation a : m.getAnnotations() ) {
					annotations.put( a.annotationType(), a );
				}

				method = new JMethod( type, m.getName(), annotations, null );
				if ( m.getModifiers() == Modifier.PUBLIC ) {
					method.addModifierBits( 0x00000020 );
				} else {
					method.addModifierBits( 0x00000010 );
				}

				for ( Class<?> param : m.getParameterTypes() ) {
					new JParameter( method, findType( param.getName() ), param.getSimpleName() );
				}

			}
		}

		return type;
	}

	private List<JClassType> getImplementedInterfaces( Class<?> c ) {
		List<JClassType> interfaces = new ArrayList<JClassType>();
		for ( Class<?> implementedInterface : c.getInterfaces() ) {
			interfaces.add( findType( implementedInterface.getName() ) );
		}

		Class<?> superClass = c.getSuperclass();
		if ( superClass != null ) {
			interfaces.addAll( getImplementedInterfaces( superClass ) );
		}

		return interfaces;
	}

}
