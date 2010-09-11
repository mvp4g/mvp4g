package com.mvp4g.util.test_tools;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JGenericType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JPackage;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.event.EventFilter;
import com.mvp4g.client.event.EventHandlerInterface;
import com.mvp4g.client.history.HistoryConverter;
import com.mvp4g.client.presenter.PresenterInterface;

public class TypeOracleStub extends TypeOracle {

	private boolean isGWT2 = true;

	@Override
	public JClassType findType( String name ) {

		JClassType type;
		if ( "com.google.gwt.core.client.RunAsyncCallback".equals( name ) ) {
			// if GWT2, return any class as long as type is not null
			type = ( isGWT2 ) ? findType( Object.class.getName() ) : null;
		} else {

			type = super.findType( name );

			if ( type == null ) {
				try {
					type = addClass( Class.forName( name ) );
				} catch ( ClassNotFoundException e ) {
					e.printStackTrace();
				}
			}
		}

		return type;
	}

	public JGenericType addClass( Class<?> c ) {
		JGenericType type = null;
		if ( !c.isArray() ) {
			JPackage p = getOrCreatePackage( c.getPackage().getName() );
			Class<?> enclosingClass = c.getEnclosingClass();
			JClassType enclosingType = null;
			if ( enclosingClass != null ) {
				enclosingType = findType( enclosingClass.getName() );
				if ( enclosingType == null ) {
					addClass( enclosingClass );
				}
			}

			type = new JGenericType( this, p, enclosingType, c.isLocalClass(), c.getSimpleName(), c.isInterface(), new JTypeParameter[0] );

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

			if ( c.getPackage().getName().contains( getClass().getPackage().getName() ) ) {
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
		} else {
			type = (JGenericType)findType( Object.class.getCanonicalName() );
		}

		return type;
	}

	private List<JClassType> getImplementedInterfaces( Class<?> c ) {
		List<JClassType> interfaces = new ArrayList<JClassType>();
		for ( Class<?> implementedInterface : c.getInterfaces() ) {
			interfaces.add( new MyParameterizedType( (JGenericType)findType( implementedInterface.getName() ), null, new JClassType[0] ) );
		}

		Class<?> superClass = c.getSuperclass();
		if ( superClass != null ) {
			interfaces.addAll( getImplementedInterfaces( superClass ) );
		}

		return interfaces;
	}

	private class MyParameterizedType extends JParameterizedType {

		public MyParameterizedType( JGenericType baseType, JClassType enclosingType, JClassType[] typeArgs ) {
			super( baseType, enclosingType, typeArgs );
		}

		@Override
		public JMethod findMethod( String name, JType[] paramTypes ) {
			JMethod method = super.findMethod( name, paramTypes );

			if ( method == null ) {
				if ( getQualifiedSourceName().equals( EventHandlerInterface.class.getName() ) ) {
					method = new JMethod( this.getBaseType(), name );
					method.setReturnType( findType( EventBusWithLookup.class.getName() ) );
				} else if ( getQualifiedSourceName().equals( PresenterInterface.class.getName() ) ) {
					method = new JMethod( this.getBaseType(), name );
					method.setReturnType( findType( String.class.getName() ) );
				}				
			}

			return method;
		}

		@Override
		public JMethod[] getMethods() {
			JMethod[] methods = null;
			if ( getQualifiedSourceName().equals( HistoryConverter.class.getName() ) ) {
				JMethod method = new JMethod( this.getBaseType(), "convertFromToken" );
				new JParameter( method, findType( String.class.getName() ), "eventType" );
				new JParameter( method, findType( String.class.getName() ), "form" );
				new JParameter( method, findType( EventBusWithLookup.class.getName() ), "eventBus" );
				methods = new JMethod[] { method, method };
			}else if ( getQualifiedSourceName().equals( EventFilter.class.getName() ) ) {
				JMethod method = new JMethod( this.getBaseType(), "filterEvent" );
				new JParameter( method, findType( String.class.getName() ), "eventType" );
				new JParameter( method, findType( String.class.getName() ), "form" );
				new JParameter( method, findType( EventBusWithLookup.class.getName() ), "eventBus" );
				methods = new JMethod[] { method, method };
			} else {
				methods = super.getMethods();
			}

			return methods;
		}

	}

	/**
	 * @param isGWT2
	 *            the isGWT2 to set
	 */
	public void setGWT2( boolean isGWT2 ) {
		this.isGWT2 = isGWT2;
	}

}
