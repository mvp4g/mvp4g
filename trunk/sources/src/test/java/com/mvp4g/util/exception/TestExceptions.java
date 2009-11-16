package com.mvp4g.util.exception;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.mvp4g.client.event.EventBus;
import com.mvp4g.client.event.EventBusWithLookup;
import com.mvp4g.client.presenter.PresenterInterface;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.exception.element.DuplicatePropertyNameException;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.exception.loader.Mvp4gXmlException;

public class TestExceptions {

	@Test
	public void testInvalidClassException() throws DuplicatePropertyNameException {

		PresenterElement p = new PresenterElement();
		p.setName( "name" );
		p.setClassName( "com.test.Presenter" );

		InvalidClassException e = new InvalidClassException( p, PresenterInterface.class.getName() );

		assertEquals( "presenter name: This class must extend " + PresenterInterface.class.getName(), e.getMessage() );
	}

	@Test
	public void testInvalidMvp4gConfigurationException() {
		InvalidMvp4gConfigurationException e = new InvalidMvp4gConfigurationException( "test" );
		assertEquals( "test", e.getMessage() );
	}

	@Test
	public void testInvalidTypeException() throws DuplicatePropertyNameException {

		PresenterElement p = new PresenterElement();
		p.setName( "name" );
		p.setClassName( "com.test.Presenter" );

		InvalidTypeException e = new InvalidTypeException( p, "Event Bus", EventBus.class.getName(), EventBusWithLookup.class.getName() );

		assertEquals( "presenter name: Invalid Event Bus. Can not convert " + EventBus.class.getName() + " to " + EventBusWithLookup.class.getName(),
				e.getMessage() );
	}

	@Test
	public void testNonUniqueIdentifierException() {

		NonUniqueIdentifierException e = new NonUniqueIdentifierException( "test" );
		assertEquals( "Element identifier 'test' is not globally unique.", e.getMessage() );
	}
	
	@Test
	public void testNotFoundClassException() throws DuplicatePropertyNameException {

		PresenterElement p = new PresenterElement();
		p.setName( "name" );
		p.setClassName( "com.test.Presenter" );

		NotFoundClassException e = new NotFoundClassException( null, PresenterInterface.class.getName() );
		assertEquals( "No source code is available for " + PresenterInterface.class.getName(), e.getMessage() );
		
		e = new NotFoundClassException( p, PresenterInterface.class.getName() );
		assertEquals( "presenter name: No source code is available for " + PresenterInterface.class.getName(), e.getMessage() );
	}
	
	@Test
	public void testUnknownConfigurationElementException() throws DuplicatePropertyNameException {

		PresenterElement p = new PresenterElement();
		p.setName( "name" );
		p.setClassName( "com.test.Presenter" );

		UnknownConfigurationElementException e = new UnknownConfigurationElementException( p, "test" );
		assertEquals( "presenter name: Encountered a reference to unknown element 'test'", e.getMessage() );
	}
	
	@Test
	public void testDuplicatePropertyNameException() {
		DuplicatePropertyNameException e = new DuplicatePropertyNameException( "test" );
		assertEquals( "Duplicate attributes 'test'", e.getMessage() );
	}
	
	@Test
	public void testMvp4gAnnotationException() {
		Mvp4gAnnotationException e = new Mvp4gAnnotationException(null, null, "test" );
		assertEquals( "test", e.getMessage() );
		
		e = new Mvp4gAnnotationException("testClass", null, "test" );
		assertEquals( "testClass: test", e.getMessage() );
		
		e = new Mvp4gAnnotationException(null, "testMethod", "test" );
		assertEquals( "Method testMethod: test", e.getMessage() );
		
		e = new Mvp4gAnnotationException("testClass", "testMethod", "test" );
		assertEquals( "testClass: Method testMethod: test", e.getMessage() );
	}
	
	@Test
	public void testMvp4gXmlException() throws DuplicatePropertyNameException {
		Mvp4gXmlException e = new Mvp4gXmlException(null, "test" );
		assertEquals( "test", e.getMessage() );
		
		PresenterElement p = new PresenterElement();
		p.setName( "name" );
		p.setClassName( "com.test.Presenter" );
		
		e = new Mvp4gXmlException(p, "test" );
		assertEquals( "presenter name: test", e.getMessage() );
		
		e = new Mvp4gXmlException(null, "test" );
		e.setXmlFilePath( "test.xml" );
		assertEquals( "test.xml: test", e.getMessage() );
		
		e = new Mvp4gXmlException(p, "test" );
		e.setXmlFilePath( "test.xml" );
		assertEquals( "test.xml: presenter name: test", e.getMessage() );
	}


}
