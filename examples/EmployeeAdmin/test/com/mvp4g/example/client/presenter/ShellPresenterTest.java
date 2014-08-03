package com.mvp4g.example.client.presenter;

import com.mvp4g.example.client.ui.shell.IShellView;
import com.mvp4g.example.client.ui.shell.ShellPresenter;
import com.mvp4g.example.client.widget.interfaces.IWidget;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.createMock;


public class ShellPresenterTest {

  private ShellPresenter presenter;
  private IShellView     mockView;
  private IWidget        mockWidget;

  @Before
  public void setUp() {
    presenter = new ShellPresenter();
    mockView = createMock(IShellView.class);
    presenter.setView(mockView);
    mockWidget = createMock(IWidget.class);
  }

  @Test
  public void testOnChangeTopWidget() {
//    mockView.setTopWidget(mockWidget);
//    replay(mockView);
//    presenter.onChangeTopWidget(mockWidget);
  }

  @Test
  public void testOnChangeLeftBottomWidget() {
//    mockView.setLeftBottomWidget(mockWidget);
//    replay(mockView);
//    presenter.onChangeLeftBottomWidget( mockWidget );
	}

	@Test
	public void testOnChangeRightBottomWidget() {
//		mockView.setRightBottomWidget( mockWidget );
//		replay( mockView );
//		presenter.onChangeRightBottomWidget( mockWidget );
	}

}
