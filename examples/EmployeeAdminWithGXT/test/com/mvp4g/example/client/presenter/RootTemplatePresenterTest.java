package com.mvp4g.example.client.presenter;

import static junit.framework.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.example.client.mock.view.MockRootTemplateView;
import com.mvp4g.example.client.mock.widget.MyMockWidget;

public class RootTemplatePresenterTest {

	private RootTemplatePresenter presenter = null;
	private MockRootTemplateView view = null;

	@Before
	public void setUp() {
		presenter = new RootTemplatePresenter();
		view = new MockRootTemplateView();
		presenter.setView( view );
		presenter.bind();
	}

	@Test
	public void testOnChangeTopWidget() {
		MyMockWidget widget = new MyMockWidget();
		presenter.onChangeTopWidget( widget );
		assertEquals( widget, view.getTopWidget() );
	}

	@Test
	public void testOnChangeLeftBottomWidget() {
		MyMockWidget widget = new MyMockWidget();
		presenter.onChangeLeftBottomWidget( widget );
		assertEquals( widget, view.getLeftBottomWidget() );
	}

	@Test
	public void testOnChangeRightBottomWidget() {
		MyMockWidget widget = new MyMockWidget();
		presenter.onChangeRightBottomWidget( widget );
		assertEquals( widget, view.getRightBottomWidget() );
	}

}
