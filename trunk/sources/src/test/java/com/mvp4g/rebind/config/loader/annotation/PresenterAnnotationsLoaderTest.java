package com.mvp4g.rebind.config.loader.annotation;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.mvp4g.client.annotation.Presenter;
import com.mvp4g.rebind.config.element.PresenterElement;
import com.mvp4g.rebind.config.element.ViewElement;
import com.mvp4g.rebind.exception.loader.Mvp4gAnnotationException;
import com.mvp4g.rebind.test_tools.annotation.Presenters;
import com.mvp4g.rebind.test_tools.annotation.Presenters.AsyncPresenter;
import com.mvp4g.rebind.test_tools.annotation.Presenters.MultiplePresenter;
import com.mvp4g.rebind.test_tools.annotation.presenters.PresenterWithName;
import com.mvp4g.rebind.test_tools.annotation.presenters.SimplePresenter;

public class PresenterAnnotationsLoaderTest extends AbstractHandlerAnnotationsLoaderTest<Presenter, PresenterElement, PresenterAnnotationsLoader> {

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
		return SimplePresenter.class;
	}

	@Override
	protected Class<?> getWithNameClass() {
		return PresenterWithName.class;
	}

	@Override
	protected Class<?> getWrongInterface() {
		return Object.class;
	}

	@Override
	protected Class<?> getMultipleClass() {
		return MultiplePresenter.class;
	}

	@Override
	protected Class<?> getAsyncClass() {
		return AsyncPresenter.class;
	}

}
