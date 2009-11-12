package com.mvp4g.util.config.loader.annotation;

import static junit.framework.Assert.*;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.util.config.element.InjectedElement;
import com.mvp4g.util.config.element.Mvp4gWithServicesElement;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.test_tools.annotation.Services;

public abstract class AbstractMvp4gAnnotationsWithServiceLoaderTest<A extends Annotation, L extends Mvp4gAnnotationsWithServiceLoader<A>> extends AbstractMvp4gAnnotationLoaderTest<A, L> {

	@Test( expected = Mvp4gAnnotationException.class )
	public void testNotPublicMethod() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( getClassNotPublic() );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Only public setter method can be used to inject a service." ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testWithNoParameter() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( getClassWithNoParameter() );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Only setter method with one parameter can be used to inject a service" ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testWithMoreThanOneParameter() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( getClassWithMoreThanOne() );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Only setter method with one parameter can be used to inject a service" ) );
			throw e;
		}
	}

	@Test( expected = Mvp4gAnnotationException.class )
	public void testNotAsyncParameter() throws Mvp4gAnnotationException {
		try {
			List<JClassType> annotedClasses = new ArrayList<JClassType>();
			JClassType type = oracle.addClass( getClassNotAsync() );
			annotedClasses.add( type );
			loader.load( annotedClasses, configuration );
		} catch ( Mvp4gAnnotationException e ) {
			assertTrue( e.getMessage().contains( "Only instance of Asynchron service class can be injected." ) );
			throw e;
		}
	}

	@Test
	public void testServicesWithName() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( getServiceWithName() );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		Set<Mvp4gWithServicesElement> elements = getSet();
		List<InjectedElement> injectedServices = elements.iterator().next().getInjectedServices();
		InjectedElement injectedService = injectedServices.get( 0 );
		assertEquals( injectedServices.size(), 1 );
		assertEquals( injectedService.getSetterName(), "setSthg" );
		assertEquals( injectedService.getElementName(), "name" );
		assertEquals( configuration.getServices().size(), 0 );
	}

	@Test
	public void testServices() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( getService() );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		Set<Mvp4gWithServicesElement> elements = getSet();

		String serviceClass = Services.SimpleService.class.getCanonicalName();
		String serviceName = serviceClass.replace( '.', '_' );
		
		List<InjectedElement> injectedServices = elements.iterator().next().getInjectedServices();
		InjectedElement injectedService = injectedServices.get( 0 );
		assertEquals( injectedServices.size(), 1 );
		assertEquals( injectedService.getSetterName(), "setSthg" );
		assertEquals( injectedService.getElementName(), serviceName );
		
		Set<ServiceElement> services = configuration.getServices();
		assertEquals( services.size(), 1 );
		ServiceElement service = services.iterator().next();
		assertEquals( service.getName(), serviceName );
		assertEquals( service.getClassName(), serviceClass );

		annotedClasses.clear();
		type = oracle.addClass( getSameService() );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		assertEquals( services.size(), 1 );
		
		ServiceElement service2 = services.iterator().next();
		assertSame( service, service2 );
	}

	abstract protected Class<?> getClassNotPublic();

	abstract protected Class<?> getClassWithNoParameter();

	abstract protected Class<?> getClassWithMoreThanOne();

	abstract protected Class<?> getClassNotAsync();

	abstract protected Class<?> getServiceWithName();

	abstract protected Class<?> getService();
	
	abstract protected Class<?> getSameService();

}
