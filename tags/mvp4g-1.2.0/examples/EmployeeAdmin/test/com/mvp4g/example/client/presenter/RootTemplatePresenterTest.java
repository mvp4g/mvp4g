package com.mvp4g.example.client.presenter;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.replay;

import org.junit.Before;
import org.junit.Test;

import com.mvp4g.example.client.presenter.RootTemplatePresenter.IRootTemplateView;
import com.mvp4g.example.client.widget.interfaces.IWidget;


public class RootTemplatePresenterTest {

	private RootTemplatePresenter presenter;
	private IRootTemplateView mockView;
	private IWidget mockWidget;

	@Before
	public void setUp() {
		presenter = new RootTemplatePresenter();
		mockView = createMock( IRootTemplateView.class );
		presenter.setView( mockView );
		mockWidget = createMock( IWidget.class ); 
	}

	@Test
	public void testOnChangeTopWidget() {
		mockView.setTopWidget( mockWidget );
		replay( mockView );
		presenter.onChangeTopWidget( mockWidget );		
	}

	@Test
	public void testOnChangeLeftBottomWidget() {
		mockView.setLeftBottomWidget( mockWidget );
		replay( mockView );
		presenter.onChangeLeftBottomWidget( mockWidget );		
	}

	@Test
	public void testOnChangeRightBottomWidget() {
		mockView.setRightBottomWidget( mockWidget );
		replay( mockView );
		presenter.onChangeRightBottomWidget( mockWidget );		
	}

}
