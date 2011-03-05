package com.mvp4g.util.config.loader.annotation;

import static junit.framework.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Service;
import com.mvp4g.util.config.element.ServiceElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.test_tools.annotation.Services;
import com.mvp4g.util.test_tools.annotation.services.ServiceWithName;
import com.mvp4g.util.test_tools.annotation.services.SimpleService;

public class ServicesAnnotationsLoaderTest extends AbstractMvp4gAnnotationLoaderTest<Service, ServiceAnnotationsLoader> {

	@Test
	public void testPath() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( getSimpleClass() ) );
		annotedClasses.add( oracle.addClass( getWithNameClass() ) );
		Set<ServiceElement> services = getSet();
		assertEquals( services.size(), 0 );
		loader.load( annotedClasses, configuration );
		assertEquals( services.size(), 2 );
		for ( ServiceElement service : services ) {
			assertEquals( service.getPath(), "path" );
		}
	}

	@Test
	public void testGeneratedClass() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		annotedClasses.add( oracle.addClass( Services.ServiceWithGeneratedClass.class ) );
		Set<ServiceElement> services = getSet();
		assertEquals( services.size(), 0 );
		loader.load( annotedClasses, configuration );
		assertEquals( services.size(), 1 );
		assertEquals( services.iterator().next().getGeneratedClassName(), Services.ServiceWithGeneratedClass.class.getCanonicalName() );

	}

	@Override
	protected ServiceAnnotationsLoader createLoader() {
		return new ServiceAnnotationsLoader();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected Set<ServiceElement> getSet() {
		return configuration.getServices();
	}

	@Override
	protected Class<?> getSimpleClass() {
		return SimpleService.class;
	}

	@Override
	protected Class<?> getWithNameClass() {
		return ServiceWithName.class;
	}

	@Override
	protected Class<?> getWrongInterface() {
		return null;
	}

}
