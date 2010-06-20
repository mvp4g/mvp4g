package com.mvp4g.util.config.loader.annotation;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.util.config.element.PresenterElement;
import com.mvp4g.util.config.element.ViewElement;
import com.mvp4g.util.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.util.test_tools.annotation.Presenters;

public class PresenterAnnotationsLoaderTest extends AbstractMvp4gAnnotationsWithServiceLoaderTest<Presenter, PresenterAnnotationsLoader> {

	@Test
	public void testView() throws Mvp4gAnnotationException {

		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( getSimpleClass() );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		ViewElement view = configuration.getViews().iterator().next();
		assertEquals( view.getClassName(), Object.class.getName() );
		assertEquals( view.getName(), type.getQualifiedSourceName().replace( '.', '_' ) + "View" );

	}

	@Test
	public void testViewWithName() throws Mvp4gAnnotationException {

		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( Presenters.PresenterWithViewName.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		ViewElement view = configuration.getViews().iterator().next();
		assertEquals( view.getClassName(), Object.class.getName() );
		assertEquals( view.getName(), "name" );

	}

	@Test
	public void testNotMultiple() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( getSimpleClass() );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		PresenterElement element = configuration.getPresenters().iterator().next();
		assertFalse( element.isMultiple() );
	}

	@Test
	public void testMultiple() throws Mvp4gAnnotationException {
		List<JClassType> annotedClasses = new ArrayList<JClassType>();
		JClassType type = oracle.addClass( Presenters.MultiplePresenter.class );
		annotedClasses.add( type );
		loader.load( annotedClasses, configuration );
		PresenterElement element = configuration.getPresenters().iterator().next();
		assertTrue( element.isMultiple() );
	}

	@Override
	protected Class<?> getClassNotAsync() {
		return Presenters.PresenterNotAsync.class;
	}

	@Override
	protected Class<?> getClassNotPublic() {
		return Presenters.PresenterNotPublic.class;
	}

	@Override
	protected Class<?> getClassWithMoreThanOne() {
		return Presenters.PresenterWithMoreThanOneParameter.class;
	}

	@Override
	protected Class<?> getClassWithNoParameter() {
		return Presenters.PresenterNoParameter.class;
	}

	@Override
	protected Class<?> getSameService() {
		return Presenters.PresenterWithSameService.class;
	}

	@Override
	protected Class<?> getService() {
		return Presenters.PresenterWithService.class;
	}

	@Override
	protected Class<?> getServiceWithName() {
		return Presenters.PresenterWithServiceAndName.class;
	}

	@Override
	protected PresenterAnnotationsLoader createLoader() {
		return new PresenterAnnotationsLoader();
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected Set<PresenterElement> getSet() {
		return configuration.getPresenters();
	}

	@Override
	protected Class<?> getSimpleClass() {
		return Presenters.SimplePresenter.class;
	}

	@Override
	protected Class<?> getWithNameClass() {
		return Presenters.PresenterWithName.class;
	}

	@Override
	protected Class<?> getWrongInterface() {
		return Object.class;
	}

}
